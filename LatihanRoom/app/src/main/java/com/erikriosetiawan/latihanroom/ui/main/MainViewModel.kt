package com.erikriosetiawan.latihanroom.ui.main

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.erikriosetiawan.latihanroom.database.Note
import com.erikriosetiawan.latihanroom.repository.NoteRepository

class MainViewModel(application: Application) : ViewModel() {
    private val mNoteRepository: NoteRepository = NoteRepository(application)
    fun getAllNotes(): LiveData<List<Note>> = mNoteRepository.getAllNotes()
}