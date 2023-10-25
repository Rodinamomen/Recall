package com.example.noteapp.dataBase

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class NoteEntity(
    @PrimaryKey(autoGenerate = true) var id :Int =0,
    var title: String="",
    var subTitle : String="",
    var dateTime: String="",
    var noteText:String="",
    var noteColor:String="#28282B",
    var imgPath:String="",
//    var webLink: String
) {
    override fun toString(): String {
        return super.toString()
    }
}