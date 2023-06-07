package com.example.groupphase.presentation.components

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.groupphase.domain.model.Simulation
import com.example.groupphase.utils.Helpers
import java.time.format.DateTimeFormatter

@Composable
fun SimulationCard(simulation: Simulation) {
    val simulationDate = simulation.date

    val formatter = DateTimeFormatter.ofPattern("d-M-yy, HH:mm")
    val formattedDate = simulationDate.atTime(21, 34).format(formatter)

    Card(
        modifier = Modifier
            .height(IntrinsicSize.Min)
            .padding(8.dp)
            .animateContentSize(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f),
        ),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Text(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .fillMaxWidth(),
                text = "Simulation on $formattedDate"
            )
            Divider(modifier = Modifier.padding(12.dp))
            Row(
                modifier = Modifier
                    .width(IntrinsicSize.Min)
                    .horizontalScroll(rememberScrollState())
                    .padding(bottom = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Text(modifier = Modifier.padding(horizontal = 5.dp), text = "Position")
                Text(modifier = Modifier.padding(horizontal = 5.dp), text = "Win")
                Text(modifier = Modifier.padding(horizontal = 5.dp), text = "Draw")
                Text(modifier = Modifier.padding(horizontal = 5.dp), text = "Loss")
                Text(modifier = Modifier.padding(horizontal = 5.dp), text = "For")
                Text(modifier = Modifier.padding(horizontal = 5.dp), text = "Against")
                Text(modifier = Modifier.padding(horizontal = 5.dp), text = "-/+")
                Text(modifier = Modifier.padding(horizontal = 5.dp), text = "Points")
            }
            simulation.results.forEachIndexed { index, result ->
                Column(
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .horizontalScroll(rememberScrollState())
                            .padding(bottom = 8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                    ) {
                        Text(text = "${index + 1}. ${result.team.name}")
                        Text(text = "${result.won}")
                        Text(text = "${result.drawn}")
                        Text(text = "${result.lost}")
                        Text(text = "${result.goalsFor}")
                        Text(text = "${result.goalsAgainst}")
                        Text(text = "${result.goalDifference}")
                        Text(text = "${result.points}")
                    }
                }
            }
            Divider(modifier = Modifier.padding(12.dp))
            // Team stats
            simulation.teams.forEach { team ->
                TeamCard(team, Helpers.calculateTotalStrength(team))
            }
        }
    }
}