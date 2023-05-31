package com.example.cprogrammingclub.learning

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeFragmentViewModel @Inject constructor(private val homeFragmentRepository: HomeFragmentRepository) :
    ViewModel() {

    val chaptersLiveData get() = homeFragmentRepository.chaptersLiveData

    fun getAllChapters() {
        viewModelScope.launch {
            homeFragmentRepository.getChapters()
        }
    }

}