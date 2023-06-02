package com.example.groupphase.presentation.screens.simulate_screen

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.groupphase.presentation.screens.start_screen.StartViewModel

@Composable
fun SimulateScreen(
    onNavigateStart: () -> Unit,
    viewModel: SimulateViewModel = hiltViewModel(),
    startViewModel: StartViewModel = hiltViewModel(),
) {
    val state = viewModel.state.collectAsState().value
    val round = state.currentRound
    val teams = startViewModel.state.collectAsState().value

    LaunchedEffect(teams) {
        if(teams.teams.isNotEmpty()) {
            viewModel.setTeams(teams.teams)
            viewModel.determineMatches()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .animateContentSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Simulation",
            fontSize = 32.sp,
            modifier = Modifier.padding(top = 32.dp)
        )
        Text(
            text = "Round #$round",
            fontSize = 24.sp,
            modifier = Modifier.padding(top = 32.dp)
        )
        state.rounds.forEach { round ->
            round.match.forEach {
                Text(
                    text = "${it.home.first.name} vs ${it.away.first.name}",
                    fontSize = 24.sp,
                    modifier = Modifier.padding(top = 32.dp)
                )
            }
        }
        Text(
            text = state.error,
            fontSize = 24.sp,
            modifier = Modifier.padding(top = 32.dp)
        )
    }
}