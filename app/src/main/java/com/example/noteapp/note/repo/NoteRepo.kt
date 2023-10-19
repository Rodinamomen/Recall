package com.example.noteapp.note.repo

import com.example.noteapp.dataBase.NoteEntity

interface NoteRepo {
    suspend fun insertNote(note: NoteEntity)
    suspend fun deleteNote(note: NoteEntity)
    suspend fun getAllNotes() :List<NoteEntity>
}