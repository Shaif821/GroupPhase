package com.example.groupphase.domain.use_case.player_use_cases

import com.example.groupphase.common.Resource
import com.example.groupphase.domain.model.Player
import com.example.groupphase.domain.repository.PlayerRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import javax.inject.Singleton

@Singleton
class GetPlayersByStrengthUseCase(
    private val playerRepository: PlayerRepository
) {
    operator fun invoke() : Flow<Resource<List<Player>>> = flow {
        try {
            emit(Resource.Loading())
            val playerList = playerRepository.getPlayersByStrength().first()
            emit(Resource.Success(playerList))
        } catch (e: Exception) {
            emit(Resource.Error("The following error occurred while retrieving the players by strength: ${e.message}"))
        }
    }
}