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
            val numRounds = 3

            // Create the rounds
            for (i in 0 until numRounds) {
                // Create the matches for the round
                val matches: MutableList<Match> = mutableListOf()
                println("-----------Round ${i + 1}------------")
                teams.forEach { println(it.name) }
                println("---")

                for (j in 0 until 2) {
                    println("First team in list: ${teams[0].name}")

                    val homeIndex = if (j == 0) 0 else (teams.size - 1)

                    println("First team after index: ${teams[homeIndex].name}")

                    val home = teams[homeIndex]
                    val away = findOpponent(home, teams, teamsPlayed, matches)

                    if (away != null) {
                        teamsPlayed.add(Pair(away, home))
                        teamsPlayed.add(Pair(home, away))
                        matches.add(Match(Pair(home, 0), Pair(away, 0), false))
                    }

                    println("Round ${i + 1} - Match ${j + 1}: ${home.name} - ${away?.name}")
                    println("\n")
                }

                rounds.add(Round(matches, LocalDate.now()))

                // Shift the teams order for the next round
                val lastTeam = teams.removeAt(teams.size - 1)
                teams.add(1, lastTeam)
            }

            rounds.forEach { round ->
                round.match.forEach { match ->
                    println("${match.home.first.name} - ${match.away.first.name}")
                }
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
        teamsPlayed.forEach {
            println("Teams played: ${it.first.name} - ${it.second.name}")
        }
        println("\n")

        // Filter out the teams that have already played against each other
        var potentialOpponents = teams.filter { team ->
            // Check if any pair in teamsPlayed contains the home team and the current team
            val playedTogether = teamsPlayed.any { it.first == home && it.second == team || it.first == team && it.second == home }
            // Exclude the home team and teams that have already played against the home team
            team != home && !playedTogether
        }

        println("Potential-------------------")
        potentialOpponents.forEach { team ->
            println(team.name)
        }
        println("\n")

        // Get all teams from matches (the teams from the first and second positions)
        val teamsFromMatches = matches.flatMap { listOf(it.home.first, it.away.first) }
        println("Matches that have already been played-------")
        teamsFromMatches.forEach { match ->
            println(match.name)
        }
        println("\n")

        // Remove the teams from matches from the potential opponents
        potentialOpponents = potentialOpponents.filter { !teamsFromMatches.contains(it) }

        println("After removing-------------------")
        potentialOpponents.forEach { team ->
            println(team.name)
        }
        println("\n")

        println("-------------------")

        return potentialOpponents.firstOrNull()
    }
}
