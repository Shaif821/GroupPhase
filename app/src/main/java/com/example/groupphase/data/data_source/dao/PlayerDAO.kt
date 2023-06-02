package com.example.groupphase.data.data_source.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.groupphase.domain.model.Player
import kotlinx.coroutines.flow.Flow

@Dao
interface PlayerDAO {
    @Query("SELECT * FROM player_table")
    fun getAllPlayers(): Flow<List<Player>>

    @Query("SELECT * FROM player_table WHERE id = :id")
    fun getPlayerById(id: Int): Flow<Player>

    // get player from team
    @Query("SELECT * FROM player_table WHERE team_id = :teamId")
    fun getPlayerByTeam(teamId: Int): Flow<List<Player>>

    // get players ordered by strength
    @Query("SELECT * FROM player_table ORDER BY strength DESC")
    fun getPlayersByStrength(): Flow<List<Player>>

    // get players ordered by strength from team
    @Query("SELECT * FROM player_table WHERE team_id = :teamId ORDER BY strength DESC")
    fun getPlayersByStrengthAndTeam(teamId: Int): Flow<List<Player>>

    @Insert
    fun insertPlayer(player: Player)

    @Update
    fun updatePlayer(player: Player)

    @Delete
    fun deletePlayer(player: Player)
}