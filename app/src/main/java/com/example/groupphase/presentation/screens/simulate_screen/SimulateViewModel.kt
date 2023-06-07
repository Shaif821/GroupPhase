package com.example.groupphase.presentation.screens.simulate_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.groupphase.common.Resource
import com.example.groupphase.domain.model.Match
import com.example.groupphase.domain.model.Team
import com.example.groupphase.domain.use_case.simulation_use_cases.CalculateResultsUseCase
import com.example.groupphase.domain.use_case.simulation_use_cases.DetermineMatchesOrderUseCase
import com.example.groupphase.domain.use_case.simulation_use_cases.InsertSimulationUseCase
import com.example.groupphase.domain.use_case.simulation_use_cases.SimulateMatchUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class SimulateViewModel @Inject constructor(
    private val determineMatchesOrderUseCase: DetermineMatchesOrderUseCase,
    private val simulateMatchUseCase: SimulateMatchUseCase,
    private val calculateResultsUseCase: CalculateResultsUseCase,
    private val insertSimulationUseCase: InsertSimulationUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(SimulationState())
    val state = _state

    fun determineMatches() {
        _state.value = state.value.copy(isLoading = true)
        determineMatchesOrderUseCase(state.value.teams).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _state.value = state.value.copy(
                        isLoading = false,
                        success = true,
                        rounds = result.data ?: listOf(),
                        simulationEvent = SimulationEvent.DETERMINE_MATCHES
                    )
                }

                is Resource.Loading -> _state.value = state.value.copy(isLoading = true)
                is Resource.Error -> {
                    _state.value = state.value.copy(
                        isLoading = false,
                        error = result.message ?: "Something went wrong.."
                    )
                }
            }
        }.launchIn(viewModelScope)
    }

    fun startMatch(match: Match) {
        // Get the current round and match
        val rounds = state.value.rounds.toMutableList()
        val currentRoundIndex = state.value.currentRound

        // Get the index of the match. This will be used to update the match later on
        _state.value = state.value.copy(isLoading = true)

        simulateMatchUseCase(match, rounds, currentRoundIndex).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    // index ends at 2
                    val newIndex = if (currentRoundIndex == 2) 2 else currentRoundIndex + 1
                    val isFinished = if(canShowResult()) SimulationEvent.MATCH_FINISHED else SimulationEvent.SIMULATE_MATCH
                    _state.value = state.value.copy(
                        isLoading = false,
                        success = true,
                        rounds = result.data ?: rounds,
                        currentRound = newIndex,
                        simulationEvent =  isFinished
                    )
                }

                is Resource.Loading -> _state.value = state.value.copy(isLoading = true)
                is Resource.Error -> {
                    _state.value = state.value.copy(
                        isLoading = false,
                        error = result.message ?: "Something went wrong."
                    )
                }
            }
        }.launchIn(viewModelScope)
    }

    fun calculateResults() {
        _state.value = state.value.copy(isLoading = true)

        calculateResultsUseCase(state.value.rounds).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _state.value = state.value.copy(
                        isLoading = false,
                        success = true,
                        simulation =  state.value.simulation.copy(
                            results = result.data ?: listOf()
                        ),
                        simulationEvent = SimulationEvent.CALCULATE_RESULTS
                    )
                    insertSimulation()
                }
                is Resource.Loading -> _state.value = state.value.copy(isLoading = true)
                is Resource.Error -> {
                    _state.value = state.value.copy(
                        isLoading = false,
                        error = result.message ?: "Something went wrong..."
                    )
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun insertSimulation() {
        _state.value = state.value.copy(isLoading = true)
        val simulation = state.value.simulation

        insertSimulationUseCase(simulation).onEach { result ->
            when(result) {
                is Resource.Success -> {
                    _state.value = state.value.copy(
                        isLoading = false,
                        success = true,
                        simulation = result.data ?: simulation,
                        simulationEvent = SimulationEvent.SAVED_SIMULATION
                    )
                }
                is Resource.Loading -> _state.value = state.value.copy(isLoading = true)
                is Resource.Error -> {
                    _state.value = state.value.copy(
                        isLoading = false,
                        error = result.message ?: "Something went wrong..."
                    )
                }
            }
        }.launchIn(viewModelScope)
    }


    fun setTeamName(match: Match, sort: Boolean): String {
        val name = if (sort) match.home.first.name else match.away.first.name

        if (match.home.second == match.away.second && sort) {
            return "$name ❌"
        }
        return if (match.home.second > match.away.second && sort) {
            "$name⚽"
        } else if (match.home.second < match.away.second && !sort) {
            "$name⚽"
        } else "❌ $name"
    }

    private fun canShowResult(): Boolean {
        var countedPlayedMatches = 2

        state.value.rounds.forEach { round ->
            round.match.forEach { match ->
                if (match.played) {
                    countedPlayedMatches++
                }
            }
        }
        return countedPlayedMatches == 5
    }

    fun setTeams(teams: List<Team>) {
        _state.value = state.value.copy(
            teams = teams
        )
    }

    fun setCurrentRound(round: Int) {
        _state.value = state.value.copy(
            currentRound = round
        )
    }
}