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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.groupphase.domain.model.Simulation
import com.example.groupphase.utils.Helpers
import java.time.format.DateTimeFormatter

@Composable
fun SimulationCard(simulation: Simulation) {
    val simulationDate = simulation.date

    val formatter = DateTimeFormatter.ofPattern("d-M-yy, HH:mm")
    val formattedDate = simulationDate.atTime(21, 34).format(formatter)
    val teamSorted = simulation.results.sortedBy { it.position }
    val topText = MaterialTheme.typography.bodySmall.copy(fontSize = 10.sp)
    val bodyText = MaterialTheme.typography.bodySmall.copy(fontSize = 11.sp)

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
                text = "Simulation on $formattedDate",
                textAlign = TextAlign.Center
            )
            Divider(modifier = Modifier.padding(12.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState())
                    .padding(bottom = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Text(text = "Position", style = topText)
                Text(text = "Win", style = topText)
                Text(text = "Draw", style = topText)
                Text(text = "Loss", style = topText)
                Text(text = "For", style = topText)
                Text(text = "Against", style = topText)
                Text(text = "-/+", style = topText)
                Text(text = "Points", style = topText)
            }
            Divider(modifier = Modifier.padding(2.dp))
            simulation.results.forEachIndexed { index, result ->
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp),
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .horizontalScroll(rememberScrollState())
                            .padding(bottom = 8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                    ) {
                        Text(text = "${index + 1}. ${result.team.name}", style = bodyText)
                        Text(text = "${result.won}", style = bodyText)
                        Text(text = "${result.drawn}", style = bodyText)
                        Text(text = "${result.lost}", style = bodyText)
                        Text(text = "${result.goalsFor}", style = bodyText)
                        Text(text = "${result.goalsAgainst}", style = bodyText)
                        Text(text = "${result.goalDifference}", style = bodyText)
                        Text(text = "${result.points}", style = bodyText)
                    }
                }
            }
            Divider(modifier = Modifier.padding(12.dp))
            // Team stats
            teamSorted.forEach { result ->
                result.team.let { team ->
                    TeamCard(team, Helpers.calculateTotalStrength(team))
                }
            }
        }
    }
}