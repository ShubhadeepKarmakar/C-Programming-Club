package com.example.cprogrammingclub.club

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.cprogrammingclub.utils.NetworkResult
import com.example.usertodatabase.utils.Constants
import com.example.whatsapp.chat.MessageRequestModel
import com.example.whatsapp.chat.MessageResponseModel
import io.appwrite.Client
import io.appwrite.ID
import io.appwrite.exceptions.AppwriteException
import io.appwrite.models.RealtimeSubscription
import io.appwrite.services.Databases
import io.appwrite.services.Realtime
import javax.inject.Inject

class ClubFragmentRepository @Inject constructor(private val databases: Databases,private val client: Client) {

    val realtime = Realtime(client)
    lateinit var subscription: RealtimeSubscription

    private val _messagesLiveData = MutableLiveData<List<MessageResponseModel>>()
    val messagesLiveData get() = _messagesLiveData

    private val _subscriptionLiveData = MutableLiveData<String>()
    val subscriptionLiveData get() = _subscriptionLiveData


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
//            getMessages()

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

    suspend fun deleteMessage(messageId: String) {
        try {
            val response = databases.deleteDocument(
                databaseId = Constants.DATABASE_ID,
                collectionId = Constants.GROUP_CHAT_COLLECTION_ID,
                documentId = messageId
            )
        } catch (e: Exception) {
            Log.e("Appwrite", "Error: " + e.message)
        }

    }




 fun subscribe(){
    Log.d("Appwrite/Realtime","Subscribed")
    // Subscribe to collections channel
    val subscription = realtime.subscribe("databases.${Constants.DATABASE_ID}.collections.${Constants.GROUP_CHAT_COLLECTION_ID}.documents") {

        if(it.payload.toString().isNotEmpty()){
            Log.d("subscribe result----------", it.payload.toString())
            _subscriptionLiveData.postValue(it.payload.toString())

        }else{
            Log.d("subscribe result----------", "nullllllllll")
        }
    }
     this.subscription=subscription
}
    fun unSubscribe(){

        Log.d("Appwrite/Realtime","Unsubscribed");
      subscription.close()
    }
}
