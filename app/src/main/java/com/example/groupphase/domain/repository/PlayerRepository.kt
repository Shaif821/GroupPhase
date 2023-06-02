package com.example.groupphase.domain.repository

import com.example.groupphase.domain.model.Player
import kotlinx.coroutines.flow.Flow

interface PlayerRepository {
    fun getAllPlayers() : Flow<List<Player>>
    fun getPlayerById(id: Int) : Flow<Player>
    fun getPlayersByTeam(teamId: Int) : Flow<List<Player>>
    fun getPlayersByStrength() : Flow<List<Player>>
    fun getPlayersByStrengthAndTeam(teamId: Int) : Flow<List<Player>>
    fun insertPlayer(player: Player)
    fun updatePlayer(player: Player)
    fun deletePlayer(player: Player)
}