package com.example.noteapp.Details.view

import android.Manifest
import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.net.toUri
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.noteapp.Details.repo.NoteDetailsRepoImp
import com.example.noteapp.Details.viewmodel.NoteDetailsViewModel
import com.example.noteapp.Details.viewmodel.NoteDetailsViewModelFactory
import com.example.noteapp.R
import com.example.noteapp.dataBase.NoteEntity
import com.example.noteapp.dataBase.localDatabase.LocalDatabaseRepoImp
import com.example.noteapp.note.repo.NoteRepoImp
import com.example.noteapp.note.viewmodel.NoteViewModel
import com.example.noteapp.note.viewmodel.NoteViewModelFactory
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions
import java.io.File
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.Date


class NoteDetailsFragment : Fragment(), EasyPermissions.PermissionCallbacks,
    EasyPermissions.RationaleCallbacks {
    val navArgs: NoteDetailsFragmentArgs by navArgs()
    private lateinit var backPresIv: ImageView
    private lateinit var noteTitleEt: EditText
    private lateinit var noteTextEt: EditText
    private lateinit var saveNoteIv: ImageView
    private lateinit var noteDetailsViewModel: NoteDetailsViewModel
    private lateinit var noteSubtitleEt: EditText
    private lateinit var dateTv: TextView
    private lateinit var color2Iv: ImageView
    private lateinit var color3Iv: ImageView
    private lateinit var color4Iv: ImageView
    private lateinit var color5Iv: ImageView
    private lateinit var color6Iv: ImageView
    private lateinit var color7Iv: ImageView
    private lateinit var colorV: View
    private lateinit var colorSelected: String
    private lateinit var imageSelected: String
    private lateinit var delteNoteTv: TextView
    private lateinit var linkEnterd: String
    private lateinit var sheet: FrameLayout
    private lateinit var imageFromGalleryIv: ImageView
    private lateinit var addImageTv: TextView
    private lateinit var addLinkTv: TextView
    private lateinit var noteLinkTv: TextView
    private val READ_STORAGE_PERM = 123
    private val RQUEST_CODE_IMAGE = 456
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_note_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        backPresIv = view.findViewById(R.id.iv_back_press)
        noteTitleEt = view.findViewById(R.id.et_title)
        noteTitleEt.setText(navArgs.title)
        noteTextEt = view.findViewById(R.id.et_note_text)
        noteTextEt.setText(navArgs.noteText)
        noteSubtitleEt = view.findViewById(R.id.et_subtitle)
        noteSubtitleEt.setText(navArgs.subTitle)
        saveNoteIv = view.findViewById(R.id.iv_save_note)
        color2Iv = view.findViewById(R.id.iv_color_2)
        color3Iv = view.findViewById(R.id.iv_color_3)
        color4Iv = view.findViewById(R.id.iv_color_4)
        color5Iv = view.findViewById(R.id.iv_color_5)
        color6Iv = view.findViewById(R.id.iv_color_6)
        color7Iv = view.findViewById(R.id.iv_color_7)
        colorV = view.findViewById(R.id.v_note)
        colorV.setBackgroundColor(Color.parseColor(navArgs.noteColor))
        dateTv = view.findViewById(R.id.tv_date)
        addLinkTv = view.findViewById(R.id.tv_add_link)
        noteLinkTv = view.findViewById(R.id.tv_note_link)
        noteLinkTv.setText(navArgs.webLink)
        delteNoteTv = view.findViewById(R.id.tv_delete_note)
        sheet = view.findViewById(R.id.bottom_sheet)
        addImageTv = view.findViewById(R.id.tv_add_image)
        imageFromGalleryIv = view.findViewById(R.id.iv_image_picker)
        if (navArgs.imgPath.isEmpty()) {
            imageFromGalleryIv.visibility = View.GONE

        } else {
            imageFromGalleryIv.setImageURI(navArgs.imgPath.toUri())
            imageFromGalleryIv.visibility = View.VISIBLE
        }
        BottomSheetBehavior.from(sheet).apply {
            peekHeight = 100
            this.state = BottomSheetBehavior.STATE_COLLAPSED
        }
        linkEnterd = navArgs.webLink
        imageSelected = navArgs.imgPath
        colorSelected = navArgs.noteColor

        color2Iv.setOnClickListener {
            colorSelected = "#f49696"
            setColorViewValue(colorV, colorSelected)
        }
        color3Iv.setOnClickListener {
            colorSelected = "#c97c7c"
            setColorViewValue(colorV, colorSelected)
        }
        color4Iv.setOnClickListener {
            colorSelected = "#C5CBFF"
            setColorViewValue(colorV, colorSelected)
        }
        color5Iv.setOnClickListener {
            colorSelected = "#96F4F4"
            setColorViewValue(colorV, colorSelected)
        }
        color6Iv.setOnClickListener {
            colorSelected = "#FEC5FF"
            setColorViewValue(colorV, colorSelected)
        }
        color7Iv.setOnClickListener {
            colorSelected = "#202734"
            setColorViewValue(colorV, colorSelected)

        }
        gettingViewModelReady(requireContext())
        val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
        var currentDate = sdf.format(Date())
        dateTv.text = currentDate
        backPresIv.setOnClickListener {
            val dialog = layoutInflater.inflate(R.layout.discard_dialog, null)
            val myDialog = Dialog(requireContext())
            myDialog.setContentView(dialog)
            myDialog.setCancelable(true)
            myDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            myDialog.show()
            val keepBtn = dialog.findViewById<Button>(R.id.btn_keep)
            val discardBtn = dialog.findViewById<Button>(R.id.btn_discard)
            keepBtn.setOnClickListener {
                checkNotEmpty(
                    noteTitleEt.text.toString(),
                    noteSubtitleEt.text.toString(),
                    dateTv.text.toString(),
                    noteTextEt.text.toString(),
                    colorSelected
                )
                myDialog.dismiss()
            }
            discardBtn.setOnClickListener {
                myDialog.dismiss()
                navigateToHomeFragment()
            }
        }
        saveNoteIv.setOnClickListener {
            checkNotEmpty(
                noteTitleEt.text.toString(),
                noteSubtitleEt.text.toString(),
                dateTv.text.toString(),
                noteTextEt.text.toString(),
                colorSelected
            )
        }
        addImageTv.setOnClickListener {
            readStorageTask()
        }
        addLinkTv.setOnClickListener {
            val dialog = layoutInflater.inflate(R.layout.link_dialog, null)
            val myDialog = Dialog(requireContext())
            myDialog.setContentView(dialog)
            myDialog.setCancelable(true)
            myDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            myDialog.show()
            val savebtn = dialog.findViewById<Button>(R.id.btn_Save)
            val cancelBtn = dialog.findViewById<Button>(R.id.btn_cancel)
            val addlinkEt = dialog.findViewById<EditText>(R.id.et_add_link)
            savebtn.setOnClickListener {
                noteLinkTv.text = addlinkEt.text
                linkEnterd = addlinkEt.text.toString()
                addlinkEt.text.clear()
                myDialog.dismiss()
            }
            cancelBtn.setOnClickListener {
                addlinkEt.text.clear()
                myDialog.dismiss()
            }
        }
        delteNoteTv.setOnClickListener {
            noteDetailsViewModel.deleteNote(
                NoteEntity(
                    navArgs.noteId,
                    navArgs.title,
                    navArgs.subTitle,
                    navArgs.dateTime,
                    navArgs.noteText,
                    navArgs.noteColor,
                    navArgs.imgPath,
                    navArgs.webLink
                )
            )
            navigateToHomeFragment()
        }
    }

    private fun checkNotEmpty(
        noteTitle: String,
        noteSubtitle: String,
        noteDate: String,
        noteText: String,
        colorSelected: String
    ) {
        if (noteTitle.isEmpty()) {
            MaterialAlertDialogBuilder(requireContext()).setTitle("Title is required")
                .setPositiveButton("Ok", null)
                .show()
        }
        if (noteText.isEmpty()) {
            MaterialAlertDialogBuilder(requireContext()).setTitle("Note Text is required")
                .setPositiveButton("Ok", null)
                .show()
        } else {
            updateNote(
                navArgs.noteId,
                noteTitle,
                noteSubtitle,
                noteDate,
                noteText,
                colorSelected,
                imageSelected,
                linkEnterd
            )
            navigateToHomeFragment()
        }
    }

    private fun gettingViewModelReady(context: Context) {
        var noteDetailsViewModelFactory =
            NoteDetailsViewModelFactory(NoteDetailsRepoImp(LocalDatabaseRepoImp(context)))
        noteDetailsViewModel = ViewModelProvider(
            this,
            noteDetailsViewModelFactory
        ).get(NoteDetailsViewModel::class.java)
    }

    private fun hasReadStoragePerm(): Boolean {
        return EasyPermissions.hasPermissions(
            requireContext(),
            Manifest.permission.READ_EXTERNAL_STORAGE
        )
    }

    private fun readStorageTask() {
        if (hasReadStoragePerm()) {
            pickImageFromGallery()
        } else {
            EasyPermissions.requestPermissions(
                requireActivity(),
                getString(R.string.request_sotrage_permission),
                READ_STORAGE_PERM,
                Manifest.permission.READ_EXTERNAL_STORAGE
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(
            requestCode,
            permissions,
            grantResults,
            requireActivity()
        )
    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {

    }

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
        if (EasyPermissions.somePermissionPermanentlyDenied(requireActivity(), perms)) {
            AppSettingsDialog.Builder(requireActivity()).build().show()
        }
    }

    override fun onRationaleAccepted(requestCode: Int) {

    }

    override fun onRationaleDenied(requestCode: Int) {

    }

    private fun pickImageFromGallery() {
        var intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
        if (intent.resolveActivity(requireActivity().packageManager) != null) {
            startActivityForResult(intent, RQUEST_CODE_IMAGE)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RQUEST_CODE_IMAGE && resultCode == Activity.RESULT_OK) {
            if (data != null) {
                var selectedImageUrl = data.data
                if (selectedImageUrl != null) {
                    try {
                        imageSelected = selectedImageUrl.toString()
                        imageFromGalleryIv.setImageURI(selectedImageUrl)
                        imageFromGalleryIv.visibility = View.VISIBLE
                    } catch (e: Exception) {
                        Toast.makeText(requireContext(), e.message, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    private fun setColorViewValue(view: View, colorSelected: String) {
        view.setBackgroundColor(Color.parseColor(colorSelected))
    }

    private fun navigateToHomeFragment() {
        findNavController().navigate(R.id.action_noteDetailsFragment_to_homeFragment)
    }

    private fun updateNote(
        noteId: Int, noteTitle: String,
        noteSubtitle: String,
        noteDate: String,
        noteText: String,
        colorSelected: String, imageSelected: String, webLink: String
    ) {
        noteDetailsViewModel.updateNote(
            NoteEntity(
                noteId,
                noteTitle,
                noteSubtitle,
                noteDate,
                noteText,
                colorSelected,
                imageSelected,
                webLink
            )
        )
    }
}
