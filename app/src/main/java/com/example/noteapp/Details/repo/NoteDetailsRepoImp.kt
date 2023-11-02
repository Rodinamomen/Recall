package com.example.noteapp.Details.repo

import com.example.noteapp.dataBase.NoteEntity
import com.example.noteapp.dataBase.localDatabase.LocalDatabaseRepo

class NoteDetailsRepoImp(var localDatabaseRepoImp: LocalDatabaseRepo) : NoteDetailsRepo {
    override suspend fun deleteNote(note: NoteEntity) {
        localDatabaseRepoImp.deleteNote(note)
    }

    override fun updateNote(note: NoteEntity) {
        localDatabaseRepoImp.updateNote(note)
    }
}