/*
This activity is used as a fragment container(Home Fragment, Compiler Fragment, Club Fragment etc.)

 */






package com.example.cprogrammingclub

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.cprogrammingclub.club.ClubFragment
import com.example.cprogrammingclub.databinding.ActivityMainBinding
import com.example.cprogrammingclub.learning.HomeFragment
import com.example.cprogrammingclub.notes.NotesActivity
import com.example.cprogrammingclub.progressbar.ProgressViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(){
    private lateinit var binding: ActivityMainBinding

    private lateinit var progressViewModel: ProgressViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        progressViewModel= ViewModelProvider(this)[ProgressViewModel::class.java]
        progressViewModel.initializeProgress()//Check Point
        progressViewModel.getProgress()//Check Point
        progressObserver()
        progressModelListLiveDataObserver()//Delete


        // Handle bottom navigation item clicks
        binding.bottomNav.setOnItemSelectedListener { MenuItem ->

            when (MenuItem.itemId) {
                R.id.bnav_home -> {
                    replaceFragment(HomeFragment())
                }
                R.id.bnav_make_notes -> {
                    intent= Intent(this,NotesActivity::class.java)
                    getResult.launch(intent)
                }
                R.id.bnav_club -> {
                    replaceFragment(ClubFragment())
                }
                R.id.bnav_profile -> {
                    replaceFragment(ProfileFragment())
                }
            }
            true
        }
        // Set the home fragment as the default fragment
        replaceFragment(HomeFragment())

    }
    private fun progressObserver() {
        progressViewModel.progressLiveData.observe(this, Observer {
            binding.progressBarPercent.progress= it
            binding.progressPercent.text= "$it%"
        })
    }
    private fun progressModelListLiveDataObserver() {
        progressViewModel.progressModelListLiveData.observe(this, Observer {
            val s = it.toString()
            Log.d("progressModelListLiveDataObserver()````````", s)
        })
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .commit()
    }

    fun hideBottomNavigationBar() {
        binding.bottomNav.visibility = View.GONE
    }

    fun showBottomNavigationBar() {
        binding.bottomNav.visibility = View.VISIBLE
    }

    private val getResult =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) {
            if (it.resultCode == Activity.CONTEXT_INCLUDE_CODE) {
                val mIntent = intent
                finish()
                startActivity(mIntent)
                Toast.makeText(this, "///////////////////////////", Toast.LENGTH_SHORT).show()
            }
        }
}