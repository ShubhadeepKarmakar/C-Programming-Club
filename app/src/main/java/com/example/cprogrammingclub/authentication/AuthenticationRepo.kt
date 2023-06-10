package com.example.cprogrammingclub.authentication

import android.util.Log
import com.example.usertodatabase.utils.Constants
import io.appwrite.Client
import io.appwrite.ID
import io.appwrite.exceptions.AppwriteException
import io.appwrite.services.Account
import io.appwrite.services.Databases
import kotlinx.coroutines.delay
import javax.inject.Inject

class AuthenticationRepo @Inject constructor(private val client: Client,private val databases: Databases) {
//lateinit var account:Account

    val account = Account(client)

    suspend fun signUp(name:String,email:String,password:String){
        try {
            val user = account.create(
                userId = ID.unique(),
                name = name,
                email = email,
                password = password
            )
        }catch (e:AppwriteException){
            Log.e("Appwrite/SignUp", "Error: " + e.message)
        }
    }

    suspend fun forgetPassword(email: String){
        try {
            val response = account.createRecovery(
                email = email,
                url = Constants.ENDPOINT
            )
        }catch (e:AppwriteException){
            Log.e("Appwrite/ForgetPassword", "Error: " + e.message)
        }
    }

    suspend fun login(email:String,password:String):Boolean{

        try {

            val session = account.createEmailSession(
                email = email,
                password = password

            )
            return true
        }catch (e:Exception){
            Log.e("Appwrite/Login", "Error: " + e.message)
            return false
        }
    }

}