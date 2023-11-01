package com.example.noteapp.Details.repo

import com.example.noteapp.dataBase.NoteEntity

interface NoteDetailsRepo {
    suspend fun deleteNote(note: NoteEntity)
    fun updateNote(note : NoteEntity)

}