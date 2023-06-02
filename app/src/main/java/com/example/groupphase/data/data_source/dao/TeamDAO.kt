package com.example.groupphase.data.data_source.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.groupphase.domain.model.Team
import kotlinx.coroutines.flow.Flow

@Dao
interface TeamDAO {
    @Query("SELECT * FROM team_table")
    fun getAllTeams(): Flow<List<Team>>

    // get team by id
    @Query("SELECT * FROM team_table WHERE id = :id")
    fun getTeamById(id: Int): Flow<Team>

    @Insert
    fun insertTeam(team: Team)

    @Update
    fun updateTeam(team: Team)

    @Delete
    fun deleteTeam(team: Team)
}