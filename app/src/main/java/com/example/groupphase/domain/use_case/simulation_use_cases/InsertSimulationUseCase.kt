package com.example.groupphase.domain.use_case.simulation_use_cases

import com.example.groupphase.common.Resource
import com.example.groupphase.domain.model.Simulation
import com.example.groupphase.domain.repository.SimulationRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Singleton

@Singleton
class InsertSimulationUseCase(
    private val simulateRepository: SimulationRepository
) {
    operator fun invoke(simulation: Simulation) : Flow<Resource<Simulation>> = flow {
        try {
            emit(Resource.Loading())
            simulateRepository.insertSimulation(simulation)
            emit(Resource.Success(simulation))
        } catch (e: Exception) {
            emit(Resource.Error("The following error occurred while inserting the simulation: ${e.message}"))
        }
    }
}