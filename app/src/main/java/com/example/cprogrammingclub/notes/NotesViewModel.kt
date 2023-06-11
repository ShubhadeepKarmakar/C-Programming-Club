package com.example.usertodatabase.ui.notes

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cprogrammingclub.notes.NotesRepository
import com.example.usertodatabase.models.NoteRequestModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotesViewModel @Inject constructor(private val notesRepository: NotesRepository):ViewModel() {
//    val notesRepository= NotesRepository()

    val notesLiveData get() = notesRepository.notesLiveData
    val statusLiveData get() = notesRepository.statusLiveData

    fun createNote(noteRequestModel: NoteRequestModel) {
        viewModelScope.launch {
            notesRepository.createNote(noteRequestModel)
        }
    }

    fun getAllNotes() {
        viewModelScope.launch {
            notesRepository.getNotes()
        }
    }

    fun updateNote(noteId: String,noteRequestModel: NoteRequestModel){
        viewModelScope.launch {
           notesRepository.updateNote(noteId,noteRequestModel)
        }
    }

    fun deleteNote(noteId: String) {
        viewModelScope.launch {
            notesRepository.deleteNote(noteId)
        }
    }

}