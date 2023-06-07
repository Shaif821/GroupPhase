package com.example.groupphase.data.data_source.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.groupphase.domain.model.Simulation
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate
import java.util.Date

@Dao
interface SimulationDAO {
    @Query("SELECT * FROM simulation_table")
    fun getAllSimulations(): Flow<List<Simulation>>

    @Query("SELECT * FROM simulation_table WHERE id = :id")
    fun getSimulationById(id: Int): Flow<Simulation>

    @Query("SELECT * FROM simulation_table WHERE date = :date")
    fun getSimulationByDate(date: LocalDate): Flow<List<Simulation>>

    @Query("SELECT * FROM simulation_table WHERE date BETWEEN :startDate AND :endDate")
    fun getSimulationByWeek(startDate: LocalDate, endDate: LocalDate): Flow<List<Simulation>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSimulation(simulation: Simulation) : Long

    @Update
    fun updateSimulation(simulation: Simulation)

    @Delete
    fun deleteSimulation(simulation: Simulation)
}