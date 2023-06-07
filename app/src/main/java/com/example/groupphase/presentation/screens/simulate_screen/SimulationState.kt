package com.example.groupphase.presentation.screens.simulate_screen

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
    val simulationEvent: SimulationEvent = SimulationEvent.DETERMINE_MATCHES
)

enum class SimulationEvent {
    DETERMINE_MATCHES,
    SIMULATE_MATCH,
    MATCH_FINISHED,
    CALCULATE_RESULTS,
    SAVED_SIMULATION,
}


