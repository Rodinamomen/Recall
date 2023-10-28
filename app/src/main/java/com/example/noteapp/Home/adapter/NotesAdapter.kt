package com.example.noteapp.Home.adapter

import android.graphics.BitmapFactory
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.noteapp.R
import com.example.noteapp.dataBase.NoteEntity

class NotesAdapter(val data : List<NoteEntity>):

    RecyclerView.Adapter<NotesAdapter.MyHolder> (){
    var dataList : ArrayList<NoteEntity> = data as ArrayList<NoteEntity>
    private lateinit var onItemClickListener: OnItemClickListener

    interface OnItemClickListener {
        fun onItemClicked(notesModel: NoteEntity)
    }
    fun setOnClickListener(listener: OnItemClickListener){
        onItemClickListener = listener
    }
    class MyHolder(val row : View, listener: OnItemClickListener ): RecyclerView.ViewHolder(row){
        var noteTitle = row.findViewById<TextView>(R.id.tv_title)
        var noteDesc= row.findViewById<TextView>(R.id.tv_note_desc)
        var noteColor= row.findViewById<CardView>(R.id.item_card_view)
        var noteDate= row.findViewById<TextView>(R.id.tv_note_date)
        var noteItem = row.findViewById<CardView>(R.id.item_card_view)


    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        val row = LayoutInflater.from(parent.context).inflate(R.layout.note_item,parent,false)
        return MyHolder(row,onItemClickListener)
    }
    override fun getItemCount(): Int {
        return data.size
    }
    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        holder.noteTitle.text = data[position].title
        holder.noteDesc.text= data[position].noteText
       holder.noteColor.setCardBackgroundColor(Color.parseColor(data[position].noteColor))
        holder.noteDate.text=data[position].dateTime
        holder.noteItem.setOnClickListener {
            onItemClickListener.onItemClicked(dataList[position])
        }

    }
}


