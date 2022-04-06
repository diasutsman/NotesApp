package com.dias.notesapp.data.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

// anotasi entity yang digunakan untuk menandakan kalau dataclass ini adalah skema untuk per 1 row-nya
@Entity
// anotasi parcelize untuk membuat class ini bisa di kirimkan ke activity atau fragment lain
@Parcelize
data class Notes(
    // untuk id didalam table supaya tidak duplikat
    @PrimaryKey(autoGenerate = true) val id: Int,
    var title: String,
    var priority: Priority,
    var description: String,
    var date: String,
) : Parcelable
