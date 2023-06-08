package com.example.groupphase.utils

import android.util.Log
import com.example.groupphase.common.Resource
import com.example.groupphase.domain.model.Match
import com.example.groupphase.domain.model.Round
import com.example.groupphase.domain.model.Team
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

object Helpers {
    fun calculateTotalStrength(team: Team): Int {
        var totalScore = 0
        team.players.forEach { player ->
            totalScore += player.strength
        }
        return totalScore / 10
    }

    fun setTeamName(match: Match, sort: Boolean): String {
        val name = if (sort) match.home.first.name else match.away.first.name

        if (match.home.second == match.away.second && sort) {
            return "$name ❌"
        }
        return if (match.home.second > match.away.second && sort) {
            "$name⚽"
        } else if (match.home.second < match.away.second && !sort) {
            "$name⚽"
        } else "❌ $name"
    }

    fun updateMatchInRounds(
        rounds: List<Round>,
        currentRoundIndex: Int,
        currentMatchIndex: Int,
        result: Resource.Success<Match>,
        match: Match
    ): List<Round> {
        val updatedRounds = rounds.toMutableList()

        // Check if currentRoundIndex is within bounds
        if (currentRoundIndex < updatedRounds.size) {
            val currentRound = updatedRounds[currentRoundIndex]
            val currentMatchList = currentRound.match.toMutableList()

            // Check if currentMatchIndex is within bounds
            if (currentMatchIndex < currentMatchList.size) {
                currentMatchList[currentMatchIndex] = result.data ?: match
                updatedRounds[currentRoundIndex] = currentRound.copy(match = currentMatchList)
            }
        }

        return updatedRounds
    }

    fun formatLocalDate(localDate: LocalDate): String {
        val formatter = DateTimeFormatter.ofPattern("d/M/yy")
        return localDate.format(formatter)
    }
}