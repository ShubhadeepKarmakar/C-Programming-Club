package com.example.cprogrammingclub.learning.reading

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.cprogrammingclub.learning.ChapterContentModel
import com.example.cprogrammingclub.utils.NetworkResult
import com.example.usertodatabase.utils.Constants
import io.appwrite.Query
import io.appwrite.exceptions.AppwriteException
import io.appwrite.services.Databases
import javax.inject.Inject

class ReadingRepository @Inject constructor(private val databases: Databases) {

    private val _chapterContentLiveData =
        MutableLiveData<NetworkResult<List<ChapterContentModel>>>()
    val chapterContentLiveData get() = _chapterContentLiveData

    suspend fun getContent(cName: String) {
        _chapterContentLiveData.postValue(NetworkResult.Loading())
        try {
            val response = databases.listDocuments(
                databaseId = Constants.DATABASE_ID,
                collectionId = Constants.CHAPTER_CONTENT,
                queries = listOf(
                    Query.equal("cName", cName)
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
            _chapterContentLiveData.postValue(NetworkResult.Success(content))


        } catch (e: AppwriteException) {
            Log.e("Appwrite", "Error: " + e.message)
            _chapterContentLiveData.postValue(NetworkResult.Error(e.message))
        }
    }


}