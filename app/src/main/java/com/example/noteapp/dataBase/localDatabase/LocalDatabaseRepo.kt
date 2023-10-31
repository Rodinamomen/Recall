package com.example.noteapp.dataBase.localDatabase

import androidx.room.Update
import com.example.noteapp.dataBase.NoteEntity

interface LocalDatabaseRepo {
    suspend fun insertNote(note: NoteEntity)
    suspend fun deleteNote(note: NoteEntity)
    suspend fun getAllNotes() :List<NoteEntity>

    fun updateNote(note : NoteEntity)
}