package com.example.groupphase.domain.use_case.player_use_cases

import com.example.groupphase.common.Resource
import com.example.groupphase.domain.model.Player
import com.example.groupphase.domain.repository.PlayerRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import javax.inject.Singleton

@Singleton
class GetPlayersByStrengthAndTeamUseCase(
    private val playerRepository: PlayerRepository
) {
    operator fun invoke(id: Int) : Flow<Resource<List<Player>>> = flow {
        try {
            emit(Resource.Loading())
            val playerList = playerRepository.getPlayersByStrengthAndTeam(id).first()
            emit(Resource.Success(playerList))
        } catch (e: Exception) {
            emit(Resource.Error(
                "The following error occurred while retrieving the players" +
                        " ordered by strength from a specific team: ${e.message}"))
        }
    }
}