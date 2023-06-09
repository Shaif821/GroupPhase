package com.example.groupphase.presentation.screens.simulate_screen

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.groupphase.common.Resource
import com.example.groupphase.domain.model.Match
import com.example.groupphase.domain.model.Team
import com.example.groupphase.domain.use_case.simulation_use_cases.CalculateResultsUseCase
import com.example.groupphase.domain.use_case.simulation_use_cases.DetermineMatchesOrderUseCase
import com.example.groupphase.domain.use_case.simulation_use_cases.InsertSimulationUseCase
import com.example.groupphase.domain.use_case.simulation_use_cases.SimulateMatchUseCase
import com.example.groupphase.utils.Helpers
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
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
    val state = _state.asStateFlow()

    private val ioScope = CoroutineScope(Dispatchers.IO)

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
        }.launchIn(ioScope)
    }

    fun startMatch(match: Match, currentRoundIndex: Int, currentMatchIndex: Int) {
        _state.value = state.value.copy(isLoading = true)

        if(checkSimulationEvent() == SimulationEvent.CALCULATE_RESULTS) return

        simulateMatchUseCase(match).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    val updatedRounds = Helpers.updateMatchInRounds(
                        state.value.rounds,
                        currentRoundIndex,
                        currentMatchIndex,
                        result,
                        match
                    )
                    _state.value = state.value.copy(
                        isLoading = false,
                        success = true,
                        rounds = updatedRounds,
                        currentRound = currentRoundIndex,
                        simulationEvent = checkSimulationEvent()
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
        }.launchIn(ioScope)
    }


    fun calculateResults() {
        _state.value = state.value.copy(isLoading = true)

        // Get all teams but only once
        val teams = state.value.rounds.flatMap { round ->
            round.match.flatMap { listOf(it.home.first, it.away.first) }
        }.distinct()

        calculateResultsUseCase(state.value.rounds, teams).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _state.value = state.value.copy(
                        isLoading = false,
                        success = true,
                        simulation = state.value.simulation.copy(
                            results = result.data ?: listOf(),
                            teams = teams
                        ),
                        simulationEvent = SimulationEvent.CALCULATE_RESULTS
                    )
                    insertSimulation()
                }

                is Resource.Loading -> _state.value = state.value.copy(isLoading = true)
                is Resource.Error -> {
                    _state.value = state.value.copy(
                        isLoading = false,
                        error = result.message
                            ?: "Something went wrong while calculating the results..."
                    )
                }
            }
        }.launchIn(ioScope)
    }

    private fun insertSimulation() {
        _state.value = state.value.copy(isLoading = true)
        val simulation = state.value.simulation

        insertSimulationUseCase(simulation).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    simulation.id = result.data ?: 0

                    _state.value = state.value.copy(
                        isLoading = false,
                        success = true,
                        simulation = simulation,
                        simulationEvent = SimulationEvent.SAVED_SIMULATION
                    )
                }

                is Resource.Loading -> _state.value = state.value.copy(isLoading = true)
                is Resource.Error -> {
                    _state.value = state.value.copy(
                        isLoading = false,
                        error = result.message
                            ?: "Something went wrong while inserting the simulation..."
                    )
                }
            }
        }.launchIn(ioScope)
    }


    private fun checkSimulationEvent(): SimulationEvent {
        var played = 2

        state.value.rounds.forEach { round ->
            round.match.forEach { match ->
                if (match.played) played++
            }
        }

        Log.d("SimulateViewModel", "Played: $played")

        return if (played > 6) {
            SimulationEvent.CALCULATE_RESULTS
        } else {
            SimulationEvent.SIMULATE_MATCH
        }
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