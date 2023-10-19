package com.example.noteapp.Home.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.noteapp.R
import com.example.noteapp.dataBase.NoteEntity

class NotesAdapter(val data : List<NoteEntity>): RecyclerView.Adapter<NotesAdapter.MyHolder> (){
    class MyHolder(val row : View): RecyclerView.ViewHolder(row){
        var noteTitle = row.findViewById<TextView>(R.id.tv_title)
        var noteDesc= row.findViewById<TextView>(R.id.tv_note_desc)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        val row = LayoutInflater.from(parent.context).inflate(R.layout.note_item,parent,false)
        return MyHolder(row)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        holder.noteTitle.text = data[position].title
        holder.noteDesc.text= data[position].noteText
    }
}