package com.example.groupphase.presentation.common

import com.example.groupphase.domain.model.Simulation

data class SimulationState(
    val loadingState: SimulationLoadingState = SimulationLoadingState.LOADING_SIMULATIONS,
    val isLoading: Boolean = false,
    val error: String = "",
    val success: Boolean = false,
    val simulationList: List<Simulation> = emptyList(),
    val currentSimulation: Simulation? = null,
)

enum class SimulationLoadingState {
    LOADING_SIMULATIONS,
    START_FIRST_ROUND,
    END_FIRST_ROUND,
    START_SECOND_ROUND,
    END_SECOND_ROUND,
    START_THIRD_ROUND,
    END_THIRD_ROUND,
    DETERMINE_WINNER,
}