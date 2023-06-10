package com.example.cprogrammingclub.learning.reading

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.example.cprogrammingclub.databinding.FragmentReadingBinding
import com.example.cprogrammingclub.progressbar.ProgressViewModel
import com.example.cprogrammingclub.progressbar.model.ProgressModel
import com.example.cprogrammingclub.utils.NetworkResult
import com.example.usertodatabase.utils.Constants
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ReadingFragment : Fragment() {
    private var _binding: FragmentReadingBinding? = null
    private val binding get() = _binding!!

    private var pId: String? = null
    private var cProgress = 0
    private var qProgress = 0

    private val progressViewModel by activityViewModels<ProgressViewModel>()
    private val readingViewModel by activityViewModels<ReadingViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentReadingBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val chapterName = arguments?.getString("chapterName").toString()
//        binding.chapterName.text = chapterName
        readingViewModel.getContent(chapterName)

        progressModelListLiveDataObserver(chapterName)
        contentObserver()


        binding.btnCompleted.setOnClickListener {
            if (cProgress == 0) {
                progressViewModel.updateProgress(
                    pId!!,
                    ProgressModel(
                        Constants.CURRENT_USER_EMAIL,
                        chapterName,
                        cProgress = 15,
                        qProgress = qProgress!!,
                        pId!!
                    )
                )
            }
        }
    }

    private fun progressModelListLiveDataObserver(chapterName: String) {

        progressViewModel.progressModelListLiveData.observe(viewLifecycleOwner, Observer {

            for (i in it) {
                if (i.chapterName == chapterName) {
                    pId = i.pId
                    cProgress = i.cProgress
                    qProgress = i.qProgress

                    if (cProgress > 0) {
                        binding.btnCompleted.text = "Completed"
                        binding.btnCompleted.isEnabled = false
                    }
                }
            }
        })
    }

    private fun contentObserver() {
        readingViewModel.chapterContentLiveData.observe(viewLifecycleOwner, Observer {
            binding.btnCompleted.isVisible = true
            binding.progressBar.isVisible = false
            when (it) {
                is NetworkResult.Success -> {
                    binding.markdownView.loadMarkdownFromUrl(it.data?.get(0)!!.cContent)
                }
                is NetworkResult.Error -> {
                    Toast.makeText(requireContext(), it.message.toString(), Toast.LENGTH_SHORT)
                        .show()
                }
                is NetworkResult.Loading -> {
                    binding.progressBar.isVisible = true
                    binding.btnCompleted.isVisible = false
                }
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}