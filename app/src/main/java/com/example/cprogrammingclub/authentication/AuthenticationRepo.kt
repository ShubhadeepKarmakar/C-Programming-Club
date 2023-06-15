package com.example.cprogrammingclub.authentication

import android.content.ContentValues
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.lifecycle.MutableLiveData
import com.example.cprogrammingclub.utils.NetworkResult
import com.example.usertodatabase.models.NoteResponseModel
import com.example.usertodatabase.utils.Constants
import com.google.gson.Gson
import io.appwrite.Client
import io.appwrite.ID
import io.appwrite.Query
import io.appwrite.exceptions.AppwriteException
import io.appwrite.services.Account
import io.appwrite.services.Databases
import javax.inject.Inject

class AuthenticationRepo @Inject constructor(
    private val client: Client,
    private val databases: Databases) {

    val account = Account(client)

    private val _userLiveData = MutableLiveData<List<UserModel>>()
    val userLiveData get() = _userLiveData
    suspend fun signUp(name: String, email: String, password: String):Boolean {
        try {
            val user = account.create(
                userId = ID.unique(),
                name = name,
                email = email,
                password = password
            )
      return true
        } catch (e: AppwriteException) {
            Log.e("Appwrite/SignUp", "Error: " + e.message)
            return false
        }
    }

    suspend fun forgetPassword(email: String):Boolean {
        try {
            val response = account.createRecovery(
                email = email,
                url = Constants.ENDPOINT
            )
            return true
        } catch (e: AppwriteException) {
            Log.e("Appwrite/ForgetPassword", "Error: " + e.message)
            return false
        }
    }

    suspend fun login(email: String, password: String): Boolean {

        try {
            val session = account.createEmailSession(
                email = email,
                password = password
            )
            return true
        } catch (e: Exception) {
            Log.e("Appwrite/Login", "Error: " + e.message)
            return false
        }
    }

    suspend fun logout(){
        account.deleteSession("current")
    }

    suspend fun createOAuth2Session(activity: ComponentActivity, provider: String) {
        try {
            account.createOAuth2Session(activity, provider)
        } catch (e: IllegalStateException) {  //for catching the login cancellation scenario
            Log.e("Appwrite/$provider/IllegalStateException", "Error: " + e.message)
        } catch (e: AppwriteException) {
            Log.e("Appwrite-OAuth with : $provider", "Error: " + e.message)
        }
    }

    suspend fun setUserData(email: String, name: String) {
        Log.d(ContentValues.TAG, "I am from setUserData method")
        try {
            val document = databases.createDocument(
                databaseId = Constants.DATABASE_ID,
                collectionId = Constants.USER_COLLECTION_ID,
                documentId = ID.unique(),
                data = Gson().toJson(UserModel(email,name))
            )

            Log.d(ContentValues.TAG, "User data saved")
        } catch (e: Exception) {
            Log.e("Appwrite", "Error: " + e.message)
        }
        Log.d(ContentValues.TAG, "User data saved")
    }
    suspend fun getUserData(){
        try {
            val response = databases.listDocuments(
                databaseId = Constants.DATABASE_ID,
                collectionId = Constants.USER_COLLECTION_ID,
                queries = listOf(
                    Query.equal(
                        "emailId",
                        Constants.CURRENT_USER_EMAIL
                    )
                )
            )
            val user: List<UserModel> = response.documents.map {
                UserModel(
                    emailId = it.data["emailId"] as String,
                    name = it.data["name"] as String
                )
            }
            _userLiveData.postValue(user)
        } catch (e: Exception) {
            Log.e("Appwrite^^^^^", "Error: " + e.message)
        }
        Log.d(ContentValues.TAG, "User Retrieved")
    }
}