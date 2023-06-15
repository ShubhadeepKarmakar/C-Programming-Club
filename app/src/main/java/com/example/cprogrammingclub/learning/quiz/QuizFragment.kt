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
import com.example.cprogrammingclub.MainActivity
import com.example.cprogrammingclub.R
import com.example.cprogrammingclub.databinding.FragmentQuizBinding
import com.example.cprogrammingclub.progressbar.ProgressViewModel
import com.example.cprogrammingclub.progressbar.model.ProgressModel
import com.example.learningapp.quiz.QuizModel
import com.example.learningapp.quiz.QuizViewModel
import com.example.usertodatabase.utils.Constants
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class QuizFragment : Fragment() {
    private var _binding: FragmentQuizBinding? = null
    private val binding get() = _binding!!

    lateinit var chapterName: String

    private var pId: String? = null
    private var cProgress = 0
    private var qProgress = 0

    private val quizFragmentViewModel by activityViewModels<QuizViewModel>()
    private val progressViewModel by activityViewModels<ProgressViewModel>()

    private var currentQuestion: QuizModel? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentQuizBinding.inflate(inflater, container, false)

        (requireActivity() as MainActivity).hideBottomNavAndToolBar()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        chapterName = arguments?.getString("chapterName").toString()
        binding.chapterName.text = chapterName

        quizFragmentViewModel.getQuestions(chapterName)
        progressModelListLiveDataObserver(chapterName)

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

                if (qProgress == 0) {
                    progressViewModel.updateProgress(
                        pId!!,
                        ProgressModel(
                            Constants.CURRENT_USER_EMAIL,
                            chapterName,
                            cProgress = cProgress,
                            qProgress = 2,//hardcoded
                            pId!!
                        )
                    )
                }

                requireActivity().supportFragmentManager.popBackStack() //To go back to the previous fragment by clearing the back stack

            })
            alertDialog.show()
        } else {
            quizFragmentViewModel.selectedQuestionLiveData.postValue(quizFragmentViewModel.selectedQuestionLiveData.value?.inc())
        }
    }

    private fun progressModelListLiveDataObserver(chapterName: String) {

        progressViewModel.progressModelListLiveData.observe(viewLifecycleOwner, Observer {

            for (i in it) {
                if (i.chapterName == chapterName) {
                    pId = i.pId
                    cProgress = i.cProgress
                    qProgress = i.qProgress

                    if (qProgress > 0) {

                        val alertDialog = AlertDialog.Builder(requireContext())
                        alertDialog.setTitle("Already Completed")
                        alertDialog.setMessage("Thank you")
                        alertDialog.setNeutralButton(
                            "Back to previous section",
                            DialogInterface.OnClickListener { _, _ ->
                                requireActivity().supportFragmentManager.popBackStack() //To go back to the previous fragment by clearing the back stack
                            })
                        // Prevent dialog from being closed when clicking outside
                        alertDialog.setCancelable(false)
                        alertDialog.show()
                    }
                }
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
        (requireActivity() as MainActivity).showBottomNavAndToolBar()
    }
}