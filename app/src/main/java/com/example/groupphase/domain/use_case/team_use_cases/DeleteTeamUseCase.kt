package com.example.groupphase.domain.use_case.team_use_cases

import com.example.groupphase.common.Resource
import com.example.groupphase.domain.model.Team
import com.example.groupphase.domain.repository.TeamRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

class DeleteTeamUseCase @Inject constructor(
    private val teamRepository: TeamRepository
) {
    operator fun invoke(team: Team) : Flow<Resource<Boolean>> = flow {
        try {
            emit(Resource.Loading())
            teamRepository.deleteTeam(team)
            emit(Resource.Success(true))
        } catch (e: Exception) {
            emit(Resource.Error("The following error occurred while deleting the team: ${e.message}"))
        }
    }
}