package com.example.groupphase.data.data_source.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.groupphase.data.data_source.dao.SimulationDAO
import com.example.groupphase.domain.model.Simulation

@Database(entities = [Simulation::class], version = 1, exportSchema = false)
@TypeConverters(TypeConverter::class)
abstract class AppRoomDatabase : RoomDatabase() {

        abstract fun simulationDao(): SimulationDAO

        companion object {
            const val DATABASE_NAME = "SIMULATION_DATABASE"

            @Volatile
            private var INSTANCE: AppRoomDatabase? = null
        }
}