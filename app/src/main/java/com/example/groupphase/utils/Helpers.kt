package com.example.groupphase.utils

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
}