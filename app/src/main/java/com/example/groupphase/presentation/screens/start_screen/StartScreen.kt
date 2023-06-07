package com.example.groupphase.presentation.screens.start_screen

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import com.example.groupphase.presentation.components.SimulationCard
import com.example.groupphase.presentation.components.TeamCard
import com.example.groupphase.utils.Helpers

@Composable
fun StartScreen(
    onNavigateSimulate: () -> Unit,
    onNavigateResult: (String?) -> Unit,
    viewModel: StartViewModel = hiltViewModel()
) {
    val state = viewModel.state.collectAsState().value

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
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
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (state.teams.isNotEmpty()) {
                val teams = state.teams // Lokale variabele voor stabiele teams
                teams.forEach { team ->
                    TeamCard(team, Helpers.calculateTotalStrength(team))
                }
                Row(
                    modifier = Modifier
                        .padding(top = 16.dp)
                        .height(48.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    Button(
                        onClick = {
                            onNavigateSimulate()
                        },
                    ) {
                        Text(text = "Start Simulation")
                    }
                    Button(
                        onClick = {
                            onNavigateResult(null)
                        },
                    ) {
                        Text(text = "Show simulations")
                    }
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
            Text(
                text = state.error,
                fontSize = 32.sp,
                modifier = Modifier.padding(top = 32.dp)
            )
        }
    }
}