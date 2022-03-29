package com.dias.notesapp.data.room

import androidx.lifecycle.LiveData
import androidx.room.*
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

    @Query("SELECT * FROM Notes ORDER BY CASE " +
            "WHEN priority = 'HIGH' THEN 1 " +
            "WHEN priority = 'MEDIUM' THEN 2 " +
            "WHEN priority = 'LOW' THEN 3 " +
            "END")
    fun sortByHighPriority() : LiveData<List<Notes>>

    @Query("SELECT * FROM Notes ORDER BY CASE " +
            "WHEN priority = 'LOW' THEN 1 " +
            "WHEN priority = 'MEDIUM' THEN 2 " +
            "WHEN priority = 'HIGH' THEN 3 " +
            "END")
    fun sortByLowPriority() : LiveData<List<Notes>>

    @Query("DELETE FROM Notes")
    suspend fun deleteAllData()

    @Query("SELECT * FROM Notes WHERE title LIKE :query")
    fun searchByQuery(query: String) : LiveData<List<Notes>>

    @Delete
    suspend fun deleteNotes(notes: Notes)
}