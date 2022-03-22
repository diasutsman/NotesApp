package com.dias.notesapp.data.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dias.notesapp.data.entity.Notes

@Dao
interface NotesDao {
    /*
     * jika ada data yang sama persis maka biarkan saja masuk datanya
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertNotes(notes: Notes)

    @Query("SELECT * FROM Notes")
    fun getAllData() : LiveData<List<Notes>>
}