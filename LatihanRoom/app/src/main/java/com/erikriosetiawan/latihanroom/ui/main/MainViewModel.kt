package com.erikriosetiawan.latihanroom.ui.main

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.erikriosetiawan.latihanroom.database.Note
import com.erikriosetiawan.latihanroom.repository.NoteRepository

class MainViewModel(application: Application) : ViewModel() {
    private val mNoteRepository: NoteRepository = NoteRepository(application)

    fun getAllNotes(): LiveData<PagedList<Note>> =
        LivePagedListBuilder(mNoteRepository.getAllNotes(), 20).build()
}