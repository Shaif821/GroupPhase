package com.example.groupphase.data.data_source.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.groupphase.domain.model.Simulation
import kotlinx.coroutines.flow.Flow
import java.util.Date

@Dao
interface SimulationDAO {
    @Query("SELECT * FROM simulation_table")
    fun getAllSimulations(): Flow<List<Simulation>>

    @Query("SELECT * FROM simulation_table WHERE date = :date")
    fun getSimulationByDate(date: Date): Flow<List<Simulation>>

    @Query("SELECT * FROM simulation_table WHERE date BETWEEN :startDate AND :endDate")
    fun getSimulationByWeek(startDate: Date, endDate: Date): Flow<List<Simulation>>

    @Insert
    fun insertSimulation(simulation: Simulation)

    @Update
    fun updateSimulation(simulation: Simulation)

    @Delete
    fun deleteSimulation(simulation: Simulation)
}