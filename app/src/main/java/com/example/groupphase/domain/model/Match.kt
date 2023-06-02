package com.example.groupphase.domain.model

import androidx.room.Entity
import java.time.LocalDate
import java.util.Date

@Entity(tableName = "match_table")
data class Match(
    val home: Team,
    val away: Team,
    val score: List<Score>,
    val date: LocalDate
)
