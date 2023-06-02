package com.example.groupphase.presentation.screens.simulate_screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.groupphase.domain.model.Team

@Composable
fun SimulateScreen(
    onNavigateStart: () -> Unit,
    viewModel: SimulateViewModel = hiltViewModel()
) {

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Text(text = "Simulate Screen")

    }
}