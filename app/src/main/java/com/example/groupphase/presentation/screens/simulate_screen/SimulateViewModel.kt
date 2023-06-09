package com.example.groupphase.presentation.screens.simulate_screen

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.groupphase.common.Resource
import com.example.groupphase.domain.model.Match
import com.example.groupphase.domain.model.Round
import com.example.groupphase.domain.model.Team
import com.example.groupphase.domain.use_case.simulation_use_cases.CalculateResultsUseCase
import com.example.groupphase.domain.use_case.simulation_use_cases.DetermineMatchesOrderUseCase
import com.example.groupphase.domain.use_case.simulation_use_cases.InsertSimulationUseCase
import com.example.groupphase.domain.use_case.simulation_use_cases.SimulateMatchUseCase
import com.example.groupphase.utils.Helpers
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
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

    private val _button = MutableStateFlow(ButtonState())
    val button = _button.asStateFlow()

    private val _playedMatches = MutableStateFlow<List<Match>>(emptyList())
    private val playedMatches = _playedMatches.asStateFlow()

    private val ioScope = CoroutineScope(Dispatchers.IO)

    fun setup(teams: List<Team>) {
        Log.d("SimulateViewModel", "setup: YOOYOO")
        setTeams(teams)

        when (state.value.simulationEvent) {
            SimulationEvent.DETERMINE_MATCHES -> {
                _button.value = button.value.copy(
                    isEnabled = false,
                    message = "Determining matches.."
                )
                determineMatches()
            }

            SimulationEvent.SIMULATE_MATCH -> {
                _button.value = button.value.copy(
                    isEnabled = false,
                    message = "Simulating match..",
                )
            }
            else -> {
                _button.value = button.value.copy(
                    isEnabled = false,
                    message = "Something went wrong.."
                )
            }
        }
    }

    private fun determineMatches() {
        _state.value = state.value.copy(isLoading = true)
        determineMatchesOrderUseCase(state.value.teams).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _state.value = state.value.copy(
                        isLoading = false,
                        success = true,
                        rounds = result.data ?: listOf(),
                        simulationEvent = SimulationEvent.SIMULATE_MATCH
                    )

                    state.value.rounds.forEach { round ->
                        round.match.forEach { match ->
                            startMatch(match)
                        }
                    }
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

    private fun startMatch(match: Match) {
        _state.value = state.value.copy(isLoading = true)

        simulateMatchUseCase(match).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    if (result.data != null) {
                        _playedMatches.value = playedMatches.value + result.data
                        Log.d("SimulateViewModel", "Played matches: ${playedMatches.value.size}")
                        isAllMatchesPlayed()
                    }
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

    private fun isAllMatchesPlayed() {
        if (playedMatches.value.size == 6) {
            _state.value = state.value.copy(
                isLoading = false,
                success = true,
                rounds = Helpers.updateMatchInRounds(playedMatches.value),
                simulationEvent = SimulationEvent.CALCULATE_RESULTS
            )

            _button.value = button.value.copy(
                isEnabled = true,
                message = "Calculate scores!",
                method = calculateResults()
            )
        }
    }

    private fun calculateResults() {
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

                    _button.value = button.value.copy(
                        isEnabled = true,
                        message = "Simulation saved! Go back.",
                        method = Unit
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

    private fun setTeams(teams: List<Team>) {
        _state.value = state.value.copy(
            teams = teams
        )
    }
}