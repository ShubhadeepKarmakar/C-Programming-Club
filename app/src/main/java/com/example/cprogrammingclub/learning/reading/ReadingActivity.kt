package com.example.cprogrammingclub.learning.reading

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.cprogrammingclub.databinding.ActivityReadingBinding
import com.example.cprogrammingclub.progressbar.ProgressViewModel
import com.example.cprogrammingclub.progressbar.model.ProgressModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ReadingActivity : AppCompatActivity() {
    private lateinit var binding: ActivityReadingBinding

    private lateinit var progressViewModel: ProgressViewModel
    private var pId:String?=null
    private var cProgress:Int? = null
    private var qProgress:Int? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityReadingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setResult(CONTEXT_INCLUDE_CODE)

        progressViewModel= ViewModelProvider(this)[ProgressViewModel::class.java]
        progressViewModel.getProgress()

        val chapterName = intent.getStringExtra("chapterName").toString()
        binding.chapterName.text = chapterName

        progressModelListLiveDataObserver(chapterName)

        binding.btnCompleted.setOnClickListener {
            if(cProgress==0){
                progressViewModel.updateProgress(pId!!, ProgressModel("a@gmail.com",chapterName, cProgress = 15,qProgress=qProgress!!,pId!!))
            }
Toast.makeText(this,"Mark as complete cn=$chapterName pId=$pId",Toast.LENGTH_SHORT).show()
//            finish()
        }


    }


    private fun progressModelListLiveDataObserver(chapterName:String) {

        progressViewModel.progressModelListLiveData.observe(this, Observer {

            for(i in it){
                if(i.chapterName==chapterName){
                    pId=i.pId
                    cProgress=i.cProgress
                    qProgress=i.qProgress
                    Log.d("progressModelListLiveDataObserver()xxxxxxxxxxxxxxx", i.toString())
                }
            }
            Log.d("Reading Activity--->>>>>>>>>", it.toString())
        })
    }
}