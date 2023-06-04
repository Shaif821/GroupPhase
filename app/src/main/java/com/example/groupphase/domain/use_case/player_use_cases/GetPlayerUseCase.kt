package com.example.groupphase.domain.use_case.player_use_cases

import com.example.groupphase.common.Resource
import com.example.groupphase.domain.model.Player
import com.example.groupphase.domain.repository.PlayerRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

class GetPlayerUseCase @Inject constructor(
    private val playerRepository: PlayerRepository
) {
    operator fun invoke(id: Int) : Flow<Resource<Player>> = flow {
        try {
            emit(Resource.Loading())
            val player = playerRepository.getPlayerById(id).first()
            emit(Resource.Success(player))
        } catch (e: Exception) {
            emit(Resource.Error("The following error occurred while retrieving the player: ${e.message}"))
        }
    }
}