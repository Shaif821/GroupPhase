package com.example.groupphase.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "team_table")
data class Team(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    val players: Player,
)