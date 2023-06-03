package com.example.groupphase.domain.use_case

import com.example.groupphase.domain.use_case.simulation_use_cases.DeleteSimulationUseCase
import com.example.groupphase.domain.use_case.simulation_use_cases.DetermineMatchesOrderUseCase
import com.example.groupphase.domain.use_case.simulation_use_cases.GetAllSimulationUseCase
import com.example.groupphase.domain.use_case.simulation_use_cases.GetSimulationByDateUseCase
import com.example.groupphase.domain.use_case.simulation_use_cases.GetSimulationByIdUseCase
import com.example.groupphase.domain.use_case.simulation_use_cases.GetSimulationByWeekUseCase
import com.example.groupphase.domain.use_case.simulation_use_cases.InsertSimulationUseCase
import com.example.groupphase.domain.use_case.simulation_use_cases.SimulateMatchUseCase
import com.example.groupphase.domain.use_case.simulation_use_cases.UpdateSimulationUseCase

data class SimulationUseCases(
    // Database access objects
    val deleteSimulationUseCase: DeleteSimulationUseCase,
    val insertSimulationUseCase: InsertSimulationUseCase,
    val updateSimulationUseCase: UpdateSimulationUseCase,
    val getAllSimulationUseCase: GetAllSimulationUseCase,
    val getSimulationByDateUseCase: GetSimulationByDateUseCase,
    val getSimulationByWeekUseCase: GetSimulationByWeekUseCase,
    val getSimulationByIdUseCase: GetSimulationByIdUseCase,
    // Complex use case
    val determineMatchesOrderUseCase: DetermineMatchesOrderUseCase,
    val simulateMatchUseCase: SimulateMatchUseCase
)
