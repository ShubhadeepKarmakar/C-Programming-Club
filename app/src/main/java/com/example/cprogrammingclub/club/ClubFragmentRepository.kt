package com.example.cprogrammingclub.club

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.usertodatabase.utils.Constants
import com.example.whatsapp.chat.MessageRequestModel
import com.example.whatsapp.chat.MessageResponseModel
import io.appwrite.ID
import io.appwrite.exceptions.AppwriteException
import io.appwrite.services.Databases
import javax.inject.Inject

class ClubFragmentRepository @Inject constructor(private val databases: Databases) {

    private val _messagesLiveData = MutableLiveData<List<MessageResponseModel>>()
    val messagesLiveData get() = _messagesLiveData


    suspend fun createMessage(messageRequestModel: MessageRequestModel) {
            try {
                val response = databases.createDocument(
                    databaseId = Constants.DATABASE_ID,
                    collectionId = Constants.GROUP_CHAT_COLLECTION_ID,
                    documentId = ID.unique(),
                    data = messageRequestModel
                )

            }catch (e: AppwriteException){
                Log.e("Appwrite/createMessage", "Error: " + e.message)
            }
            getMessages()

    }
   suspend fun getMessages() {
            try {
                val response = databases.listDocuments(
                    databaseId = Constants.DATABASE_ID,
                    collectionId = Constants.GROUP_CHAT_COLLECTION_ID,
                )
                val messages: List<MessageResponseModel> = response.documents.map {
                    MessageResponseModel(
                        emailId=it.data["emailId"] as String,
                        message = it.data["message"] as String,
                        messageId = it.data["\$id"] as String,
                        timeStamp = it.data["\$createdAt"] as String
                    )
                }
                _messagesLiveData.postValue(messages)
            } catch (e: Exception) {
                Log.e("Appwrite/getMessages", "Error: " + e.message)
            }
                Log.d(ContentValues.TAG, "Document Retrieved")
    }
}
