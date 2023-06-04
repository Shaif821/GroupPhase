package com.example.groupphase.presentation.screens.simulate_screen

import com.example.groupphase.domain.model.Match
import com.example.groupphase.domain.model.Round
import com.example.groupphase.domain.model.Simulation
import com.example.groupphase.domain.model.Team
import java.time.LocalDate

data class SimulationState(
    val isLoading : Boolean = true,
    val error : String = "",
    val success : Boolean = false,
    val simulation: Simulation = Simulation(0, emptyList(), emptyList(), LocalDate.now()),
    val rounds: List<Round> = emptyList(),
    val teams : List<Team> = emptyList(),
    val currentRound: Int = 0,
)


