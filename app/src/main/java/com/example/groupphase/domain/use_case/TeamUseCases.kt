package com.example.groupphase.domain.use_case

import com.example.groupphase.domain.use_case.team_use_cases.DeleteTeamUseCase
import com.example.groupphase.domain.use_case.team_use_cases.GetAllTeamsUseCase
import com.example.groupphase.domain.use_case.team_use_cases.GetTeamByIdUseCase
import com.example.groupphase.domain.use_case.team_use_cases.InsertTeamUseCase
import com.example.groupphase.domain.use_case.team_use_cases.UpdateTeamUseCase

data class TeamUseCases(
    val getAllTeamsUseCase: GetAllTeamsUseCase,
    val getTeamByIdUseCase: GetTeamByIdUseCase,
    val insertTeamUseCase: InsertTeamUseCase,
    val updateTeamUseCase: UpdateTeamUseCase,
    val deleteTeamUseCase: DeleteTeamUseCase
)
