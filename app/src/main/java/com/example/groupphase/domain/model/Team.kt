package com.example.groupphase.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "team_table")
data class Team(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val name: String,
    val players: List<Player>,
)


// mock data team
fun mockTeams() : List<Team> {
    val teams = mutableListOf<Team>()
    // create 4 teams with 11 players each
    for (i in 1..4) {
        teams.add(Team(i, "Team $i", createMockPlayers(teamId = i)))
    }
    return teams
}

// mock data player
fun createMockPlayers(teamId: Int): List<Player> {
    val players = mutableListOf<Player>()
    for (i in 1..11) {
        // randomize between 0.0 and 1.0
        val randomStrength = (0..100).random() / 100.0
        players.add(Player(i, teamId, "Player $i", randomStrength))
    }
    return players
}