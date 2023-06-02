package com.example.groupphase.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "player_table")
data class Player(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    val strength: Double,
)
