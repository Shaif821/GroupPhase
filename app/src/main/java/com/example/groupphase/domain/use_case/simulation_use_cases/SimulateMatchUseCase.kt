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

// Determine the order which the teams will play against eah other. A total of 3 rounds will be played
class SimulateMatchUseCase @Inject constructor() {
    operator fun invoke(match: Match): Flow<Resource<Match>> = flow {
        try {
            emit(Resource.Loading())

            val homeStrength = calculatStrenght(match.home.first.players)
            val awayStrength = calculatStrenght(match.away.first.players)

            // calculate the score. The score is based on the strength of the team. But is randomized a bit
            // TODO: make the randomization more realistic (and make it actually work)

            // update the score in the temp match (since this is an invoke function, the match will be updated in the tempMatch)
            val updatedMatch = match
//                .copy(
//                    home = match.home.copy(second = homeStrength),
//                    away = match.away.copy(second = awayStrength),
//                )
            emit(Resource.Success(updatedMatch))
        } catch (e: Exception) {
            emit(Resource.Error("The following went wrong while simulating the match: ${e.message}"))
        }
    }

    private fun calculatStrenght(players: List<Player>): Int {
        var strength = 0.0
        players.forEach { player ->
            strength += player.strength
        }
        return strength.toInt()
    }
}