package com.example.groupphase.presentation.screens.simulate_screen

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
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
import com.example.groupphase.presentation.components.MatchCard
import com.example.groupphase.presentation.screens.start_screen.StartViewModel

@Composable
fun SimulateScreen(
    onNavigateStart: () -> Unit,
    onNavigateResult: (String?) -> Unit,
    viewModel: SimulateViewModel = hiltViewModel(),
    startViewModel: StartViewModel = hiltViewModel(),
) {
    val teams = startViewModel.state.collectAsState().value
    val state = viewModel.state.collectAsState().value
    val rounds = state.rounds

    val buttonState by viewModel.button.collectAsState()

    LaunchedEffect(teams.teams) {
        if (teams.teams.isNotEmpty()) {
            viewModel.setup(teams.teams)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Filled.ArrowBack,
                contentDescription = "Back",
                modifier = Modifier
                    .padding(16.dp)
                    .clickable {
                        onNavigateStart()
                    }
            )
            Text(text = "Simulate", fontSize = 32.sp)
            Spacer(modifier = Modifier.weight(1f))
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .animateContentSize()
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (rounds.isNotEmpty()) {
                rounds.forEachIndexed { currentRoundIndex, round ->
                    Text(
                        text = "Round #${currentRoundIndex + 1}",
                        fontSize = 24.sp,
                        modifier = Modifier.padding(top = 32.dp)
                    )
                    round.match.forEach {
                        MatchCard(
                            match = it,
                        )
                    }
                }
                Button(
                    enabled = buttonState.isEnabled,
                    onClick = { buttonState.method },
                    modifier = Modifier
                        .padding(32.dp)
                        .height(48.dp),
                ) {
                    Text(text = buttonState.message)
                }
            }
        }
    }
}
