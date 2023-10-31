package com.example.noteapp.dataBase.localDatabase

import android.content.Context
import com.example.noteapp.dataBase.NoteDao
import com.example.noteapp.dataBase.NoteDataBase
import com.example.noteapp.dataBase.NoteEntity

class LocalDatabaseRepoImp(context: Context): LocalDatabaseRepo {
    private lateinit var noteDao: NoteDao
    init {
        val db: NoteDataBase = NoteDataBase.getInstance(context)
        noteDao= db.noteDao()
    }

    override suspend fun insertNote(note: NoteEntity) {
        noteDao.insertNote(note)
    }
    override suspend fun deleteNote(note: NoteEntity) {
        noteDao.deleteNote(note)
    }
    override suspend fun getAllNotes(): List<NoteEntity> {
       return noteDao.getAllNotes()
    }

    override fun updateNote(note: NoteEntity) {
        noteDao.updateNote(note)
    }
}