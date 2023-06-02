package com.example.cprogrammingclub.learning.quiz

import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.example.cprogrammingclub.R
import com.example.cprogrammingclub.databinding.FragmentQuizBinding
import com.example.learningapp.quiz.QuizModel
import com.example.learningapp.quiz.QuizViewModel


class QuizFragment : Fragment() {
    private var _binding: FragmentQuizBinding? = null
    private val binding get() = _binding!!

    private val quizFragmentViewModel by activityViewModels<QuizViewModel>()

    private var currentQuestion: QuizModel? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentQuizBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val cName = arguments?.getString("chapterName").toString()
        binding.chapterName.text = cName

        quizFragmentViewModel.getQuestions(cName)

        quizFragmentViewModel.questionsLiveData.observe(viewLifecycleOwner, Observer { questions ->
            if (questions != null) {
                quizFragmentViewModel.selectedQuestionLiveData.observe(viewLifecycleOwner) { index ->
                    binding.questionNumber.text = "Question ${(index + 1)}/${questions.size}"
                    currentQuestion = questions[index]

                    val options = currentQuestion!!.options
                    binding.question.text = currentQuestion!!.question
                    binding.option1.text = options[0]
                    binding.option2.text = options[1]
                    if (options.size > 2) {
                        binding.option4.text = options[3]
                        binding.option3.text = options[2]
                        binding.option3.visibility = View.VISIBLE
                        binding.option4.visibility = View.VISIBLE
                    } else {
                        binding.option3.visibility = View.GONE
                        binding.option4.visibility = View.GONE

                    }
                }
            }
        })

        binding.btnNext.setOnClickListener {
            if (quizFragmentViewModel.selectedQuestionLiveData.value!! >= (quizFragmentViewModel.questionsLiveData.value!!.size - 2)) {
                binding.btnNext.text = getString(R.string.submit)
            }
            onNextClicked()
        }
    }

    private fun onNextClicked() {
        if (currentQuestion == null) return
        val rbSelected: RadioButton =
            view?.findViewById(binding.rbOptionsGroup.checkedRadioButtonId) ?: return
        val ans: String = rbSelected.text.toString()
        if (ans == currentQuestion!!.answer) {
            quizFragmentViewModel.correctLiveData.value =
                quizFragmentViewModel.correctLiveData.value?.inc()
        }
        binding.rbOptionsGroup.clearCheck()
        if (quizFragmentViewModel.selectedQuestionLiveData.value!! >= (quizFragmentViewModel.questionsLiveData.value!!.size - 1)) {
            //show complete dialog
            Log.d("Quiz complete", "Quiz complete")
            val alertDialog = AlertDialog.Builder(requireContext())
            alertDialog.setTitle("Quiz complete")
            alertDialog.setMessage("Score: ${quizFragmentViewModel.correctLiveData.value!!} / ${quizFragmentViewModel.questionsLiveData.value!!.size}")
            alertDialog.setNeutralButton("Done", DialogInterface.OnClickListener { _, _ ->

                requireActivity().supportFragmentManager.popBackStack() //To go back to the previous fragment by clearing the back stack
            })
            alertDialog.show()
        } else {
            quizFragmentViewModel.selectedQuestionLiveData.postValue(quizFragmentViewModel.selectedQuestionLiveData.value?.inc())
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null

    }
}