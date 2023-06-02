package com.example.groupphase.domain.repository

import com.example.groupphase.domain.model.Team
import kotlinx.coroutines.flow.Flow

interface TeamRepository {
    fun getAllTeams() : Flow<List<Team>>
    fun getTeamById(id: Int) : Flow<Team>
    fun insertTeam(team: Team)
    fun updateTeam(team: Team)
    fun deleteTeam(team: Team)
}