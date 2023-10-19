package com.example.noteapp.dataBase

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface NoteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
   suspend fun insertNote(note:NoteEntity)
    @Delete
    suspend fun deleteNote(note: NoteEntity)
    @Query("SELECT * FROM NoteEntity ORDER BY id DESC")
    suspend fun getAllNotes() :List<NoteEntity>
}