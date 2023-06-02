package com.example.groupphase.presentation.screens.simulate_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.groupphase.common.Resource
import com.example.groupphase.domain.model.Match
import com.example.groupphase.domain.model.Round
import com.example.groupphase.domain.model.Team
import com.example.groupphase.domain.use_case.SimulationUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class SimulateViewModel @Inject constructor(
    private val simulationUseCases: SimulationUseCases
) : ViewModel() {

    private val _state = MutableStateFlow(SimulationState())
    val state = _state

    fun determineMatches() {
        _state.value = state.value.copy(isLoading = true)
        simulationUseCases.determineMatchesOrderUseCase(state.value.teams).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _state.value = state.value.copy(
                        isLoading = false,
                        success = true,
                        rounds = result.data ?: listOf()
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


    fun setMatch(match: Match) {
        _state.value = state.value.copy(
            matches = state.value.matches + match
        )
    }

    fun setRound(round: Round) {
        _state.value = state.value.copy(
            rounds = state.value.rounds + round
        )
    }

    fun setTeams(teams: List<Team>) {
        _state.value = state.value.copy(
            teams = teams
        )
    }
}