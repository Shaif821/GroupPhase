package com.example.groupphase.domain.use_case.team_use_cases

import com.example.groupphase.common.Resource
import com.example.groupphase.domain.model.Team
import com.example.groupphase.domain.repository.TeamRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import javax.inject.Singleton

@Singleton
class GetAllTeamsUseCase(
    private val teamRepository: TeamRepository
) {
    operator fun invoke() : Flow<Resource<List<Team>>> = flow {
        try {
            emit(Resource.Loading())
            val teams = teamRepository.getAllTeams().first()
            emit(Resource.Success(teams))
        } catch (e: Exception) {
            emit(Resource.Error("The following error occurred while retrieving the teams: ${e.message}"))
        }
    }
}