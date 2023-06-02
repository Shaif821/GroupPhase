package com.example.groupphase.data.repository

import com.example.groupphase.data.data_source.dao.PlayerDAO
import com.example.groupphase.domain.model.Player
import com.example.groupphase.domain.repository.PlayerRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PlayerRepositoryImpl @Inject constructor(
    private val playerDao: PlayerDAO
) : PlayerRepository {
    override fun getAllPlayers(): Flow<List<Player>> {
        return playerDao.getAllPlayers()
    }

    override fun getPlayerById(id: Int): Flow<Player> {
        return playerDao.getPlayerById(id)
    }

    override fun getPlayersByTeam(teamId: Int): Flow<List<Player>> {
        return playerDao.getPlayerByTeam(teamId)
    }

    override fun getPlayersByStrength(): Flow<List<Player>> {
        return playerDao.getPlayersByStrength()
    }

    override fun getPlayersByStrengthAndTeam(teamId: Int): Flow<List<Player>> {
        return playerDao.getPlayersByStrengthAndTeam(teamId)
    }

    override fun insertPlayer(player: Player) {
        playerDao.insertPlayer(player)
    }

    override fun updatePlayer(player: Player) {
        playerDao.updatePlayer(player)
    }

    override fun deletePlayer(player: Player) {
        playerDao.deletePlayer(player)
    }
}