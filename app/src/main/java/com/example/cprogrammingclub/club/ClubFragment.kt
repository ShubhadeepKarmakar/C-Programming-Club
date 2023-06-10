package com.example.cprogrammingclub.club

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cprogrammingclub.MainActivity
import com.example.cprogrammingclub.databinding.FragmentClubBinding
import com.example.usertodatabase.utils.Constants
import com.example.whatsapp.chat.MessageRequestModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ClubFragment : Fragment() {
    private var _binding: FragmentClubBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: ClubAdapter

    private val clubFragmentViewModel by activityViewModels<ClubFragmentViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentClubBinding.inflate(inflater, container, false)
        adapter = ClubAdapter()
        val parentActivity = requireActivity() as MainActivity
        parentActivity.hideBottomNavAndToolBar()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        clubFragmentViewModel.getMessages()


        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())


        binding.btnSend.setOnClickListener {
            val message = binding.chatText.text.toString()

            binding.chatText.setText("")
            clubFragmentViewModel.createMessage(
                MessageRequestModel(
                    Constants.CURRENT_USER_EMAIL!!,
                    message
                )
            )

            hideKeyboard(requireContext(), it)
        }
        messageObserver()
    }

    private fun messageObserver() {
        clubFragmentViewModel.messagesLiveData.observe(
            viewLifecycleOwner,
            androidx.lifecycle.Observer {
                adapter.submitList(it)

                scrollToLastPosition()
            })
    }

    private fun scrollToLastPosition() {
        binding.recyclerView.scrollToPosition(adapter.itemCount - 1)
    }

    fun hideKeyboard(context: Context, view: View) {
        val inputMethodManager =
            context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        val parentActivity = requireActivity() as MainActivity
        parentActivity.showBottomNavAndToolBar()
    }
}