package com.example.groupphase.domain.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity(tableName = "simulation_table")
data class Simulation(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") var id: Long,
    val rounds: List<Round>,
    val results: List<Result>,
    val date: LocalDate
)
