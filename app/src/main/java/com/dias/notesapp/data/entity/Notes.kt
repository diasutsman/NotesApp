package com.dias.notesapp.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

// anotasi entity yang digunakan untuk menandakan kalau dataclass ini adalah skema untuk per 1 row-nya
@Entity
data class Notes(
    // untuk id didalam table supaya tidak duplikat
    @PrimaryKey(autoGenerate = true) val id: Int,
    var title: String,
    var priority: Priority,
    var description: String,
    var date: String,
)
