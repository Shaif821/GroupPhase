package com.example.groupphase.presentation.screens.start_screen

import com.example.groupphase.domain.model.Simulation
import com.example.groupphase.domain.model.Team

data class StartState(
    val isLoading: Boolean = true,
    val error: String = "",
    val success: Boolean = false,
    val teams: List<Team> = emptyList(),
    val canStart: Boolean = false,
    val simulations: List<Simulation> = emptyList(),
)
