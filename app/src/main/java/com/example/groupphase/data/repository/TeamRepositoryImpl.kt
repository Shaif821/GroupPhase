package com.example.groupphase.data.repository

import com.example.groupphase.data.data_source.dao.TeamDAO
import com.example.groupphase.domain.model.Team
import com.example.groupphase.domain.repository.TeamRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TeamRepositoryImpl @Inject constructor(
    private val teamDAO: TeamDAO
) : TeamRepository {
    override fun getAllTeams(): Flow<List<Team>> {
        return teamDAO.getAllTeams()
    }

    override fun getTeamById(id: Int): Flow<Team> {
        return teamDAO.getTeamById(id)
    }

    override fun insertTeam(team: Team) {
        teamDAO.insertTeam(team)
    }

    override fun updateTeam(team: Team) {
        teamDAO.updateTeam(team)
    }

    override fun deleteTeam(team: Team) {
        teamDAO.deleteTeam(team)
    }
}