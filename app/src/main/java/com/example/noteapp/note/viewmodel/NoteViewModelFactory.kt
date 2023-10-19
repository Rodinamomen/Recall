package com.example.noteapp.note.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.noteapp.note.repo.NoteRepo

class NoteViewModelFactory (val noteRepo:NoteRepo): ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return if (modelClass.isAssignableFrom(NoteViewModel::class.java)) {
                NoteViewModel(noteRepo) as T
            }else{
                throw IllegalArgumentException("noteviewmodel class not found")
            }
        }
}