package com.dias.notesapp.data

import androidx.lifecycle.LiveData
import com.dias.notesapp.data.entity.Notes
import com.dias.notesapp.data.room.NotesDao
import kotlinx.coroutines.coroutineScope

class NotesRepository(private val notesDao: NotesDao) {
    // fungsi ini untuk insert/add data ke database
    suspend fun insertNotes(notes: Notes) {
        notesDao.insertNotes(notes)
    }

    fun getAllData(): LiveData<List<Notes>> = notesDao.getAllData()

    fun sortByHighPriority() : LiveData<List<Notes>> = notesDao.sortByHighPriority()

    fun sortByLowPriority() : LiveData<List<Notes>> = notesDao.sortByLowPriority()

    suspend fun deleteAllData() = notesDao.deleteAllData()

    fun searchByQuery(query: String) : LiveData<List<Notes>> = notesDao.searchByQuery(query)

    suspend fun deleteNotes(notes: Notes) = notesDao.deleteNotes(notes)
}