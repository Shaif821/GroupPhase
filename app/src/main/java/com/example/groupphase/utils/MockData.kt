package com.example.groupphase.utils

import com.example.groupphase.domain.model.Player
import com.example.groupphase.domain.model.Team

class MockData {
    // mock data team
    fun mockTeams() : List<Team> {
        val teams = mutableListOf<Team>()
        val trackHomeTownName = mutableListOf<String>()
        // create 4 teams with 11 players each
        for (i in 1..4) {
            var homeTown = randomizeHometown()
            // make sure hometowns are unique
            while (trackHomeTownName.contains(homeTown)) {
                homeTown = randomizeHometown()
            }
            teams.add(Team(i, "Team $i", homeTown, createMockPlayers(teamId = i)))
        }
        return teams
    }

    // mock data player
    private fun createMockPlayers(teamId: Int): List<Player> {
        val players = mutableListOf<Player>()
        for (i in 1..11) {
            // randomize between 30 and 100
            val randomStrength = (0..100).random()
            players.add(Player(i, teamId, "Player $i", randomStrength))
        }
        return players
    }

    // randomize hometown for team
    private fun randomizeHometown(): String {
        val hometowns = listOf("Amsterdam", "Rotterdam", "Den Haag", "Utrecht", "Eindhoven", "Tilburg", "Groningen", "Almere", "Breda", "Nijmegen", "Enschede", "Haarlem", "Arnhem", "Zaanstad", "Amersfoort", "Schiphol", "Hoofddorp", "Nieuw - Vennep")
        return hometowns.random()
    }
}