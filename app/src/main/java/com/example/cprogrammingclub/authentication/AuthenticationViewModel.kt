package com.example.cprogrammingclub.authentication

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthenticationViewModel @Inject constructor(private val authenticationRepo: AuthenticationRepo) :
    ViewModel() {

    fun signUP(name:String,email: String, password: String) {
        viewModelScope.launch { authenticationRepo.signUp(name,email, password) }
    }

    fun forgetPassword(email: String) {
        viewModelScope.launch { authenticationRepo.forgetPassword(email) }
    }

    suspend fun login(email: String, password: String): Boolean {
        val job = viewModelScope.async {
            authenticationRepo.login(email, password)
        }
        return job.await()
    }

}