package com.example.groupphase.domain.use_case.player_use_cases

import com.example.groupphase.common.Resource
import com.example.groupphase.domain.model.Player
import com.example.groupphase.domain.repository.PlayerRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Singleton

@Singleton
class InsertPlayerUseCase(
    private val playerRepository: PlayerRepository
) {
    operator fun invoke(player: Player) : Flow<Resource<Player>> = flow {
        try {
            emit(Resource.Loading())
            playerRepository.insertPlayer(player)
            emit(Resource.Success(player))
        } catch (e: Exception) {
            emit(Resource.Error("The following error occurred while inserting the player: ${e.message}"))
        }
    }
}