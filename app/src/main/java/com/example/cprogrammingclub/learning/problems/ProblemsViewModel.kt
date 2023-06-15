package com.example.cprogrammingclub.learning.problems

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cprogrammingclub.learning.ChapterContentModel
import com.example.cprogrammingclub.utils.NetworkResult
import com.example.usertodatabase.utils.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import io.appwrite.Query
import io.appwrite.exceptions.AppwriteException
import io.appwrite.services.Databases
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProblemsViewModel @Inject constructor(private val databases: Databases) : ViewModel() {

    private val _problemsLiveData = MutableLiveData<NetworkResult<List<ChapterContentModel>>>()
    val problemsLiveData get() = _problemsLiveData


    fun getContent(chapterName: String) {

        _problemsLiveData.postValue(NetworkResult.Loading())
        viewModelScope.launch {
            try {
                val response = databases.listDocuments(
                    databaseId = Constants.DATABASE_ID,
                    collectionId = Constants.CHAPTER_CONTENT,
                    queries = listOf(
                        Query.equal("cName", chapterName)
                    )
                )
                val content: List<ChapterContentModel> = response.documents.map {
                    ChapterContentModel(
                        cName = it.data["cName"] as String,
                        cContent = it.data["cContent"] as String,
                        videoId = it.data["videoId"] as String,
                        cProblems = it.data["cProblems"] as String
                    )
                }
                _problemsLiveData.postValue(NetworkResult.Success(content))
                
            } catch (e: AppwriteException) {
                Log.e("Appwrite", "Error: " + e.message)
                _problemsLiveData.postValue(NetworkResult.Error(e.message))
            }
        }
    }

}