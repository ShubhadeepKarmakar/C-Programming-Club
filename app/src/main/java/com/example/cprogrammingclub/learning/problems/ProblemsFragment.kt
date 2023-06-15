package com.example.cprogrammingclub.learning.problems

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.example.cprogrammingclub.MainActivity
import com.example.cprogrammingclub.databinding.FragmentProblemsBinding
import com.example.cprogrammingclub.learning.reading.ReadingViewModel
import com.example.cprogrammingclub.utils.NetworkResult
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProblemsFragment : Fragment() {
    private var _binding: FragmentProblemsBinding? = null
    private val binding get() = _binding!!

    private val problemsViewModel by activityViewModels<ProblemsViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProblemsBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity() as MainActivity).hideBottomNavAndToolBar()

        val chapterName = arguments?.getString("chapterName").toString()
//        binding.chapterName.text = data
        problemsViewModel.getContent(chapterName)

        contentObserver()
    }
    private fun contentObserver() {
        problemsViewModel.problemsLiveData.observe(viewLifecycleOwner, Observer {
            binding.progressBar.isVisible = false
            when (it) {
                is NetworkResult.Success -> {
                    binding.markdownView.loadMarkdownFromUrl(it.data?.get(0)!!.cProblems)
                }
                is NetworkResult.Error -> {
                    Toast.makeText(requireContext(), it.message.toString(), Toast.LENGTH_SHORT)
                        .show()
                }
                is NetworkResult.Loading -> {
                    binding.progressBar.isVisible = true
                }
            }
        })
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        //To show the bottom nav and toolbar when the fragment will destroy
        (requireActivity() as MainActivity).showBottomNavAndToolBar()
    }
}