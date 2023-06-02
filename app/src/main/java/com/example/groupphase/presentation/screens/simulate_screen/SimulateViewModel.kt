package com.example.groupphase.presentation.screens.simulate_screen

import androidx.lifecycle.ViewModel
import com.example.groupphase.domain.model.Match
import com.example.groupphase.domain.model.Round
import com.example.groupphase.domain.model.Team
import com.example.groupphase.domain.use_case.SimulationUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class SimulateViewModel @Inject constructor(
    private val simulationUseCases: SimulationUseCases
) : ViewModel() {

    private val _simState = MutableStateFlow(SimulationState())
    val simState = _simState

    // Determine which teams are playing against each other in the next round
    fun determineMatches() {
        _simState.value = simState.value.copy(isLoading = true)

        // Rounds that have already been played, use this to determine which teams will play
        // against each other, and which teams will play at home or away.
        val rounds = simState.value.rounds



    }


    fun setMatch(match: Match) {
        _simState.value = simState.value.copy(
            matches = simState.value.matches + match
        )
    }

    fun setRound(round: Round) {
        _simState.value = simState.value.copy(
            rounds = simState.value.rounds + round
        )
    }

    fun setTeams(teams: List<Team>) {
        _simState.value = simState.value.copy(
            teams = teams
        )
    }
}