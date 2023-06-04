package com.example.groupphase.domain.use_case.simulation_use_cases

import com.example.groupphase.common.Resource
import com.example.groupphase.domain.model.Match
import com.example.groupphase.domain.model.Player
import com.example.groupphase.domain.model.Round
import com.example.groupphase.domain.model.Team
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.time.LocalDate
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.random.Random

// Determine the order which the teams will play against eah other. A total of 3 rounds will be played
class SimulateMatchUseCase @Inject constructor() {
    operator fun invoke(
        match: Match, rounds: List<Round>,
        roundIndex: Int
    ): Flow<Resource<List<Round>>> = flow {
        emit(Resource.Loading())

        val roundsCopy = rounds.toMutableList()

        val currentRound = rounds[roundIndex]
        val matchIndex = currentRound.match.indexOfFirst { !it.played }

        if (matchIndex != -1) {
            val homeStrength = calculatStrenght(match.home.first.players)
            val awayStrength = calculatStrenght(match.away.first.players)

            val homeScore = (homeStrength * (0.9 + 0.2 * Random.nextFloat())).toInt()
            val awayScore = (awayStrength * (0.9 + 0.2 * Random.nextFloat())).toInt()

            val updatedMatchList = currentRound.match.toMutableList()
            updatedMatchList[matchIndex] = match.copy(
                home = match.home.copy(second = homeScore),
                away = match.away.copy(second = awayScore),
                played = true
            )

            val updatedRound = currentRound.copy(match = updatedMatchList)
            roundsCopy[roundIndex] = updatedRound
        }

        emit(Resource.Success(roundsCopy.toList()))
    }

    private fun calculatStrenght(players: List<Player>): Int {
        var strength = 0.0
        players.forEach { player ->
            strength += player.strength
        }
        return strength.toInt()
    }
}