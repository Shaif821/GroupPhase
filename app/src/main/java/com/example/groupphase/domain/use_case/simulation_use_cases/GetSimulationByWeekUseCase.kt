package com.example.groupphase.domain.use_case.simulation_use_cases

import com.example.groupphase.common.Resource
import com.example.groupphase.domain.model.Simulation
import com.example.groupphase.domain.repository.SimulationRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import java.util.Date
import javax.inject.Singleton

@Singleton
class GetSimulationByWeekUseCase(
    private val simulateRepository: SimulationRepository
) {
    operator fun invoke(startDate: Date, endDate: Date): Flow<Resource<List<Simulation>>> = flow {
        try {
            emit(Resource.Loading())
            val simulationList = simulateRepository.getSimulationByWeek(startDate, endDate).first()
            emit(Resource.Success(simulationList))
        }  catch (e: Exception) {
            emit(Resource.Error("The following error occurred while getting the simulation: ${e.message}"))
        }
    }
}
