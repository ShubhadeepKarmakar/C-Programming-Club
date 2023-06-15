package com.example.cprogrammingclub.club

import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cprogrammingclub.MainActivity
import com.example.cprogrammingclub.databinding.FragmentClubBinding
import com.example.cprogrammingclub.progressbar.model.ProgressModel
import com.example.usertodatabase.utils.Constants
import com.example.whatsapp.chat.MessageRequestModel
import com.example.whatsapp.chat.MessageResponseModel
import dagger.hilt.android.AndroidEntryPoint
import kotlin.properties.Delegates

@AndroidEntryPoint
class ClubFragment : Fragment() {
    private var _binding: FragmentClubBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: ClubAdapter
    var adapterItemCount = 0

    private val clubFragmentViewModel by activityViewModels<ClubFragmentViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentClubBinding.inflate(inflater, container, false)
        adapter = ClubAdapter(::onLongPress)
        val parentActivity = requireActivity() as MainActivity
        parentActivity.hideBottomNavAndToolBar()

        clubFragmentViewModel.subscribe()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        clubFragmentViewModel.getMessages()


        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())


        binding.btnSend.setOnClickListener {
            val message = binding.chatText.text.toString()
            if (message.isNotEmpty()) {

                binding.chatText.setText("")
                clubFragmentViewModel.createMessage(
                    MessageRequestModel(
                        Constants.CURRENT_USER_EMAIL!!,
                        message
                    )
                )
//                binding.recyclerView.scrollToPosition(adapterItemCount)
                hideKeyboard(requireContext(), it)
            }

        }
        messageObserver()
        subscriptionObserver()

    }

    private fun subscriptionObserver() {
        clubFragmentViewModel.subscriptionLiveData.observe(viewLifecycleOwner, Observer {
            clubFragmentViewModel.getMessages()
        })
    }

    private fun messageObserver() {
        clubFragmentViewModel.messagesLiveData.observe(
            viewLifecycleOwner,
            androidx.lifecycle.Observer {
                adapter.submitList(it)
                adapterItemCount=it.count()
                //to be at the last position when community section open
                binding.recyclerView.scrollToPosition(it.count()-1)

            })
    }

    fun hideKeyboard(context: Context, view: View) {
        val inputMethodManager =
            context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }

    private fun onLongPress(messageResponseModel: MessageResponseModel) {
if (messageResponseModel.emailId==Constants.CURRENT_USER_EMAIL){

        val alertDialog = AlertDialog.Builder(requireContext())
        alertDialog.setTitle("Delete Message")
        alertDialog.setMessage("The message will be permanently deleted from database.")
        alertDialog.setNeutralButton("Delete", DialogInterface.OnClickListener { _, _ ->
            clubFragmentViewModel.deleteMessage(messageResponseModel.messageId)
        })
        alertDialog.show()
    }
        else{
    Log.e("Appwrite+++++++++++++++++++++++", "You can't delete others message" )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        val parentActivity = requireActivity() as MainActivity
        parentActivity.showBottomNavAndToolBar()
        clubFragmentViewModel.unSubscribe()
    }
}