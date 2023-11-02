package com.example.noteapp.Details.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.noteapp.Details.repo.NoteDetailsRepo
import com.example.noteapp.note.repo.NoteRepo

class NoteDetailsViewModelFactory(val noteDetailsRepo: NoteDetailsRepo) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(NoteDetailsViewModel::class.java)) {
            NoteDetailsViewModel(noteDetailsRepo) as T
        } else {
            throw IllegalArgumentException("notedetailsviewmodel class not found")
        }
    }
}