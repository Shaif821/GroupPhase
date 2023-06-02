package com.example.groupphase.domain.model

import androidx.room.Entity
import java.time.LocalDate

@Entity(tableName = "round_table")
data class Round(
    val match: List<Match>,
    val date: LocalDate
)
