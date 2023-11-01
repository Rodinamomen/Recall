package com.example.noteapp.Home.view

import android.content.Context
import android.os.Bundle
import android.provider.ContactsContract.CommonDataKinds.Note
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.SearchView
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

import com.google.android.material.floatingactionbutton.FloatingActionButton
import androidx.navigation.fragment.navArgs
import java.util.Locale


class HomeFragment : Fragment() {
    lateinit var addNoteBtn: FloatingActionButton
    lateinit var homeViewModel: HomeViewModel
    lateinit var notesRecyclerView: RecyclerView
   lateinit var searchView :SearchView
    lateinit var arrListNotes: ArrayList<NoteEntity>
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
     //
           searchView= view.findViewById(R.id.searchView)
        gettingViewModelReady(requireContext())
        homeViewModel.getAllNotes()
        homeViewModel.allNotes.observe(requireActivity()) { data->
            if(data!=null){
                NotesAdapter(data)
                var myAdapter = NotesAdapter(data)
                notesRecyclerView.adapter = myAdapter
                myAdapter.setOnClickListener(object : NotesAdapter.OnItemClickListener{
                    override fun onItemClicked(notesModel: NoteEntity) {
                    val action =     HomeFragmentDirections.actionHomeFragmentToNoteDetailsFragment(notesModel.id,notesModel.title,notesModel.subTitle,notesModel.dateTime,notesModel.noteText,notesModel.noteColor,notesModel.imgPath,notesModel.webLink)
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
       searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
           override fun onQueryTextSubmit(query: String?): Boolean {
              return true
           }

           override fun onQueryTextChange(newText: String?): Boolean {
               if(newText.toString()!=""){
                   homeViewModel.searchByTitle("%${newText.toString()}%")
                   homeViewModel.searchNotes.observe(requireActivity()) { data ->
                       var myAdapter = NotesAdapter(data)
                       notesRecyclerView.adapter = myAdapter
                       myAdapter.setOnClickListener(object : NotesAdapter.OnItemClickListener {
                           override fun onItemClicked(notesModel: NoteEntity) {
                               val action =
                                   HomeFragmentDirections.actionHomeFragmentToNoteDetailsFragment(
                                       notesModel.id,
                                       notesModel.title,
                                       notesModel.subTitle,
                                       notesModel.dateTime,
                                       notesModel.noteText,
                                       notesModel.noteColor,
                                       notesModel.imgPath,
                                       notesModel.webLink
                                   )
                               findNavController().navigate(action)
                           }
                       })
                       notesRecyclerView.layoutManager =
                           LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)

                   }
               }else{
                   homeViewModel.getAllNotes()
               }

               return true
           }
       })

    }
    private fun gettingViewModelReady(context: Context) {
        var homeViewModelFactory = HomeViewModelFactory(HomeRepoImp(LocalDatabaseRepoImp(context)))
        homeViewModel = ViewModelProvider(this, homeViewModelFactory).get(HomeViewModel::class.java)
    }
}