package com.example.noteapp.note.repo

import com.example.noteapp.dataBase.NoteEntity
import com.example.noteapp.dataBase.localDatabase.LocalDatabaseRepo
import com.example.noteapp.dataBase.localDatabase.LocalDatabaseRepoImp

class NoteRepoImp(var localDatabaseRepoImp: LocalDatabaseRepo): NoteRepo {
    override suspend fun insertNote(note: NoteEntity) {
        localDatabaseRepoImp.insertNote(note)
    }
    override suspend fun deleteNote(note: NoteEntity) {
       localDatabaseRepoImp.deleteNote(note)
    }
    override suspend fun getAllNotes(): List<NoteEntity> {
       return localDatabaseRepoImp.getAllNotes()
    }

    override fun updateNote(note: NoteEntity) {
        localDatabaseRepoImp.updateNote(note)
    }
}