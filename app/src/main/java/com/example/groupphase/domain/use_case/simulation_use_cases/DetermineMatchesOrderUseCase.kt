package com.example.groupphase.domain.use_case.simulation_use_cases

import com.example.groupphase.common.Resource
import com.example.groupphase.domain.model.Match
import com.example.groupphase.domain.model.Round
import com.example.groupphase.domain.model.Team
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.time.LocalDate
import javax.inject.Inject

// Determine the order which the teams will play against eah other. A total of 3 rounds will be played
class DetermineMatchesOrderUseCase @Inject constructor() {
    operator fun invoke(teamList: List<Team>): Flow<Resource<List<Round>>> = flow {
        try {
            emit(Resource.Loading())

            val teams = teamList.toMutableList()
            val rounds = mutableListOf<Round>()

            // Total rounds to be played
            val numRounds = 2

            // Create the rounds
            for (i in 0..numRounds) {
                // Create the matches for the first round
                val matches: MutableList<Match> = mutableListOf()

                for (j in 0..1) {
                    val home = teams[j]
                    val away = findOpponent(home, teams, matches)
                    if (away != null) {
                        matches.add(Match(Pair(home, 0), Pair(away, 0), false))
                    }
                }
                rounds.add(Round(matches, LocalDate.now()))
            }

            emit(Resource.Success(rounds))
        } catch (e: Exception) {
            emit(Resource.Error("Er is een fout opgetreden bij het bepalen van de wedstrijdvolgorde: ${e.message}"))
        }
    }

    private fun findOpponent(
        home: Team,
        teams: MutableList<Team>,
        matches: List<Match>
    ): Team? {
        // filter out the teams that have already played against each other
        val potentialOpponents = teams.filter { team ->
            matches.none { match ->
                (match.home.first == team && match.away.first == home) ||
                        (match.home.first == home && match.away.first == team)
            } && team != home // ALSO make sure the team is not the home team
        }

        potentialOpponents.forEach {
            println(it.name)
        }

        return potentialOpponents.firstOrNull()
    }
}