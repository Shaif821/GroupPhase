package com.example.groupphase.domain.model

import androidx.room.Entity
import java.util.Date

@Entity(tableName = "match_table")
data class Match(
    val rounds: List<Round>,
    val home: Team,
    val away: Team,
    val date: Date,
)
