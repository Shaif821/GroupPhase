package com.example.groupphase.domain.use_case.simulation_use_cases

import com.example.groupphase.common.Resource
import com.example.groupphase.domain.model.Simulation
import com.example.groupphase.domain.repository.SimulationRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

class GetSimulationByIdUseCase @Inject constructor(
    private val simulateRepository: SimulationRepository
) {
    operator fun invoke(id: Long): Flow<Resource<Simulation>> = flow {
        try {
            emit(Resource.Loading())
            val simulationList = simulateRepository.getSimulationById(id).first()
            emit(Resource.Success(simulationList))
        }  catch (e: Exception) {
            emit(Resource.Error("The following error occurred while getting the simulation: ${e.message}"))
        }
    }
}
