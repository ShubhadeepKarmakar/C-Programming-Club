package com.example.cprogrammingclub.learning.problems

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.cprogrammingclub.databinding.FragmentProblemsBinding


class ProblemsFragment : Fragment() {
    private var _binding: FragmentProblemsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProblemsBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val data = arguments?.getString("chapterName").toString()
        binding.chapterName.text = data

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}