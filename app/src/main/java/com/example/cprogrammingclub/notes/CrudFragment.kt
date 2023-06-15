package com.example.cprogrammingclub.notes

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.cprogrammingclub.MainActivity
import com.example.cprogrammingclub.R
import com.example.cprogrammingclub.databinding.FragmentCrudBinding
import com.example.cprogrammingclub.utils.NetworkResult
import com.example.usertodatabase.models.NoteRequestModel
import com.example.usertodatabase.models.NoteResponseModel
import com.example.usertodatabase.ui.notes.NotesViewModel
import com.example.usertodatabase.utils.Constants
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CrudFragment : Fragment() {
    private var _binding: FragmentCrudBinding? = null
    private val binding get() = _binding!!

    private val noteViewModel by activityViewModels<NotesViewModel>()
    private var note: NoteResponseModel? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCrudBinding.inflate(inflater, container, false)

        (requireActivity() as MainActivity).hideBottomNavAndToolBar()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setInitialData()
        bindHandlers()
        bindObservers()
    }

    private fun bindObservers() {
        noteViewModel.statusLiveData.observe(viewLifecycleOwner, Observer {
            when (it) {
                is NetworkResult.Success -> {

                    Log.d("Crud Fragment", "Lala La Lala")
                    parentFragmentManager.beginTransaction()
                        .replace(R.id.fragmentContainer, NotesFragment())
                        .commit()
                }
                is NetworkResult.Error -> {

                }
                is NetworkResult.Loading -> {

                    Log.d("Crud Fragment", "Loadinggggggggggggggggggggggggggggg")
                }
            }
        })
    }

    private fun bindHandlers() {
        binding.btnDelete.setOnClickListener {
            note?.let { noteViewModel.deleteNote(it.noteId) }
        }
        binding.apply {
            btnSubmit.setOnClickListener {
                val title = title.text.toString()
                val description = description.text.toString()
                val noteRequestModel =
                    NoteRequestModel(title, description, Constants.CURRENT_USER_EMAIL)
                if (note == null) {
                    noteViewModel.createNote(noteRequestModel)
                } else {
                    noteViewModel.updateNote(note!!.noteId, noteRequestModel)
                }
            }
        }
    }

    private fun setInitialData() {
        val jsonNote = arguments?.getString("note")
        if (jsonNote != null) {
            note = Gson().fromJson(jsonNote, NoteResponseModel::class.java)
            note?.let {
                binding.title.setText(it.title)
                binding.description.setText(it.description)
            }
        } else {
            binding.heading.text = resources.getString(R.string.add_note)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        (requireActivity() as MainActivity).showBottomNavAndToolBar()
    }
}