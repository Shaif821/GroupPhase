package com.example.groupphase.presentation.components

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.groupphase.domain.model.Match

@Composable
fun MatchCard(
    match: Match,
    teamName: (match: Match, sort: Boolean) -> String,
    statMatch: (Match) -> Unit,
) {

    LaunchedEffect(match) {
        statMatch(match)
    }

    val homeName =  teamName(match, true).uppercase()
    val awayName =  teamName(match, false).uppercase()

    val score = match.home.second.toString() + "-" + match.away.second.toString()

    val titleStyle = MaterialTheme.typography.titleLarge
        .copy(
            fontSize = 32.sp,
            color = Color.White
        )

    val scoreStyle = MaterialTheme.typography.titleLarge
        .copy(
            fontSize = 28.sp,
            color = Color.White
        )

    Card(
        modifier = Modifier
            .padding(12.dp)
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f),
        ),
    ) {
        Column(
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
        ) {
            Row(
                modifier = Modifier
                    .padding(12.dp)
                    .animateContentSize()
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = homeName,
                    style = titleStyle
                )
                Text(
                    modifier = Modifier.animateContentSize(),
                    text = score,
                    style = scoreStyle
                )
                Text(
                    text = awayName,
                    style = titleStyle
                )
            }
        }
    }
}