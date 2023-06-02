package com.example.groupphase.domain.model

import androidx.room.Entity
import java.util.Date

@Entity
data class Result(
    val position: Int,
    val team: Team,
    val played: Int,
    val won: Int,
    val drawn: Int,
    val lost: Int,
    val goalsFor: Int,
    val goalsAgainst: Int,
    val goalDifference: Int,
    val points: Int,
    val date: Date,
)
