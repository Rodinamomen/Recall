package com.example.noteapp.note.view

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.noteapp.R
import com.example.noteapp.dataBase.NoteEntity
import com.example.noteapp.dataBase.localDatabase.LocalDatabaseRepoImp
import com.example.noteapp.note.repo.NoteRepoImp
import com.example.noteapp.note.viewmodel.NoteViewModel
import com.example.noteapp.note.viewmodel.NoteViewModelFactory
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import java.text.SimpleDateFormat
import java.util.Date


class NoteFragment : Fragment() {
    lateinit var backPresIv: ImageView
    lateinit var noteTitleEt: EditText
    lateinit var noteTextEt: EditText
    lateinit var saveNoteIv: ImageView
    lateinit var noteViewModel: NoteViewModel
    lateinit var noteSubtitleEt: EditText
    lateinit var dateTv:TextView
    lateinit var color1Iv:ImageView
    lateinit var color2Iv:ImageView
    lateinit var color3Iv:ImageView
    lateinit var color4Iv:ImageView
    lateinit var color5Iv:ImageView
    lateinit var color6Iv:ImageView
    lateinit var color7Iv:ImageView
    lateinit var colorV: View


    lateinit var colorSelected : String
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_note, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        backPresIv=view.findViewById(R.id.iv_back_press)
        noteTitleEt=view.findViewById(R.id.et_title)
        noteTextEt= view.findViewById(R.id.et_note_text)
        noteSubtitleEt= view.findViewById(R.id.et_subtitle)
        saveNoteIv=view.findViewById(R.id.iv_save_note)
        color1Iv=view.findViewById(R.id.iv_color_1)
        color2Iv=view.findViewById(R.id.iv_color_2)
        color3Iv=view.findViewById(R.id.iv_color_3)
        color4Iv=view.findViewById(R.id.iv_color_4)
        color5Iv=view.findViewById(R.id.iv_color_5)
        color6Iv=view.findViewById(R.id.iv_color_6)
        color7Iv=view.findViewById(R.id.iv_color_7)
        colorV=view.findViewById(R.id.v_note)
        dateTv=view.findViewById(R.id.tv_date)

        colorSelected="#28282B"
        color1Iv.setOnClickListener {
            colorSelected="#ffffff"
            colorV.setBackgroundColor(Color.parseColor(colorSelected))
        }
        color2Iv.setOnClickListener {
            colorSelected="#4e33ff"
            colorV.setBackgroundColor(Color.parseColor(colorSelected))
        }
        color3Iv.setOnClickListener {
            colorSelected="#ffd633"
            colorV.setBackgroundColor(Color.parseColor(colorSelected))
        }
        color4Iv.setOnClickListener {
            colorSelected="#ae3b76"
            colorV.setBackgroundColor(Color.parseColor(colorSelected))
        }
        color5Iv.setOnClickListener {
            colorSelected="#0aebaf"
            colorV.setBackgroundColor(Color.parseColor(colorSelected))
        }
        color6Iv.setOnClickListener {
            colorSelected="#ff7746"
            colorV.setBackgroundColor(Color.parseColor(colorSelected))
        }
        color7Iv.setOnClickListener {
            colorSelected="#202734"
            colorV.setBackgroundColor(Color.parseColor(colorSelected))
        }
        gettingViewModelReady(requireContext())
        val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
        var currentDate = sdf.format(Date())
        dateTv.text= currentDate
        backPresIv.setOnClickListener {
          val dialog=  layoutInflater.inflate(R.layout.discard_dialog,null)
           val myDialog = Dialog(requireContext())
            myDialog.setContentView(dialog)
            myDialog.setCancelable(true)
            myDialog.show()
         //   findNavController().navigate(R.id.action_noteFragment_to_homeFragment)
        }
        saveNoteIv.setOnClickListener {
            checkNotEmpty(noteTitleEt.text.toString(),noteSubtitleEt.text.toString(),dateTv.text.toString(),noteTextEt.text.toString(),colorSelected)
        }
    }
    private fun checkNotEmpty(noteTitle:String, noteSubtitle:String,noteDate:String, noteText:String,colorSelected:String ) {
        if(noteTitle==""){
            MaterialAlertDialogBuilder(requireContext()).setTitle("Title is required").setPositiveButton("Ok", null)
                .show()
        }
        if( noteSubtitle==""){
            MaterialAlertDialogBuilder(requireContext()).setTitle("SubTitle is required").setPositiveButton("Ok", null)
                .show()
        }
        if(noteText=="") {
            MaterialAlertDialogBuilder(requireContext()).setTitle("Note Text is required")
                .setPositiveButton("Ok", null)
                .show()
        }
        else {
            noteViewModel.insertNote(NoteEntity(0,noteTitle, noteSubtitle,noteDate,noteText, colorSelected))
            Toast.makeText(requireContext(), "DONE", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_noteFragment_to_homeFragment)
        }
    }
    private fun gettingViewModelReady(context: Context){
        var noteViewModelFactory = NoteViewModelFactory(NoteRepoImp(LocalDatabaseRepoImp(context)))
        noteViewModel = ViewModelProvider(this,noteViewModelFactory).get(NoteViewModel::class.java)
    }
}