package com.example.noteapp.Home.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.noteapp.Home.repo.HomeRepo
import com.example.noteapp.dataBase.NoteEntity
import kotlinx.coroutines.launch

class HomeViewModel(val homeRepo:HomeRepo): ViewModel() {
    private val _allNotes = MutableLiveData<List<NoteEntity>>()
    val allNotes :LiveData<List<NoteEntity>> = _allNotes

    fun  getAllNotes(){
        viewModelScope.launch {
            val result = homeRepo.getAllNotes()
            _allNotes.value= result
        }
    }
}