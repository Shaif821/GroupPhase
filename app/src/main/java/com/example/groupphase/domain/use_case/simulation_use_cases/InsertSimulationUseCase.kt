package com.example.groupphase.domain.use_case.simulation_use_cases

import com.example.groupphase.common.Resource
import com.example.groupphase.domain.model.Simulation
import com.example.groupphase.domain.repository.SimulationRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

class InsertSimulationUseCase @Inject constructor(
    private val simulateRepository: SimulationRepository
) {
    operator fun invoke(simulation: Simulation) : Flow<Resource<Long>> = flow {
        try {
            emit(Resource.Loading())
            val inserted = simulateRepository.insertSimulation(simulation)
            emit(Resource.Success(inserted))
        } catch (e: Exception) {
            emit(Resource.Error("The following error occurred while inserting the simulation: ${e.message}"))
        }
    }
}