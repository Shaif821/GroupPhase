package com.example.groupphase.domain.repository

import com.example.groupphase.domain.model.Simulation
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate
import java.util.Date

interface SimulationRepository {
    fun getAllSimulation(): Flow<List<Simulation>>
    fun getSimulationById(id: Int): Flow<Simulation>
    fun getSimulationByDate(date: LocalDate): Flow<List<Simulation>>
    fun getSimulationByWeek(startDate: LocalDate, endDate: LocalDate): Flow<List<Simulation>>
    fun insertSimulation(simulation: Simulation)
    fun updateSimulation(simulation: Simulation)
    fun deleteSimulation(simulation: Simulation)
}