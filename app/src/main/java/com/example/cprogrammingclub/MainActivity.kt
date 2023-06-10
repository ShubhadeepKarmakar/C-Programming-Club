/*
This activity is used as a fragment container(Home Fragment, Compiler Fragment, Club Fragment etc.)

 */






package com.example.cprogrammingclub

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.cprogrammingclub.club.ClubFragment
import com.example.cprogrammingclub.databinding.ActivityMainBinding
import com.example.cprogrammingclub.learning.HomeFragment
import com.example.cprogrammingclub.more.MoreFragment
import com.example.cprogrammingclub.notes.NotesFragment
import com.example.cprogrammingclub.profile.ProfileFragment
import com.example.cprogrammingclub.progressbar.ProgressViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private lateinit var progressViewModel: ProgressViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        progressViewModel = ViewModelProvider(this)[ProgressViewModel::class.java]
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
                R.id.bnav_more -> {
                    replaceFragment(MoreFragment())
                }
                R.id.bnav_make_notes -> {
                    replaceFragment(NotesFragment())
                }
                R.id.bnav_club -> {
                    replaceFragment(ClubFragment())
                }
                R.id.bnav_profile -> {
                    supportFragmentManager.beginTransaction()
                        .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_from_left)
                        .replace(R.id.fragmentContainer, ProfileFragment())
                        .commit()
                }
            }
            true
        }
        // Set the home fragment as the default fragment
        replaceFragment(HomeFragment())


    }

    private fun progressObserver() {
        progressViewModel.progressLiveData.observe(this, Observer {
            binding.progressBarPercent.progress = it
            binding.progressPercent.text = "$it%"
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

    fun hideBottomNavAndToolBar() {
        binding.bottomNav.visibility = View.GONE
        binding.toolbar.visibility = View.GONE
    }

    fun showBottomNavAndToolBar() {
        binding.bottomNav.visibility = View.VISIBLE
        binding.toolbar.visibility = View.VISIBLE
    }

    override fun onBackPressed() {
        if (binding.bottomNav.selectedItemId != R.id.bnav_home) {
            binding.bottomNav.selectedItemId = R.id.bnav_home
        } else {
            val f = this.supportFragmentManager
            if (f.backStackEntryCount > 0)
                        {
//                supportFragmentManager.beginTransaction()
//                    .replace(R.id.fragmentContainer, HomeFragment()).addToBackStack(null)
//                    .commit()

                f.popBackStack()
            } else {

                finish()
            }
        }
    }
}