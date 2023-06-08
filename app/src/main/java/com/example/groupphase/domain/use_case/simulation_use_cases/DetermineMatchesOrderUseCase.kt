package com.example.groupphase.domain.use_case.simulation_use_cases

import com.example.groupphase.common.Resource
import com.example.groupphase.domain.model.Match
import com.example.groupphase.domain.model.Round
import com.example.groupphase.domain.model.Team
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.time.LocalDate
import javax.inject.Inject

// Determine the order in which the teams will play against each other. A total of 3 rounds will be played.
class DetermineMatchesOrderUseCase @Inject constructor() {
    operator fun invoke(teamList: List<Team>): Flow<Resource<List<Round>>> = flow {
        try {
            emit(Resource.Loading())

            val teams = teamList.toMutableList()
            val rounds = mutableListOf<Round>()
            val teamsPlayed = mutableListOf<Pair<Team, Team>>()

            // Total rounds to be played
            val numRounds = 2

            // Create the rounds
            for (i in 0..numRounds) {
                // Create the matches for the round
                val matches: MutableList<Match> = mutableListOf()

                for (j in 0..2) {
                    val homeIndex = if (j == 0) 0 else (2)

                    val home = teams[homeIndex]
                    val away = findOpponent(home, teams, teamsPlayed, matches)

                    if (away != null) {
                        teamsPlayed.add(Pair(away, home))
                        teamsPlayed.add(Pair(home, away))
                        matches.add(Match(Pair(home, 0), Pair(away, 0), false))
                    }
                }

                rounds.add(Round(matches, LocalDate.now()))

                // Shift the teams order for the next round
                val lastTeam = teams.removeAt(teams.size - 1)
                teams.add(1, lastTeam)
            }

            emit(Resource.Success(rounds))
        } catch (e: Exception) {
            emit(Resource.Error("Er is een fout opgetreden bij het bepalen van de wedstrijdvolgorde: ${e.message}"))
        }
    }

    private fun findOpponent(
        home: Team,
        teams: MutableList<Team>,
        teamsPlayed: MutableList<Pair<Team, Team>>,
        matches: MutableList<Match>
    ): Team? {
        // Filter out the teams that have already played against each other
        var potentialOpponents = teams.filter { team ->
            // Check if any pair in teamsPlayed contains the home team and the current team
            val playedTogether = teamsPlayed.any { it.first == home && it.second == team || it.first == team && it.second == home }
            // Exclude the home team and teams that have already played against the home team
            team != home && !playedTogether
        }

        // Get all teams from matches (the teams from the first and second positions)
        val teamsFromMatches = matches.flatMap { listOf(it.home.first, it.away.first) }

        // Remove the teams from matches from the potential opponents
        potentialOpponents = potentialOpponents.filter { !teamsFromMatches.contains(it) }

        return potentialOpponents.firstOrNull()
    }
}
