package com.example.groupphase.domain.use_case.simulation_use_cases

import com.example.groupphase.common.Resource
import com.example.groupphase.domain.model.Simulation
import com.example.groupphase.domain.repository.SimulationRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Singleton

@Singleton
class DeleteSimulationUseCase(
    private val simulateRepository: SimulationRepository
) {
    operator fun invoke(simulation: Simulation) : Flow<Resource<Boolean>> = flow {
        try {
            emit(Resource.Loading())
            simulateRepository.deleteSimulation(simulation)
            emit(Resource.Success(true))
        } catch (e: Exception) {
            emit(Resource.Error("The following error occurred while deleting the simulation: ${e.message}"))
        }
    }
}