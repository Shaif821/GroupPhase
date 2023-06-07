package com.example.groupphase.presentation.screens.start_screen

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.groupphase.domain.model.Team
import com.example.groupphase.presentation.components.TeamCard

@Composable
fun StartScreen(
    onNavigateSimulate: () -> Unit,
    viewModel: StartViewModel = hiltViewModel()
) {
    val state = viewModel.state.collectAsState().value

    Column(
        modifier = Modifier
            .fillMaxSize()
            .animateContentSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Group Phase Simulation",
            fontSize = 32.sp,
            modifier = Modifier.padding(top = 32.dp)
        )
        Spacer(modifier = Modifier.height(32.dp))
        Column(
            modifier = Modifier.fillMaxSize().height(400.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (state.teams.isNotEmpty()) {
                val teams = state.teams // Lokale variabele voor stabiele teams
                teams.forEach { team ->
                    TeamCard(team, viewModel.calculateTotalStrength(team))
                }
                Button(
                    onClick = {
                        onNavigateSimulate()
                    },
                    modifier = Modifier
                        .padding(top = 16.dp)
                        .height(48.dp),
                ) {
                    Text(text = "Start Simulation")
                }
            } else {
                Text(
                    text = "No teams found...",
                    fontSize = 16.sp,
                    modifier = Modifier.padding(top = 16.dp)
                )
                Button(
                    onClick = { viewModel.generateTeams() },
                    modifier = Modifier
                        .padding(top = 16.dp)
                        .height(48.dp),
                ) {
                    Text(text = "Generate Teams")
                }
            }

            Divider(modifier = Modifier.padding(top = 16.dp))
            state.simulations.forEach {
                Text(text = "Simulation: ${it.id}")
                Divider(modifier = Modifier.padding(top = 16.dp))
            }
        }

    }
}