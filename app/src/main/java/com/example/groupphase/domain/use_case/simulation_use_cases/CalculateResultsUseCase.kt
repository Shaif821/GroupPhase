package com.example.groupphase.domain.use_case.simulation_use_cases

import android.util.Log
import com.example.groupphase.common.Resource
import com.example.groupphase.domain.model.Match
import com.example.groupphase.domain.model.Result
import com.example.groupphase.domain.model.Round
import com.example.groupphase.domain.model.Team
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.time.LocalDate
import javax.inject.Inject

// Determine the order which the teams will play against eah other. A total of 3 rounds will be played
class CalculateResultsUseCase @Inject constructor() {
    operator fun invoke(
        rounds: List<Round>,
    ): Flow<Resource<List<Result>>> = flow {
        try {
            emit(Resource.Loading())
            emit(Resource.Success(getResults(rounds)))
        } catch (e: Exception) {
            emit(Resource.Error("The following error occurred while calculating the results: ${e.message}"))
        }
    }

    private fun getResults(rounds: List<Round>): List<Result> {
        val results = mutableListOf<Result>()

        // make a list of all the teams but make sure there are no duplicates with distinct()
        val teams = rounds.flatMap { round ->
            round.match.flatMap { listOf(it.home.first, it.away.first) }
        }.distinct()

        teams.forEach { team ->
            results.add(calculateScore(team, rounds))
        }

        // all results are calculated, but the results are not ordered yet (position)
        // so order the results by points, goal difference, goals for and goals against
        results.sortWith(
            compareByDescending<Result> { it.points }.thenByDescending { // first sort by points
                it.goalDifference
            }.thenByDescending { // then by goal difference
                it.goalsFor
            }.thenBy { // then by goals for
                it.goalsAgainst
            } // then by goals against
        )

        // Now the list is ordered, but the position is not set yet. So we set the pisition
        results.forEachIndexed { index, result -> result.position = index + 1 }

        return results
    }

    private fun calculateScore(team: Team, rounds: List<Round>): Result {
        // filter all the matches so that only the matches of the team are left.
        // So matches where the team did not participate are filtered out
        val teamMatches: List<Match> = rounds.flatMap { round ->
            round.match.filter { match ->
                match.home.first == team || match.away.first == team
            }
        }

        val won = rounds.count { round ->
            round.match.any { match ->
                (match.home.first == team && match.home.second > match.away.second) ||
                        (match.away.first == team && match.away.second > match.home.second)
            }
        }

        val lost = rounds.count { round ->
            round.match.any { match ->
                (match.home.first == team && match.home.second < match.away.second) ||
                        (match.away.first == team && match.away.second < match.home.second)
            }
        }

        val draw = rounds.count { round ->
            round.match.any { match ->
                (match.home.first == team && match.home.second == match.away.second) ||
                        (match.away.first == team && match.away.second == match.home.second)
            }
        }

        val goalsFor = calculateGoalsFor(team, teamMatches)

        val goalsAgainst = calculateGoalsAgainst(team, teamMatches)

        val goalDifference = goalsFor - goalsAgainst
        val points = (won * 3) + draw

        return Result(
            team = team,
            played = 0,
            won = won,
            lost = lost,
            drawn = draw,
            goalsFor = goalsFor,
            goalsAgainst = goalsAgainst,
            goalDifference = goalDifference,
            points = points,
            date = LocalDate.now(),
            position = 0
        )
    }


    private fun calculateGoalsFor(team: Team, matches: List<Match>): Int {
        var counter = 0
        matches.forEach { match ->
            if (match.home.first == team) {
                counter += match.home.second
            } else if (match.away.first == team) {
                counter += match.away.second
            }
        }
        return counter
    }

    private fun calculateGoalsAgainst(team: Team, matches: List<Match>): Int {
        var counter = 0
        matches.forEach { match ->
            if (match.home.first == team) {
                counter += match.away.second
            } else if (match.away.first == team) {
                counter += match.home.second
            }
        }
        return counter
    }
}
