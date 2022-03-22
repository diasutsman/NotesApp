package com.dias.notesapp.data

import androidx.lifecycle.LiveData
import com.dias.notesapp.data.entity.Notes
import com.dias.notesapp.data.room.NotesDao

class NotesRepository(private val notesDao: NotesDao) {
    // fungsi ini untuk insert/add data ke database
    suspend fun insertNotes(notes: Notes) {
        notesDao.insertNotes(notes)
    }

    fun getAllData(): LiveData<List<Notes>> = notesDao.getAllData()
}