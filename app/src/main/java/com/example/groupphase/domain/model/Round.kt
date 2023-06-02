package com.example.groupphase.domain.model

import androidx.room.Entity

@Entity(tableName = "round_table")
data class Round(
    val scores: List<Score>
)
