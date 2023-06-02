package com.example.groupphase.domain.model

import androidx.room.Entity
import java.time.LocalDate
import java.util.Date

@Entity(tableName = "match_table")
data class Match(
    var home: Pair<Team, Int>,
    var away: Pair<Team, Int>,
)
