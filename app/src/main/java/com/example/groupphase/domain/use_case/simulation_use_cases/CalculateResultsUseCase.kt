package com.example.groupphase.domain.use_case.simulation_use_cases

import com.example.groupphase.common.Resource
import com.example.groupphase.domain.model.Match
import com.example.groupphase.domain.model.Player
import com.example.groupphase.domain.model.Round
import com.example.groupphase.domain.model.Team
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import kotlin.random.Random

// Determine the order which the teams will play against eah other. A total of 3 rounds will be played
class CalculateResultsUseCase @Inject constructor() {
    operator fun invoke(
        match: Match, rounds: List<Round>,
        roundIndex: Int
    ): Flow<Resource<List<Round>>> = flow {

        try {
            emit(Resource.Loading())

            val roundsCopy = rounds.toMutableList()

            val currentRound = rounds[roundIndex]
            val matchIndex = currentRound.match.indexOfFirst { !it.played }

            if (matchIndex != -1) {
                val updatedMatchList = currentRound.match.toMutableList()

                // randomize total goals, but keep it within a range of 0 and 6
                val totalGoals = Random.nextInt(1, 6)

                val playedMatch = calculateScore(totalGoals, match.home, match.away)

                updatedMatchList[matchIndex] = match.copy(
                    home = match.home.copy(second = playedMatch[0].second),
                    away = match.away.copy(second = playedMatch[1].second),
                    played = true
                )

                val updatedRound = currentRound.copy(match = updatedMatchList)
                roundsCopy[roundIndex] = updatedRound

                emit(Resource.Success(roundsCopy.toList()))
            }
        } catch (e: Exception) {
            emit(Resource.Error("The following error occurred while simulating the match $roundIndex: ${e.message}"))
        }
    }

    private fun calculateScore(
        totalGoals: Int,
        home: Pair<Team, Int>,
        away: Pair<Team, Int>
    ): List<Pair<Team, Int>> {
        // based on the total goals, the team with the highest strength will get the most goals
        val homeStrength = (calculatStrenght(home.first.players) * 10).toInt()
        val awayStrength = (calculatStrenght(away.first.players) * 10).toInt()

        var homeGoals = 0
        var awayGoals = 0
        var goalsToSpend = totalGoals

        repeat(totalGoals) {
            if (goalsToSpend > 0) {
                val randomNumb = Random.nextInt(0, homeStrength + awayStrength)

                if (randomNumb < homeStrength && homeStrength > 0) {
                    homeGoals++
                } else if (randomNumb < homeStrength + awayStrength && awayStrength > 0) {
                    awayGoals++
                }

                goalsToSpend--
            }
        }

        return listOf(
            home.copy(second = homeGoals),
            away.copy(second = awayGoals)
        )
    }

    private fun calculatStrenght(players: List<Player>): Double {
        var strength = 0.0
        players.forEach { player ->
            strength += player.strength
        }
        return strength + 10
    }
}