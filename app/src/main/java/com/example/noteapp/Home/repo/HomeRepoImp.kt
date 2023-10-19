package com.example.noteapp.Home.repo

import com.example.noteapp.dataBase.NoteEntity
import com.example.noteapp.dataBase.localDatabase.LocalDatabaseRepo

class HomeRepoImp(val localDatabaseRepo: LocalDatabaseRepo): HomeRepo {
    override suspend fun getAllNotes(): List<NoteEntity> {
        return localDatabaseRepo.getAllNotes()
    }
}