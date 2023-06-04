package com.example.cprogrammingclub.club

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.cprogrammingclub.R
import com.example.usertodatabase.utils.Constants
import com.example.whatsapp.chat.MessageResponseModel
import java.text.SimpleDateFormat
import java.util.*

class ClubAdapter() :
    ListAdapter<MessageResponseModel, RecyclerView.ViewHolder>(Diffutil()) {


    val ADMIN_VIEW_TYPE = 0
    val SENDER_VIEW_TYPE = 1
    val RECEIVER_VIEW_TYPE = 2


    inner class ReceiverViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val receivedChat = itemView.findViewById<TextView>(R.id.receivedChat)
        val receivedTime = itemView.findViewById<TextView>(R.id.receivedTime)
        val chatterName = itemView.findViewById<TextView>(R.id.chatterName)

        fun bind(messageResponseModel: MessageResponseModel) {
//            chatterName.text=messageResponseModel.fName
            receivedChat.text = messageResponseModel.message
            receivedTime.text = convertToIST(messageResponseModel.timeStamp)
        }
    }

    inner class SenderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val sendingChat = itemView.findViewById<TextView>(R.id.sendingChat)
        val sendingTime = itemView.findViewById<TextView>(R.id.sendingTime)
        val chatterName = itemView.findViewById<TextView>(R.id.chatterName)

        fun bind(messageResponseModel: MessageResponseModel) {
//            chatterName.text=messageResponseModel.fName
            sendingChat.text = messageResponseModel.message
            sendingTime.text = convertToIST(messageResponseModel.timeStamp)
        }

    }

    inner class AdminViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val adminChat = itemView.findViewById<TextView>(R.id.adminChat)
        val adminChatTime = itemView.findViewById<TextView>(R.id.adminChatTime)

        fun bind(messageResponseModel: MessageResponseModel) {
            adminChat.text = messageResponseModel.message
            adminChatTime.text = convertToIST(messageResponseModel.timeStamp)
        }

    }

    override fun getItemViewType(position: Int): Int {
        if (getItem(position).emailId == Constants.CURRENT_USER_EMAIL) {
            return SENDER_VIEW_TYPE
        }
        else if (getItem(position).emailId == "admin@cprogrammingclub.com") {
                return ADMIN_VIEW_TYPE
            }
        else {
                return RECEIVER_VIEW_TYPE
            }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == ADMIN_VIEW_TYPE) {
            val view =
                LayoutInflater.from(parent.context).inflate(R.layout.admin_chat, parent, false)
            return AdminViewHolder(view)
        } else if (viewType == SENDER_VIEW_TYPE) {
            val view =
                LayoutInflater.from(parent.context).inflate(R.layout.sender_chat, parent, false)
            return SenderViewHolder(view)
        } else {
            val view =
                LayoutInflater.from(parent.context).inflate(R.layout.receiver_chat, parent, false)
            return ReceiverViewHolder(view)
        }


    }

//    override fun getItemCount(): Int {
//        return itemCount
//    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val messageResponseModel = getItem(position)


        when (holder) {
            is AdminViewHolder -> {
                holder.bind(messageResponseModel)
            }
            is SenderViewHolder -> {
                holder.bind(messageResponseModel)
            }
            is ReceiverViewHolder -> {
                holder.bind(messageResponseModel)
            }
        }
    }


    class Diffutil : DiffUtil.ItemCallback<MessageResponseModel>() {
        override fun areItemsTheSame(
            oldItem: MessageResponseModel,
            newItem: MessageResponseModel
        ): Boolean {
            return oldItem.messageId == newItem.messageId
        }

        override fun areContentsTheSame(
            oldItem: MessageResponseModel,
            newItem: MessageResponseModel
        ): Boolean {
            return oldItem == newItem
        }

    }

    fun convertToIST(timestamp: String): String {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX", Locale.getDefault())
        val date = inputFormat.parse(timestamp)
//        val outputFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        val outputFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
        outputFormat.timeZone = TimeZone.getTimeZone("Asia/Kolkata")
        return outputFormat.format(date)
    }
}