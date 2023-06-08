package com.example.groupphase.presentation.screens.start_screen

import androidx.lifecycle.ViewModel
import com.example.groupphase.common.Resource
import com.example.groupphase.domain.model.Team
import com.example.groupphase.domain.use_case.simulation_use_cases.GetAllSimulationUseCase
import com.example.groupphase.domain.use_case.team_use_cases.GetAllTeamsUseCase
import com.example.groupphase.domain.use_case.team_use_cases.InsertTeamUseCase
import com.example.groupphase.utils.MockData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class StartViewModel @Inject constructor(
    private val getAllTeamsUseCase: GetAllTeamsUseCase,
    private val insertTeamUseCase: InsertTeamUseCase,
    private val getAllSimulationUseCase: GetAllSimulationUseCase
) : ViewModel() {
    private val _state = MutableStateFlow<StartState>(StartState())
    val state = _state.asStateFlow()

    private val ioScope = CoroutineScope(Dispatchers.IO)

    companion object {
        const val ERROR = "An unexpected error occurred"
    }

    init {
        // Check if teams exists, if not create the teams and players
        getTeams()
        getSimulations()
    }

    fun generateTeams() {
        val generatedTeams = MockData().mockTeams()
        generatedTeams.forEach { team ->
            insertTeam(team)
        }
    }

    private fun getTeams() {
        _state.value = StartState(isLoading = true)
        getAllTeamsUseCase().onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _state.value = state.value.copy(
                        isLoading = false,
                        teams = result.data ?: listOf(),
                    )
                }
                is Resource.Error -> _state.value = StartState(error = result.message ?: ERROR)
                is Resource.Loading -> _state.value = StartState(isLoading = true)
            }
        }.launchIn(ioScope)
    }

    private fun insertTeam(team: Team) {
        _state.value = state.value.copy(isLoading = true)
        insertTeamUseCase(team).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _state.value = state.value.copy(
                        isLoading = false,
                        success = true,
                        teams = state.value.teams + team,
                    )
                }

                is Resource.Error -> {
                    getTeams()
                    _state.value = StartState(error = result.message ?: ERROR)
                }
                is Resource.Loading -> _state.value = StartState(isLoading = true)
            }
        }.launchIn(ioScope)
    }

    private fun getSimulations() {
        _state.value = StartState(isLoading = true)
        getAllSimulationUseCase().onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _state.value = state.value.copy(
                        isLoading = false,
                        simulations = result.data ?: listOf()
                    )
                }

                is Resource.Error -> {
                    _state.value = StartState(error = result.message ?: ERROR)
                }

                is Resource.Loading -> _state.value = StartState(isLoading = true)
            }
        }.launchIn(ioScope)
    }
}