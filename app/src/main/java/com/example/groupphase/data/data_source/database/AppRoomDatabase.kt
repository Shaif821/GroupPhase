package com.example.groupphase.data.data_source.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.groupphase.data.data_source.dao.PlayerDAO
import com.example.groupphase.data.data_source.dao.SimulationDAO
import com.example.groupphase.data.data_source.dao.TeamDAO
import com.example.groupphase.domain.model.Player
import com.example.groupphase.domain.model.Simulation
import com.example.groupphase.domain.model.Team

@Database(entities = [Simulation::class, Player::class, Team::class], version = 2, exportSchema = false)
@TypeConverters(TypeConverter::class)
abstract class AppRoomDatabase : RoomDatabase() {

        abstract fun simulationDao(): SimulationDAO
        abstract fun playerDao(): PlayerDAO
        abstract fun teamDao(): TeamDAO

        companion object {
            const val DATABASE_NAME = "SIMULATION_DATABASE"

            @Volatile
            private var INSTANCE: AppRoomDatabase? = null
        }
}