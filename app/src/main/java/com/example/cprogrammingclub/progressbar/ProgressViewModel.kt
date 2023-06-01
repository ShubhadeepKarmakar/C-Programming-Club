package com.example.cprogrammingclub.progressbar

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cprogrammingclub.progressbar.model.ProgressModel
import com.example.usertodatabase.utils.Constants
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import io.appwrite.ID
import io.appwrite.Query
import io.appwrite.services.Databases
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProgressViewModel @Inject constructor(private val databases: Databases) : ViewModel() {
    private val chapterList = listOf("Print Hello World", "Introduction", "Pointers")

    private val _progressLiveData = MutableLiveData<Int>()
    val progressLiveData get() = _progressLiveData


    private val _progressModelListLiveData = MutableLiveData<List<ProgressModel>>()
    val progressModelListLiveData get() = _progressModelListLiveData


    fun initializeProgress() {
        for (i in chapterList) {
            val progressModel = ProgressModel("a@gmail.com", i, 0, 0, ID.unique())
            createProgress(progressModel)
        }
    }

    private fun createProgress(progressModel: ProgressModel) {
        viewModelScope.launch {
            try {
                val document = databases.createDocument(
                    databaseId = Constants.DATABASE_ID,
                    collectionId = Constants.PROGRESS_COLLECTION_ID,
                    documentId = progressModel.pId,
                    data = Gson().toJson(progressModel)
                )
                Log.d(ContentValues.TAG, "Progress Created..........")
                delay(1000)
            } catch (e: Exception) {
                Log.e("Appwrite(createProgress)", "Error: " + e.message)
            }
        }
    }


    fun getProgress() {
        viewModelScope.launch {
            try {
                val response = databases.listDocuments(
                    databaseId = Constants.DATABASE_ID,
                    collectionId = Constants.PROGRESS_COLLECTION_ID,
                    queries = listOf(
                        Query.equal("emailId", "a@gmail.com")
                    )
                )

                val progress: List<ProgressModel> = response.documents.map {
                    ProgressModel(
                        emailId = it.data["emailId"] as String,
                        chapterName = it.data["chapterName"] as String,
                        cProgress = it.data["cProgress"].toString().toDouble().toInt(),
                        qProgress = it.data["qProgress"].toString().toDouble().toInt(),
                        pId = it.data["\$id"] as String
                    )
                }
                Log.e("Appwrite(getProgress--->)", progress.toString())


                var progressPercentage = 0
                for (i in progress) {
                    progressPercentage += i.cProgress + i.qProgress
                }
                _progressModelListLiveData.postValue(progress)
                _progressLiveData.postValue(progressPercentage)

            } catch (e: Exception) {
                Log.e("Appwrite(getProgress)", "Error: " + e.message)
            }
        }
    }

    fun updateProgress(pId: String, progressModel: ProgressModel) {

        viewModelScope.launch {
            try {
                val response = databases.updateDocument(
                    databaseId = Constants.DATABASE_ID,
                    collectionId = Constants.PROGRESS_COLLECTION_ID,
                    documentId = pId,
                    data = progressModel
                )
                Log.d(ContentValues.TAG, "Progress Updated..........")
            } catch (e: Exception) {
                Log.e("Appwrite(updateProgress)", "Error: " + e.message)
            }
        }
        getProgress()
    }
}