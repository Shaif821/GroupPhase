package com.example.groupphase.data.data_source.database

import androidx.room.TypeConverter
import com.example.groupphase.domain.model.Match
import com.example.groupphase.domain.model.Player
import com.example.groupphase.domain.model.Result
import com.example.groupphase.domain.model.Round
import com.example.groupphase.domain.model.Team
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.time.LocalDate

class TypeConverter {
    @TypeConverter
    fun fromString(value: String): List<Result> {
        val listType = object : TypeToken<List<Result>>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun toString(list: List<Result>): String {
        return Gson().toJson(list)
    }

    @TypeConverter
    fun fromTimestamp(value: Long?): LocalDate? {
        return value?.let { LocalDate.ofEpochDay(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: LocalDate?): Long? {
        return date?.toEpochDay()
    }

    @TypeConverter
    fun localDateToString(date: LocalDate): String {
        return date.toString()
    }

    @TypeConverter
    fun stringToLocalDate(dateString: String): LocalDate {
        return LocalDate.parse(dateString)
    }

    @TypeConverter
    fun fromPlayersList(players: List<Player>): String {
        return Gson().toJson(players)
    }

    @TypeConverter
    fun toPlayersList(playersJson: String): List<Player> {
        val listType = object : TypeToken<List<Player>>() {}.type
        return Gson().fromJson(playersJson, listType)
    }

    @TypeConverter
    fun fromTeamList(teams: List<Team>): String {
        return Gson().toJson(teams)
    }

    @TypeConverter
    fun toTeamList(teamsJson: String): List<Team> {
        val listType = object : TypeToken<List<Team>>() {}.type
        return Gson().fromJson(teamsJson, listType)
    }

    @TypeConverter
    fun fromRoundList(rounds: List<Round>): String {
        return Gson().toJson(rounds)
    }

    @TypeConverter
    fun toRoundList(roundsJson: String): List<Round> {
        val listType = object : TypeToken<List<Round>>() {}.type
        return Gson().fromJson(roundsJson, listType)
    }
}