package com.example.groupphase.presentation.components

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.groupphase.domain.model.Match
import com.example.groupphase.utils.Helpers

@Composable
fun MatchCard(
    match: Match,
) {
    val homeName = Helpers.setTeamName(match, true).uppercase()
    val awayName = Helpers.setTeamName(match, false).uppercase()

    val score = match.home.second.toString() + "-" + match.away.second.toString()

    Card(
        modifier = Modifier
            .padding(12.dp)
            .height(IntrinsicSize.Min)
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f),
        ),
    ) {
        Column(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
        ) {
            Row(
                modifier = Modifier
                    .padding(8.dp)
                    .animateContentSize()
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = homeName,
                    style = MaterialTheme.typography.bodyLarge
                        .copy(
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                )
                Text(
                    modifier = Modifier.animateContentSize(),
                    text = score,
                    style = MaterialTheme.typography.titleLarge
                        .copy(
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                )
                Text(
                    text = awayName,
                    style = MaterialTheme.typography.bodyLarge
                        .copy(
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                )
            }
        }
    }
}