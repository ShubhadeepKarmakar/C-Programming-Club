package com.example.cprogrammingclub.notes

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.cprogrammingclub.R
import com.example.cprogrammingclub.databinding.ActivityCrudNoteBinding
import com.example.cprogrammingclub.utils.NetworkResult
import com.example.usertodatabase.models.NoteRequestModel
import com.example.usertodatabase.models.NoteResponseModel
import com.example.usertodatabase.ui.notes.NotesViewModel
import com.example.usertodatabase.utils.AppPreference
import com.google.gson.Gson
import javax.inject.Inject

class CrudNoteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCrudNoteBinding

    @Inject
    lateinit var appPreference: AppPreference

    private var note: NoteResponseModel? = null
    lateinit var notesViewModel: NotesViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCrudNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        notesViewModel = ViewModelProvider(this).get(NotesViewModel::class.java)
//        notesViewModel.createClient(this)

        setInitialData()
        NotesObserver()
        NotesHandlers() //for controlling onClick
    }

    private fun NotesObserver() {

        notesViewModel.statusLiveData.observe(this, Observer {
            when (it) {
                is NetworkResult.Success -> {
                    setResult(RESULT_OK)
                    finish()
                    Toast.makeText(this, "submitted", Toast.LENGTH_SHORT).show()
                }
                is NetworkResult.Error -> {

                }
                is NetworkResult.Loading -> {

                }
            }
        })

    }

    private fun NotesHandlers() {
        binding.btnDelete.setOnClickListener {
            note?.let { notesViewModel.deleteNote(it.noteId) }
        }
        binding.btnSubmit.setOnClickListener {
            val title = binding.etTitle.text.toString()
            val description = binding.etDescription.text.toString()
            val noteRequestModel =
//                NoteRequestModel(title, description, appPreference.getSharedPerferences()!!)
                NoteRequestModel(title, description, "a@gmail.com")
            if (note == null) {
                notesViewModel.createNote(noteRequestModel)
            } else {
                notesViewModel.updateNote(note!!.noteId, noteRequestModel)
            }
        }
    }

    private fun setInitialData() {
        val bundle = intent.extras

        if (bundle != null) {
            val getNote = bundle!!.getString("note", "")

            note = Gson().fromJson(getNote, NoteResponseModel::class.java)
            note?.let {
                binding.etTitle.setText(it.TITLE)
                binding.etDescription.setText(it.DESCRIPTION)
            }

        } else {
            binding.tvActivityName.text = getString(R.string.add_note)

        }
    }
}