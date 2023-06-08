package com.example.groupphase.domain.use_case.simulation_use_cases

import com.example.groupphase.common.Resource
import com.example.groupphase.domain.model.Match
import com.example.groupphase.domain.model.Round
import com.example.groupphase.domain.model.Team
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.time.LocalDate
import javax.inject.Inject
import javax.inject.Singleton

// Determine the order which the teams will play against eah other. A total of 3 rounds will be played
class DetermineMatchesOrderUseCase @Inject constructor() {
    operator fun invoke(teamList: List<Team>): Flow<Resource<List<Round>>> = flow {
        try {
            emit(Resource.Loading())

            val teams = teamList.toMutableList()
            val rounds = mutableListOf<Round>()

            // Total rounds to be played
            val numRounds = 3

            // keep track of the played matches to prevent duplicate matches
            val playedMatches = mutableSetOf<Pair<Team, Team>>()

            // Each round has 2 matches
            for (i in 1..numRounds) {
                val matches = mutableListOf<Match>()

                // Make copy of teams list
                val teamsCopy = teams.toMutableList()


                for (j in 0..1) {
                    val home = teamsCopy.first()
                    teamsCopy.removeAt(0)

                    // Find opponent for the home team
                    val away = findOpponent(home, teamsCopy, playedMatches)

                    if (away != null) {
                        val match = Match(Pair(home, 0), Pair(away, 0))
                        matches.add(match)

                        // add the match to the played matches
                        playedMatches.add(Pair(home, away))
                    }
                }

                rounds.add(Round(matches, LocalDate.now()))

                // Rotate the teams
                // first team becomes last team
                val first = teams.first()
                teams.removeAt(0)
                teams.add(first)
            }
            emit(Resource.Success(rounds))
        } catch (e: Exception) {
            emit(Resource.Error("Er is een fout opgetreden bij het bepalen van de wedstrijdvolgorde: ${e.message}"))
        }
    }

    private fun findOpponent(
        team: Team,
        teams: MutableList<Team>,
        playedMatches: Set<Pair<Team, Team>>
    ): Team? {
        val potentialOpponents = teams.filter { otherTeam ->
            !playedMatches.contains(Pair(team, otherTeam)) ||
                    !playedMatches.contains(Pair(otherTeam, team))
        }

        return potentialOpponents.randomOrNull()
    }
}