package com.example.groupphase.domain.use_case.player_use_cases

import com.example.groupphase.common.Resource
import com.example.groupphase.domain.model.Player
import com.example.groupphase.domain.repository.PlayerRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

class DeletePlayerUseCase @Inject constructor(
    private val playerRepository: PlayerRepository
) {
    operator fun invoke(player: Player) : Flow<Resource<Boolean>> = flow {
        try {
            emit(Resource.Loading())
            playerRepository.deletePlayer(player)
            emit(Resource.Success(true))
        } catch (e: Exception) {
            emit(Resource.Error("The following error occurred while deleting the player: ${e.message}"))
        }
    }
}