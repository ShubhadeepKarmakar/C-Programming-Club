package com.example.learningapp.quiz

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuizViewModel @Inject constructor(private val quizRepository: QuizRepository) : ViewModel() {

    val questionsLiveData get() = quizRepository.questionsLiveData
    val selectedQuestionLiveData get() = quizRepository.selectedQuestionLiveData
    val correctLiveData get() = quizRepository.correctLiveData

    fun getQuestions(chapterName: String) {
        viewModelScope.launch {
            quizRepository.getQuestions(chapterName)
        }
    }

}