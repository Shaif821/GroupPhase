package com.example.groupphase.data.data_source.database

import androidx.room.TypeConverter
import com.example.groupphase.domain.model.Player
import com.example.groupphase.domain.model.Result
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.Date

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
    fun fromDate(date: Date): Long {
        return date.time
    }

    @TypeConverter
    fun toDate(timestamp: Long): Date {
        return Date(timestamp)
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
}