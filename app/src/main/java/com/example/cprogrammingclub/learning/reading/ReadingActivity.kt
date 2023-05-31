package com.example.cprogrammingclub.learning.reading

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.cprogrammingclub.R
import com.example.cprogrammingclub.databinding.ActivityReadingBinding

class ReadingActivity : AppCompatActivity() {
    private lateinit var binding: ActivityReadingBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityReadingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val cName = intent.getStringExtra("chapterName").toString()
        binding.chapterName.text = cName
    }
}