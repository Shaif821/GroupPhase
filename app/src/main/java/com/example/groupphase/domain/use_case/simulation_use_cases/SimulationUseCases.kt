package com.example.groupphase.domain.use_case.simulation_use_cases

data class SimulationUseCases(
    val deleteSimulationUseCase: DeleteSimulationUseCase,
    val insertSimulationUseCase: InsertSimulationUseCase,
    val updateSimulationUseCase: UpdateSimulationUseCase,
    val getAllSimulationUseCase: GetAllSimulationUseCase,
    val getSimulationByDateUseCase: GetSimulationByDateUseCase,
    val getSimulationByWeekUseCase: GetSimulationByWeekUseCase,
    val getSimulationById: GetSimulationByIdUseCase
)
