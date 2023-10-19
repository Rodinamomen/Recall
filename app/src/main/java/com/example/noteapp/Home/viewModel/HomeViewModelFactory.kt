package com.example.noteapp.Home.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.noteapp.Home.repo.HomeRepo
import com.example.noteapp.note.repo.NoteRepo
import com.example.noteapp.note.viewmodel.NoteViewModel

class HomeViewModelFactory(val homeRepo: HomeRepo): ViewModelProvider.Factory  {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
                HomeViewModel(homeRepo) as T
            }else{
                throw IllegalArgumentException("HomeViewModel class not found")
            }
        }
}