package com.example.groupphase.data.repository

import com.example.groupphase.data.data_source.dao.SimulationDAO
import com.example.groupphase.domain.model.Simulation
import com.example.groupphase.domain.repository.SimulationRepository
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate
import javax.inject.Inject

class SimulationRepositoryImpl @Inject constructor(
    private val simulationDao: SimulationDAO
) : SimulationRepository {
    override fun getAllSimulation(): Flow<List<Simulation>> {
        return simulationDao.getAllSimulations()
    }

    override fun getSimulationById(id: Int): Flow<Simulation> {
        return simulationDao.getSimulationById(id)
    }

    override fun getSimulationByDate(date: LocalDate): Flow<List<Simulation>> {
        return simulationDao.getSimulationByDate(date)
    }

    override fun getSimulationByWeek(startDate: LocalDate, endDate: LocalDate): Flow<List<Simulation>> {
        return simulationDao.getSimulationByWeek(startDate, endDate)
    }

    override fun insertSimulation(simulation: Simulation) {
        simulationDao.insertSimulation(simulation)
    }

    override fun updateSimulation(simulation: Simulation) {
        simulationDao.updateSimulation(simulation)
    }

    override fun deleteSimulation(simulation: Simulation) {
        simulationDao.deleteSimulation(simulation)
    }
}