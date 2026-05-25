package com.psoft.tetrisgame.data.database

import androidx.room.TypeConverter

class Converters {
    @TypeConverter
    fun fromIntArray(array: IntArray): String {
        return array.joinToString(",")
    }

    @TypeConverter
    fun toIntArray(data: String): IntArray {
        return if (data.isEmpty()) intArrayOf() else data.split(",").map { it.toInt() }.toIntArray()
    }
}