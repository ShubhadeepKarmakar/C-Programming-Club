package com.example.cprogrammingclub.club

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.whatsapp.chat.MessageRequestModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ClubFragmentViewModel @Inject constructor(private val clubFragmentRepository: ClubFragmentRepository) :
    ViewModel() {


    val messagesLiveData get() = clubFragmentRepository.messagesLiveData

    fun createMessage(messageRequestModel: MessageRequestModel) {
        viewModelScope.launch {
            clubFragmentRepository.createMessage(messageRequestModel)
        }
    }

    fun getMessages() {
        viewModelScope.launch {
            clubFragmentRepository.getMessages()
        }
    }
}