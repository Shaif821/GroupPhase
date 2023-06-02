package com.example.groupphase.domain.use_case.simulation_use_cases

import com.example.groupphase.common.Resource
import com.example.groupphase.domain.model.Match
import com.example.groupphase.domain.model.Round
import com.example.groupphase.domain.model.Simulation
import com.example.groupphase.domain.model.Team
import com.example.groupphase.domain.repository.SimulationRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.time.LocalDate
import javax.inject.Singleton

// Determine the order which the teams will play against eah other. A total of 3 rounds will be played
@Singleton
class DetermineMatchesOrderUseCase() {
    operator fun invoke(teamList: List<Team>) : Flow<Resource<List<Round>>> = flow {
        try {
            emit(Resource.Loading())

            val teams = teamList.toMutableList()
            val rounds = mutableListOf<Round>()

            // total rounds is 3.
            for (roundIndex in 0 until teams.size - 1) {
                val matches = mutableListOf<Match>()

                // The inner loop adds a match to the current round
                for (matchIndex in 0 until teams.size / 2) {
                    val homeTeam = Pair(teams[matchIndex], 0)
                    val awayTeam = Pair(teams[teams.size - 1 - matchIndex], 0)

                    val match = Match(homeTeam, awayTeam)
                    matches.add(match)
                }

                val round = Round(matches, LocalDate.now())
                rounds.add(round)

                // Switch the first team with the last team in the list. This prevents a team from playing against the same team twice
                teams.add(teams.size - 1, teams.removeAt(0))
            }

            emit(Resource.Success(rounds))
        } catch (e: Exception) {
            emit(Resource.Error("The following error occurred while determining the matches order: ${e.message}"))
        }
    }
}