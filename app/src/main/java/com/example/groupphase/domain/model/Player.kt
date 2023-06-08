package com.example.groupphase.domain.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "player_table")
data class Player(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    @ColumnInfo(name = "team_id") val teamId: Int,
    val name: String,
    val strength: Int,
)
