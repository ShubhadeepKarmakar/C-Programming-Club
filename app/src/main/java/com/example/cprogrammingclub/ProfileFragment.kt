package com.example.cprogrammingclub

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.example.cprogrammingclub.databinding.FragmentHomeBinding
import com.example.cprogrammingclub.databinding.FragmentProfileBinding
import com.example.cprogrammingclub.learning.HomeFragmentViewModel
import com.example.cprogrammingclub.progressbar.ProgressViewModel


class ProfileFragment : Fragment() {
    private var _binding: FragmentProfileBinding?=null
    private val binding get() = _binding!!




    private val progressViewModel by activityViewModels<ProgressViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //        progressViewModel.createProgress()//Check Point
//        progressViewModel.getProgress()//Check Point

        progressObserver()
    }
    private fun progressObserver() {
        progressViewModel.progressLiveData.observe(viewLifecycleOwner, Observer {
            binding.progressBarPercent.progress= it
            binding.progressPercent.text= "$it%"
        })
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}