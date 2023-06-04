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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.groupphase.presentation.components.MatchCard
import com.example.groupphase.presentation.screens.start_screen.StartViewModel

@Composable
fun SimulateScreen(
    onNavigateStart: () -> Unit,
    viewModel: SimulateViewModel = hiltViewModel(),
    startViewModel: StartViewModel = hiltViewModel(),
) {
    val teams = startViewModel.state.collectAsState().value

    val state = viewModel.state.collectAsState().value
    val rounds = state.rounds

    LaunchedEffect(teams) {
        if (teams.teams.isNotEmpty()) {
            viewModel.setTeams(teams.teams)
            viewModel.setCurrentRound(0)
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
        if (state.rounds.isNotEmpty()) {
            val currentRound = rounds[state.currentRound]

            Text(
                text = "Round #${rounds.indexOf(currentRound) + 1}",
                fontSize = 24.sp,
                modifier = Modifier.padding(top = 32.dp)
            )
            currentRound.match.forEach {
                MatchCard(
                    match = it,
                    viewModel::setTeamName,
                    viewModel::startMatch
                )
            }
            Text(
                text = state.error,
                fontSize = 24.sp,
                modifier = Modifier.padding(top = 32.dp)
            )
        }
    }
}