package com.example.learningapp.quiz

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.usertodatabase.utils.Constants
import io.appwrite.Query
import io.appwrite.exceptions.AppwriteException
import io.appwrite.services.Databases
import javax.inject.Inject

class QuizRepository @Inject constructor(private val databases: Databases) {

    private val _questions = MutableLiveData<List<QuizModel>>().apply { value = null }

    val questionsLiveData: LiveData<List<QuizModel>> = _questions
    val selectedQuestionLiveData = MutableLiveData<Int>().apply { value = 0 }
    val correctLiveData = MutableLiveData<Int>().apply { value = 0 }

    suspend fun getQuestions(chapterName: String) {
        try {
            val response = databases.listDocuments(
                databaseId = Constants.DATABASE_ID,
                collectionId = Constants.QUIZS_COLLECTION_ID,
                queries = listOf(Query.equal("chaptername", chapterName))
            )
            val questions: List<QuizModel> = response.documents.map {
                QuizModel(
                    chaptername = it.data["chaptername"] as String,
                    question = it.data["question"] as String,
                    options = it.data["options"] as List<String>,
                    answer = it.data["answer"] as String
                )
            }
            _questions.postValue(questions)
        } catch (e: AppwriteException) {
            Log.e("Get questions", e.message.toString())
        }catch (e:Exception){
            Log.e("QuizException", e.message.toString())
        }
    }
}