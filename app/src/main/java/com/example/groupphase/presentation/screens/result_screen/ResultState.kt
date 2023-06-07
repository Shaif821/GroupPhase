package com.example.groupphase.presentation.screens.result_screen

import com.example.groupphase.domain.model.Simulation

data class ResultState(
    val isLoading : Boolean = true,
    val error : String = "",
    val success : Boolean = false,
    val currentSimulation: Simulation? = null,
    val simulations: List<Simulation> = emptyList(),
)

