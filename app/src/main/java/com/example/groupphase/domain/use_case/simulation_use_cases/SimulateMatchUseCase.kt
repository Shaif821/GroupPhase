package com.example.groupphase.domain.use_case.simulation_use_cases

import com.example.groupphase.common.Resource
import com.example.groupphase.domain.model.Match
import com.example.groupphase.domain.model.Player
import com.example.groupphase.domain.model.Round
import com.example.groupphase.domain.model.Team
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.time.LocalDate
import javax.inject.Singleton

// Determine the order which the teams will play against eah other. A total of 3 rounds will be played
@Singleton
class SimulateMatchUseCase {
    operator fun invoke(match: Match): Flow<Resource<Match>> = flow {
        try {
            emit(Resource.Loading())

            val homeStrength = calculatStrenght(match.home.first.players)
            val awayStrength = calculatStrenght(match.away.first.players)

            // calculate the score. The score is based on the strength of the team. But is randomized a bit
            val homeScore = (homeStrength * 10.0).toInt() / 10.0 + (Math.random() * 2.0 - 1.0)
            val awayScore = (awayStrength * 10.0).toInt() / 10.0 + (Math.random() * 2.0 - 1.0)

            // update the score in the temp match (since this is an invoke function, the match will be updated in the tempMatch)
            val updatedMatch = match
                .copy(
                    home = match.home.copy(second = homeScore.toInt()),
                    away = match.away.copy(second = awayScore.toInt())
                )
            emit(Resource.Success(updatedMatch))
        } catch (e: Exception) {
            emit(Resource.Error("Er is een fout opgetreden bij het bepalen van de wedstrijdvolgorde: ${e.message}"))
        }
    }

    private fun calculatStrenght(players: List<Player>): Double {
        var strength = 0.0
        players.forEach { player ->
            strength += player.strength
        }
        return (strength * 10.0).toInt() / 10.0
    }
}