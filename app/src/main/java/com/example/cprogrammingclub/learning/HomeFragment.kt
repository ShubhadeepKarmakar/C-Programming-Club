package com.example.cprogrammingclub.learning

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.cprogrammingclub.*
import com.example.cprogrammingclub.databinding.FragmentHomeBinding
import com.example.cprogrammingclub.learning.problems.ProblemsActivity
import com.example.cprogrammingclub.learning.quiz.QuizActivity
import com.example.cprogrammingclub.learning.reading.ReadingActivity
import com.example.cprogrammingclub.utils.NetworkResult
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: ChapterAdapter

    private val homeFragmentViewModel by activityViewModels<HomeFragmentViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = ChapterAdapter(
            ::onChapterProblemsClicked, ::onChapterQuizClicked, ::onReadingClicked
        )
        binding.chapterList.layoutManager =
            StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL)
        binding.chapterList.adapter = adapter

        homeFragmentViewModel.getAllChapters()

        chapterObserver()
    }

    private fun chapterObserver() {

        homeFragmentViewModel.chaptersLiveData.observe(viewLifecycleOwner, Observer {
            binding.progressBar.isVisible = false
            when (it) {
                is NetworkResult.Success -> {
                    adapter.submitList(it.data)
                }
                is NetworkResult.Error -> {
                    Toast.makeText(activity, it.message.toString(), Toast.LENGTH_SHORT).show()
                }
                is NetworkResult.Loading -> {
                    binding.progressBar.isVisible = true
                }
            }
        })
    }


    private fun onReadingClicked(chapterModel: ChapterModel) {
        transferData(ReadingFragment(), chapterModel)
    }

    private fun onChapterProblemsClicked(chapterModel: ChapterModel) {
        transferData(ProblemsFragment(), chapterModel)
    }

    private fun onChapterQuizClicked(chapterModel: ChapterModel) {
        transferData(QuizeFragment(), chapterModel)

    }

    private fun transferData(fragment: Fragment, chapterModel: ChapterModel) {
        val bundle = Bundle()
        bundle.putString("chapterName", chapterModel.chapterName)
        fragment.arguments = bundle
        replaceFragment(fragment)

    }

    private fun replaceFragment(fragment: Fragment) {
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment).addToBackStack(null)
            .commit()

        val parentActivity = requireActivity() as MainActivity
        parentActivity.hideBottomNavigationBar()//to hide the bottomNavigationBar
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}