package com.example.groupphase.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity(tableName = "simulation_table")
data class Simulation(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val rounds: List<Round>,
    val results: List<Result>,
    val date: LocalDate
)
