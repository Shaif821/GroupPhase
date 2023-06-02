package com.example.groupphase.presentation.screens.simulate_screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.groupphase.domain.model.Team
import com.example.groupphase.presentation.screens.start_screen.StartViewModel

@Composable
fun SimulateScreen(
    onNavigateStart: () -> Unit,
    viewModel: SimulateViewModel = hiltViewModel(),
    startViewModel: StartViewModel = hiltViewModel(),
) {

    LaunchedEffect(Unit) {
        val teams by startViewModel.state.collectAsState()
        viewModel.setTeams(teams.teams)
        viewModel.determineMatches()
    }


    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Text(text = "Simulate Screen")

    }
}