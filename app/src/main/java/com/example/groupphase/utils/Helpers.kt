package com.example.groupphase.utils

import android.util.Log
import com.example.groupphase.common.Resource
import com.example.groupphase.domain.model.Match
import com.example.groupphase.domain.model.Round
import com.example.groupphase.domain.model.Team

object Helpers {
    fun calculateTotalStrength(team: Team): Double {
        var totalScore = 0.0
        team.players.forEach { player ->
            totalScore += player.strength
        }

        //round to 1 decimal
        return (totalScore * 10.0).toInt() / 10.0
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

        if(result.data != null) {
            Log.d("Helpers", "Match: ${result.data.home.first.name} ---- ${result.data.away.first.name} score ${result.data.home.second} - ${result.data.away.second}")
        }

        return updatedRounds
    }
}