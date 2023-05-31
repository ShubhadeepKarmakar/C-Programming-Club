/*
This activity is used as a fragment container(Home Fragment, Compiler Fragment, Club Fragment etc.)

 */






package com.example.cprogrammingclub

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.cprogrammingclub.club.ClubFragment
import com.example.cprogrammingclub.compiler.CompilerFragment
import com.example.cprogrammingclub.databinding.ActivityMainBinding
import com.example.cprogrammingclub.learning.HomeFragment
import com.example.cprogrammingclub.notes.NotesActivity
import com.google.android.material.navigation.NavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(){
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Handle bottom navigation item clicks
        binding.bottomNav.setOnItemSelectedListener { MenuItem ->

            when (MenuItem.itemId) {
                R.id.bnav_home -> {
                    replaceFragment(HomeFragment())
                    true
                }
                R.id.bnav_make_notes -> {
                    intent= Intent(this,NotesActivity::class.java)
                    startActivity(intent)
                    true
                }
                R.id.bnav_compiler -> {
                    replaceFragment(CompilerFragment())
                    true
                }
                R.id.bnav_club -> {
                    replaceFragment(ClubFragment())
                    true
                }
                // Add more bottom navigation items if needed
//                else -> false
            }
            true
        }
        // Set the home fragment as the default fragment
        replaceFragment(HomeFragment())

    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .commit()
    }
}