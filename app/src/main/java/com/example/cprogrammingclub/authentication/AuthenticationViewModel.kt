package com.example.cprogrammingclub.authentication

import android.util.Log
import androidx.activity.ComponentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthenticationViewModel @Inject constructor(private val authenticationRepo: AuthenticationRepo) :
    ViewModel() {

    val userLiveData get() = authenticationRepo.userLiveData
    suspend fun signUP(name: String, email: String, password: String):Boolean {
        val job = viewModelScope.async { authenticationRepo.signUp(name, email, password)
//            delay(2500)
//            setUserData(email,name)
        }
return job.await()

    }

    suspend fun forgetPassword(email: String):Boolean {
//        viewModelScope.launch { return@launch authenticationRepo.forgetPassword(email) }
        val job = viewModelScope.async {
            authenticationRepo.forgetPassword(email)
        }
        return job.await()
    }

    suspend fun login(email: String, password: String): Boolean {
        val job = viewModelScope.async {
            authenticationRepo.login(email, password)
        }
        return job.await()
    }
    fun logout(){
        viewModelScope.launch { authenticationRepo.logout() }
    }

    suspend fun setUserData(email: String, name: String){
        viewModelScope.launch {
            authenticationRepo.setUserData(email,name)
        }
    }
    fun getUserData(){
        viewModelScope.launch { authenticationRepo.getUserData() }
    }
    fun createOAuth2Session(activity: ComponentActivity, provider: String) {
        viewModelScope.launch { authenticationRepo.createOAuth2Session(activity, provider) }
    }
}