package com.example.groupphase.domain.model

import androidx.room.Entity
import java.time.LocalDate
import java.util.Date

@Entity
data class Result(
    var position: Int,
    val team: Team,
    val played: Int,
    val won: Int,
    val drawn: Int,
    val lost: Int,
    val goalsFor: Int,
    val goalsAgainst: Int,
    val goalDifference: Int,
    val points: Int,
    val date: LocalDate
)
