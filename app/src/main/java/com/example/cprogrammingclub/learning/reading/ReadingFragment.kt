package com.example.cprogrammingclub.learning.reading

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.example.cprogrammingclub.databinding.FragmentReadingBinding
import com.example.cprogrammingclub.progressbar.ProgressViewModel
import com.example.cprogrammingclub.progressbar.model.ProgressModel


class ReadingFragment : Fragment() {
    private var _binding: FragmentReadingBinding? = null
    private val binding get() = _binding!!

    private var pId: String? = null
    private var cProgress: Int? = null
    private var qProgress: Int? = null

    private val progressViewModel by activityViewModels<ProgressViewModel>()
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
        binding.chapterName.text = chapterName


        progressModelListLiveDataObserver(chapterName)

        binding.btnCompleted.setOnClickListener {
            if (cProgress == 0) {
                progressViewModel.updateProgress(
                    pId!!,
                    ProgressModel(
                        "a@gmail.com",
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
                }
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}