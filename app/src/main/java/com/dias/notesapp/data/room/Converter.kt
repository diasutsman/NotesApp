package com.dias.notesapp.data.room

import androidx.room.TypeConverter
import com.dias.notesapp.data.entity.Priority

class Converter {

    @TypeConverter
    fun fromPriority(priority: Priority): String {
        return priority.name
    }

    // ini adalah sebuah converter yang mengonvert string ke enum Priority
    // fungsi ini dipanggil ketika meng-add atau meng-update sebuah data ke dalam database
    @TypeConverter
    fun toPriority(priority: String): Priority {
        return Priority.valueOf(priority)
    }
}