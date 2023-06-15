package com.example.cprogrammingclub.learning.reading

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReadingViewModel @Inject constructor(private val readingRepository: ReadingRepository) :
    ViewModel() {
    val chapterContentLiveData get() = readingRepository.chapterContentLiveData

    fun getContent(cName: String) {
        viewModelScope.launch {
            readingRepository.getContent(cName)
        }
    }
}