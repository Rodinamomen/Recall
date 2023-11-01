package com.example.noteapp.Home.viewModel

import android.service.quicksettings.Tile
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
    private val _searchNotes = MutableLiveData<List<NoteEntity>>()
    val searchNotes :LiveData<List<NoteEntity>> = _searchNotes
    fun  getAllNotes(){
        viewModelScope.launch {
            val result = homeRepo.getAllNotes()
            _allNotes.value= result
        }
    }
    fun searchByTitle(tile: String){
        viewModelScope.launch {
            val result = homeRepo.searchByTitle(tile)
            _searchNotes.value= result
        }
    }
}