package com.example.noteapp.dataBase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities =[ NoteEntity::class], version =7)
abstract class NoteDataBase: RoomDatabase() {
    abstract fun noteDao(): NoteDao

    companion object {
        @Volatile
        private var INSTANCE: NoteDataBase? = null
        fun getInstance(context: Context): NoteDataBase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    NoteDataBase::class.java,
                    "NoteData"
                ).fallbackToDestructiveMigration().allowMainThreadQueries()
                    .build()
                    .also {
                        INSTANCE = it
                    }
            }
        }
    }
}