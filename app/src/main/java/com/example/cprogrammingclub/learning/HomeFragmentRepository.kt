package com.example.cprogrammingclub.learning

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.cprogrammingclub.utils.NetworkResult
import com.example.usertodatabase.utils.Constants
import io.appwrite.services.Databases
import javax.inject.Inject

class HomeFragmentRepository @Inject constructor(private val databases: Databases) {


    private val _chaptersLiveData = MutableLiveData<NetworkResult<List<ChapterModel>>>()
    val chaptersLiveData get() = _chaptersLiveData

    suspend fun getChapters() {
        _chaptersLiveData.postValue(NetworkResult.Loading())
        try {
            val response = databases.listDocuments(
                databaseId = Constants.DATABASE_ID,
                collectionId = Constants.CHAPTERS_COLLECTION_ID,
            )
            val notes: List<ChapterModel> = response.documents.map {
                ChapterModel(
                    chapterName = it.data["chapterName"] as String
                )
            }
            _chaptersLiveData.postValue(NetworkResult.Success(notes))
        } catch (e: Exception) {
            Log.e("Appwrite", "Error: " + e.message)
            _chaptersLiveData.postValue(NetworkResult.Error(e.message))
        }
        Log.d(ContentValues.TAG, "Document Retrieved")
    }

}