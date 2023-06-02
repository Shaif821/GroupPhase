package com.example.groupphase.presentation.screens.start_screen

import com.example.groupphase.domain.model.Team

data class StartState(
    val isLoading: Boolean = false,
    val error: String = "",
    val success: Boolean = false,
    val teams: List<Team> = emptyList(),
)
