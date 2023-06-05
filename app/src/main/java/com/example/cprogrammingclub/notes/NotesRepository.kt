package com.example.cprogrammingclub.notes

import android.content.ContentValues
import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.cprogrammingclub.utils.NetworkResult
import com.example.usertodatabase.models.NoteRequestModel
import com.example.usertodatabase.models.NoteResponseModel
import com.example.usertodatabase.utils.AppPreference
import com.example.usertodatabase.utils.Constants
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import io.appwrite.Client
import io.appwrite.ID
import io.appwrite.Query
import io.appwrite.services.Databases
import javax.inject.Inject


class NotesRepository @Inject constructor(private val databases: Databases) {

    private val _notesLiveData = MutableLiveData<NetworkResult<List<NoteResponseModel>>>()
    val notesLiveData get() = _notesLiveData

    private val _statusLiveData = MutableLiveData<NetworkResult<Pair<Boolean, String>>>()
    val statusLiveData get() = _statusLiveData

    suspend fun createNote(noteRequestModel: NoteRequestModel) {
        _statusLiveData.postValue(NetworkResult.Loading())
        Log.d(ContentValues.TAG, "I am from createNote method")
        try {
            val document = databases.createDocument(
                databaseId = Constants.DATABASE_ID,
                collectionId = Constants.NOTES_COLLECTION_ID,
                documentId = ID.unique(),
                data = Gson().toJson(noteRequestModel)
            )
            _statusLiveData.postValue(NetworkResult.Success(Pair(true, "Note Created")))

            Log.d(ContentValues.TAG, "Note Created...............")
        } catch (e: Exception) {
            Log.e("Appwrite", "Error: " + e.message)
            _statusLiveData.postValue(NetworkResult.Success(Pair(false, e.message!!)))
        }

        Log.d(ContentValues.TAG, "Account Created")
    }

    suspend fun getNotes() {
        _notesLiveData.postValue(NetworkResult.Loading())
        try {
            val response = databases.listDocuments(
                databaseId = Constants.DATABASE_ID,
                collectionId = Constants.NOTES_COLLECTION_ID,
                queries = listOf(
                    Query.equal(
                        "emailId",
                       Constants.CURRENT_USER_EMAIL
                    )
                )
            )
            val notes: List<NoteResponseModel> = response.documents.map {
                NoteResponseModel(
                    title = it.data["title"] as String,
                    description = it.data["description"] as String,
                    emailId = it.data["emailId"] as String,
                    noteId = it.data["\$id"] as String
                )
            }
            _notesLiveData.postValue(NetworkResult.Success(notes))
        } catch (e: Exception) {
            Log.e("Appwrite", "Error: " + e.message)
            _notesLiveData.postValue(NetworkResult.Error(e.message))
        }
        Log.d(ContentValues.TAG, "Document Retrieved")
    }

    suspend fun updateNote(noteId: String, noteRequestModel: NoteRequestModel) {
        _statusLiveData.postValue(NetworkResult.Loading())
        try {
            val response = databases.updateDocument(
                databaseId = Constants.DATABASE_ID,
                collectionId = Constants.NOTES_COLLECTION_ID,
                documentId = noteId,
                data = noteRequestModel
            )
            _statusLiveData.postValue(NetworkResult.Success(Pair(true, "Note Updated")))

        } catch (e: Exception) {
            Log.e("Appwrite", "Error: " + e.message)
            _statusLiveData.postValue(NetworkResult.Success(Pair(false, e.message!!)))
        }
    }

    suspend fun deleteNote(noteId: String) {
        _statusLiveData.postValue(NetworkResult.Loading())
        try {
            val response = databases.deleteDocument(
                databaseId = Constants.DATABASE_ID,
                collectionId = Constants.NOTES_COLLECTION_ID,
                documentId = noteId
            )
            _statusLiveData.postValue(NetworkResult.Success(Pair(true, "Note Deleted")))

        } catch (e: Exception) {
            Log.e("Appwrite", "Error: " + e.message)
            _statusLiveData.postValue(NetworkResult.Success(Pair(false, e.message!!)))
        }

    }

}
