package com.example.noteapp.Details.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.noteapp.Details.repo.NoteDetailsRepo
import com.example.noteapp.dataBase.NoteEntity
import com.example.noteapp.note.repo.NoteRepo
import kotlinx.coroutines.launch

class NoteDetailsViewModel(val noteDetailsRepo: NoteDetailsRepo) : ViewModel(){
    fun updateNote(note: NoteEntity){
        viewModelScope.launch {
            noteDetailsRepo.updateNote(note)
        }
    }
    fun deleteNote(note: NoteEntity){
        viewModelScope.launch {
            noteDetailsRepo.deleteNote(note)
        }
    }
    private val _allNotes = MutableLiveData<List<NoteEntity>>()
    val allNotes : LiveData<List<NoteEntity>> = _allNotes
}