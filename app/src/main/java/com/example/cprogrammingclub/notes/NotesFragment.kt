package com.example.cprogrammingclub.notes

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.cprogrammingclub.MainActivity
import com.example.cprogrammingclub.R
import com.example.cprogrammingclub.databinding.FragmentNotesBinding
import com.example.cprogrammingclub.utils.NetworkResult
import com.example.usertodatabase.models.NoteResponseModel
import com.example.usertodatabase.ui.notes.NotesAdapter
import com.example.usertodatabase.ui.notes.NotesViewModel
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NotesFragment : Fragment() {
    private var _binding: FragmentNotesBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: NotesAdapter
    private val notesViewModel by activityViewModels<NotesViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNotesBinding.inflate(inflater, container, false)
        adapter = NotesAdapter(::onNoteClicked)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        notesViewModel.getAllNotes()
        binding.notesList.layoutManager =
            StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        binding.notesList.adapter = adapter

        binding.btnAddNote.setOnClickListener {
            replaceFragment(CrudFragment())
        }
        NotesObserver()
//        backStackHandling()
    }

    private fun NotesObserver() {
        notesViewModel.notesLiveData.observe(viewLifecycleOwner, Observer {

            binding.progressBar.isVisible = false
            when (it) {
                is NetworkResult.Success -> {
                    adapter.submitList(it.data)
                }
                is NetworkResult.Error -> {
                    Toast.makeText(requireContext(), it.message.toString(), Toast.LENGTH_SHORT)
                        .show()
                }
                is NetworkResult.Loading -> {
                    binding.progressBar.isVisible = true
                }
            }
        })
    }

    private fun onNoteClicked(noteResponseodel: NoteResponseModel) {
        val bundle = Bundle()
        val fragment = CrudFragment()
        bundle.putString("note", Gson().toJson(noteResponseodel))
        fragment.arguments = bundle
        Log.d("onNoteClicked", "Lala La Lala")
        replaceFragment(fragment)
    }

    private fun replaceFragment(fragment: Fragment) {
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .commit()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}