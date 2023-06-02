package com.example.groupphase.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "simulation_table")
data class Simulation(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val results: List<Result>,
    val date: Date,
)
