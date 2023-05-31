package com.example.cprogrammingclub.learning.problems

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.cprogrammingclub.databinding.ActivityProblemsBinding

class ProblemsActivity : AppCompatActivity() {
    private lateinit var binding:ActivityProblemsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityProblemsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val cName = intent.getStringExtra("chapterName").toString()
        binding.chapterName.text = cName
    }
}