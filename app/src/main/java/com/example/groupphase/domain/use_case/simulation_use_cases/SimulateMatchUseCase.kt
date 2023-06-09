package com.example.groupphase.domain.use_case.simulation_use_cases

import com.example.groupphase.common.Resource
import com.example.groupphase.domain.model.Match
import com.example.groupphase.domain.model.Team
import com.example.groupphase.utils.Helpers.calculateTotalStrength
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import kotlin.random.Random

// Determine the order which the teams will play against eah other. A total of 3 rounds will be played
class SimulateMatchUseCase @Inject constructor() {
    operator fun invoke(
        match: Match,
    ): Flow<Resource<Match>> = flow {
        try {
            emit(Resource.Loading())
            // randomize total goals, but keep it within a range of 0 and 6
            val totalGoals = Random.nextInt(1, 6)

            val playedMatch = calculateScore(totalGoals, match.home, match.away)

            val updatedMatch = match.copy(
                home = match.home.copy(second = playedMatch[0].second),
                away = match.away.copy(second = playedMatch[1].second),
                played = true
            )
            emit(Resource.Success(updatedMatch))
        } catch (e: Exception) {
            emit(Resource.Error("An error occurred while simlating the match: ${e.message}"))
        }
    }

    private fun calculateScore(
        totalGoals: Int,
        home: Pair<Team, Int>,
        away: Pair<Team, Int>
    ): List<Pair<Team, Int>> {
        // based on the total goals, the team with the highest strength will get the most goals
        val homeStrength = calculateTotalStrength(home.first)
        val awayStrength = calculateTotalStrength(away.first)

        var homeGoals = 0
        var awayGoals = 0
        var goalsToSpend = totalGoals

        // loop through the total goals. Assign each goal to a team based on their strength
        repeat(totalGoals) {
            if (goalsToSpend > 0) {
                val randomNumb = Random.nextInt(0, homeStrength + awayStrength)

                // If the random number is lower than the home strength, the home team will get a goal
                if (randomNumb < homeStrength && homeStrength > 0) {
                    homeGoals++
                } else if (randomNumb < homeStrength + awayStrength && awayStrength > 0) { // If the random number is higher than the home strength, the away team will get a goal
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
}