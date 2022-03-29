package com.dias.notesapp.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dias.notesapp.data.NotesRepository
import com.dias.notesapp.data.entity.Notes
import com.dias.notesapp.data.room.NotesDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NotesViewModel(application: Application): AndroidViewModel(application) {
    private val notesDao = NotesDatabase.getDatabase(application).notesDao()
    private val repository: NotesRepository = NotesRepository(notesDao)

    fun getAllData(): LiveData<List<Notes>> = repository.getAllData()

    fun sortByHighPriority() : LiveData<List<Notes>> = repository.sortByHighPriority()

    fun sortByLowPriority() : LiveData<List<Notes>> = repository.sortByLowPriority()

    fun deleteAllData() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAllData()
        }
    }

    fun insertData(notes: Notes) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertNotes(notes)
        }
    }

    // search by query
    fun searchByQuery(query: String) : LiveData<List<Notes>> = repository.searchByQuery(query)

    // delete note
    fun deleteNote(notes: Notes) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteNotes(notes)
        }
    }
}