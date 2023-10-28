package com.example.noteapp.Home.view

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.noteapp.Home.adapter.NotesAdapter
import com.example.noteapp.Home.repo.HomeRepoImp
import com.example.noteapp.Home.viewModel.HomeViewModel
import com.example.noteapp.Home.viewModel.HomeViewModelFactory
import com.example.noteapp.R
import com.example.noteapp.dataBase.NoteEntity
import com.example.noteapp.dataBase.localDatabase.LocalDatabaseRepoImp
import com.example.noteapp.note.repo.NoteRepoImp
import com.example.noteapp.note.viewmodel.NoteViewModel
import com.example.noteapp.note.viewmodel.NoteViewModelFactory
import com.google.android.material.floatingactionbutton.FloatingActionButton
import androidx.navigation.fragment.navArgs



class HomeFragment : Fragment() {
    lateinit var addNoteBtn: FloatingActionButton
    lateinit var homeViewModel: HomeViewModel
    lateinit var notesRecyclerView: RecyclerView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        addNoteBtn = view.findViewById(R.id.btn_add_note)
        notesRecyclerView = view.findViewById(R.id.rl_notes)
        gettingViewModelReady(requireContext())
        homeViewModel.getAllNotes()
        homeViewModel.allNotes.observe(requireActivity()) { data->
            if(data!=null){
                NotesAdapter(data)
                var myAdapter = NotesAdapter(data)
                notesRecyclerView.adapter = myAdapter
                myAdapter.setOnClickListener(object : NotesAdapter.OnItemClickListener{
                    override fun onItemClicked(notesModel: NoteEntity) {
                      val action= HomeFragmentDirections.actionHomeFragmentToNoteFragment8(notesModel.title,notesModel.subTitle,notesModel.dateTime,notesModel.noteText,notesModel.noteColor,notesModel.imgPath,notesModel.webLink)
                      //  val action=  HomeFragmentDirections.actionHomeFragmentToNoteFragment(notesModel.title,notesModel.subTitle,notesModel.dateTime,notesModel.noteText,notesModel.noteColor,notesModel.imgPath,notesModel.webLink)
                        findNavController().navigate(action)
                    }
                })
                notesRecyclerView.layoutManager =
                    LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
            }
            }

        addNoteBtn.setOnClickListener {
            findNavController().navigate(R.id.noteFragment)
        }
    }
    private fun gettingViewModelReady(context: Context) {
        var homeViewModelFactory = HomeViewModelFactory(HomeRepoImp(LocalDatabaseRepoImp(context)))
        homeViewModel = ViewModelProvider(this, homeViewModelFactory).get(HomeViewModel::class.java)
    }
}