package com.example.cprogrammingclub.notes

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.cprogrammingclub.databinding.ActivityNotesBinding
import com.example.cprogrammingclub.utils.NetworkResult
import com.example.usertodatabase.models.NoteResponseModel
import com.example.usertodatabase.ui.notes.NotesAdapter
import com.example.usertodatabase.ui.notes.NotesViewModel
import com.google.gson.Gson

class NotesActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNotesBinding

    private lateinit var adapter: NotesAdapter
    private lateinit var notesViewModel: NotesViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityNotesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        notesViewModel = ViewModelProvider(this).get(NotesViewModel::class.java)
//        notesViewModel.createClient(this)

        adapter = NotesAdapter(::onNoteClicked)

        notesViewModel.getAllNotes(this)
        binding.notesList.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        binding.notesList.adapter = adapter

        binding.btnAddNote.setOnClickListener {
            val intent = Intent(this, CrudNoteActivity::class.java)
            startActivity(intent)
        }
        NotesObserver()
    }

    private fun NotesObserver() {
        notesViewModel.notesLiveData.observe(this, Observer {

            binding.progressBar.isVisible = false
            when (it) {
                is NetworkResult.Success -> {
                    adapter.submitList(it.data)
                }
                is NetworkResult.Error -> {
                    Toast.makeText(this, it.message.toString(), Toast.LENGTH_SHORT).show()
                }
                is NetworkResult.Loading -> {
                    binding.progressBar.isVisible = true
                }
            }
        })
    }

    private fun onNoteClicked(noteResponseModel: NoteResponseModel) {
        val bundle = Bundle()
        bundle.putString("note", Gson().toJson(noteResponseModel))
        val intent = Intent(this, CrudNoteActivity::class.java)
        intent.putExtras(bundle)
        getResult.launch(intent)
    }

    private val getResult =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) {
            if (it.resultCode == Activity.RESULT_OK) {
                val mIntent = intent
                finish()
                startActivity(mIntent)
            }
        }
}