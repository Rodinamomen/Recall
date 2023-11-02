package com.example.noteapp.Home.repo

import com.example.noteapp.dataBase.NoteEntity

interface HomeRepo {
    suspend fun getAllNotes(): List<NoteEntity>
    suspend fun searchByTitle(title: String): List<NoteEntity>
}