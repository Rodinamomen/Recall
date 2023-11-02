package com.example.noteapp.note.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.noteapp.dataBase.NoteEntity
import com.example.noteapp.note.repo.NoteRepo
import kotlinx.coroutines.launch

class NoteViewModel(val noteRepo: NoteRepo) : ViewModel() {
    fun insertNote(note: NoteEntity) {
        viewModelScope.launch {
            noteRepo.insertNote(note)
        }
    }

    fun updateNote(note: NoteEntity) {
        viewModelScope.launch {
            noteRepo.updateNote(note)
        }
    }

    private val _allNotes = MutableLiveData<List<NoteEntity>>()
    val allNotes: LiveData<List<NoteEntity>> = _allNotes
}