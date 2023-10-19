package com.example.noteapp.note.model

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.noteapp.R
import com.example.noteapp.dataBase.NoteEntity
import com.example.noteapp.dataBase.localDatabase.LocalDatabaseRepoImp
import com.example.noteapp.note.repo.NoteRepoImp
import com.example.noteapp.note.viewmodel.NoteViewModel
import com.example.noteapp.note.viewmodel.NoteViewModelFactory
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
        dateTv=view.findViewById(R.id.tv_date)
        gettingViewModelReady(requireContext())
        val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
        var currentDate = sdf.format(Date())
        dateTv.text= currentDate
        backPresIv.setOnClickListener {
            findNavController().navigate(R.id.action_noteFragment_to_homeFragment)
        }
        saveNoteIv.setOnClickListener {
            noteViewModel.insertNote(NoteEntity(0,noteTitleEt.text.toString(),noteSubtitleEt.text.toString(), dateTv.text.toString(), noteTextEt.text.toString()))
        }
    }
    private fun gettingViewModelReady(context: Context){
        var noteViewModelFactory = NoteViewModelFactory(NoteRepoImp(LocalDatabaseRepoImp(context)))
        noteViewModel = ViewModelProvider(this,noteViewModelFactory).get(NoteViewModel::class.java)
    }
}