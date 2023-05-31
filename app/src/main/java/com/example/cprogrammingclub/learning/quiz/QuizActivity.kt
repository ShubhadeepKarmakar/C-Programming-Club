package com.example.cprogrammingclub.learning.quiz

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.RadioButton
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.cprogrammingclub.R
import com.example.cprogrammingclub.databinding.ActivityQuizBinding
import com.example.cprogrammingclub.learning.HomeFragment
import com.example.learningapp.quiz.QuizModel
import com.example.learningapp.quiz.QuizViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class QuizActivity : AppCompatActivity() {
    private lateinit var binding: ActivityQuizBinding

    private lateinit var viewModel: QuizViewModel
    private var currentQuestion: QuizModel? = null

    //    private val quizViewModel by activityViewModels<QuizViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuizBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val cName = intent.getStringExtra("chapterName").toString()
        binding.chapterName.text = cName

        viewModel = ViewModelProvider(this).get(QuizViewModel::class.java)
        viewModel.getQuestions(cName)

        viewModel.questionsLiveData.observe(this, Observer { questions ->
            if (questions != null) {
                viewModel.selectedQuestionLiveData.observe(this) { index ->
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
            if (viewModel.selectedQuestionLiveData.value!! >= (viewModel.questionsLiveData.value!!.size - 2)){
                binding.btnNext.text=getString(R.string.submit)
            }
            onNextClicked()
        }
    }

    private fun onNextClicked() {
        if (currentQuestion == null) return
        val rbSelected: RadioButton =
            findViewById(binding.rbOptionsGroup.checkedRadioButtonId) ?: return
        val ans: String = rbSelected.text.toString()
        if (ans == currentQuestion!!.answer) {
            viewModel.correctLiveData.value = viewModel.correctLiveData.value?.inc()
        }
        binding.rbOptionsGroup.clearCheck()
        if (viewModel.selectedQuestionLiveData.value!! >= (viewModel.questionsLiveData.value!!.size - 1)) {
            //show complete dialog
            Log.d("Quiz complete", "Quiz complete")
            val alertDialog = AlertDialog.Builder(this)
            alertDialog.setTitle("Quiz complete")
            alertDialog.setMessage("Score: ${viewModel.correctLiveData.value!!} / ${viewModel.questionsLiveData.value!!.size}")
            alertDialog.setNeutralButton("Done", DialogInterface.OnClickListener { _, _ ->
//                val mIntent = Intent(this, HomeFragment::class.java)
//                mIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
//                mIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
//                startActivity(intent)
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragmentContainer, HomeFragment())
                    .commit()
            })
            alertDialog.show()
        } else {
            viewModel.selectedQuestionLiveData.postValue(viewModel.selectedQuestionLiveData.value?.inc())
        }
    }
}