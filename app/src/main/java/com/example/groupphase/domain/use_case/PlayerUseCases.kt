package com.example.groupphase.domain.use_case

import com.example.groupphase.domain.use_case.player_use_cases.DeletePlayerUseCase
import com.example.groupphase.domain.use_case.player_use_cases.GetAllPlayersUseCase
import com.example.groupphase.domain.use_case.player_use_cases.GetPlayerUseCase
import com.example.groupphase.domain.use_case.player_use_cases.GetPlayersByStrengthAndTeamUseCase
import com.example.groupphase.domain.use_case.player_use_cases.GetPlayersByStrengthUseCase
import com.example.groupphase.domain.use_case.player_use_cases.GetPlayersByTeamUseCase
import com.example.groupphase.domain.use_case.player_use_cases.InsertPlayerUseCase
import com.example.groupphase.domain.use_case.player_use_cases.UpdatePlayerUseCase
import com.example.groupphase.domain.use_case.simulation_use_cases.DeleteSimulationUseCase
import com.example.groupphase.domain.use_case.simulation_use_cases.GetAllSimulationUseCase
import com.example.groupphase.domain.use_case.simulation_use_cases.GetSimulationByDateUseCase
import com.example.groupphase.domain.use_case.simulation_use_cases.GetSimulationByIdUseCase
import com.example.groupphase.domain.use_case.simulation_use_cases.GetSimulationByWeekUseCase
import com.example.groupphase.domain.use_case.simulation_use_cases.InsertSimulationUseCase
import com.example.groupphase.domain.use_case.simulation_use_cases.UpdateSimulationUseCase

data class PlayerUseCases(
    val deletePlayerUseCase: DeletePlayerUseCase,
    val insertPlayerUseCase: InsertPlayerUseCase,
    val updatePlayerUseCase: UpdatePlayerUseCase,
    val getAllPlayerUseCase: GetAllPlayersUseCase,
    val getPlayer: GetPlayerUseCase,
    val getPlayersByTeamUseCase: GetPlayersByTeamUseCase,
    val getPlayersByStrengthUseCase: GetPlayersByStrengthUseCase,
    val getPlayersByStrengthAndTeamUseCase: GetPlayersByStrengthAndTeamUseCase
)
