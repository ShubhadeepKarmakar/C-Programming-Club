package com.example.cprogrammingclub.authentication

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import com.example.cprogrammingclub.databinding.ActivitySignUpBinding
import com.example.cprogrammingclub.progressbar.ProgressViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SignUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding

    private lateinit var authenticationViewModel: AuthenticationViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)


        authenticationViewModel = ViewModelProvider(this).get(AuthenticationViewModel::class.java)

        val lIntent = Intent(this, LoginActivity::class.java)

        val rootView = findViewById<View>(android.R.id.content)

        binding.btnSignUp.setOnClickListener {

            val firstName = binding.etFirstName.text.toString()
            val lastName = binding.etLastName.text.toString()
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()
            val confirmPassword = binding.etConfirmPassword.text.toString()

            if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                Snackbar.make(rootView, "Please fill in all the fields", Snackbar.LENGTH_LONG)
                    .show()

            } else if (!isEmailValid(email)) {
                binding.etEmail.error = "Invalid Email"
            } else if (password.length < 8) {
                binding.etPassword.error = "Password should be at least 8 characters"
            } else if (password.toString() != confirmPassword.toString()) {
                binding.etConfirmPassword.error = "Passwords do not match"
            } else if (!binding.checkBox.isChecked) {
                binding.btnPrivacy.error = "Please accept the Privacy Policy to proceed."
            } else {
                val name = "$firstName $lastName"

                CoroutineScope(Dispatchers.IO).launch {
                    val flag = authenticationViewModel.signUP(name, email, password)
                    if (flag) {
                        authenticationViewModel.setUserData(email, name)
                        runOnUiThread {
                            binding.progressBar.visibility = View.VISIBLE
                        }
                        delay(1000)
                        runOnUiThread {
                            binding.progressBar.visibility = View.GONE
                        }
                        startActivity(lIntent)
                        finish()
                    }
                }

            }

        }

        binding.btnLogin.setOnClickListener {
            startActivity(lIntent)
        }
        binding.btnPrivacy.setOnClickListener {
            val url = "https://www.privacypolicies.com/live/a0dd363e-2639-46ad-8bbd-982c810a4841"
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(url)
            startActivity(intent)
        }
    }

    private fun isEmailValid(email: String): Boolean {
        val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
        return email.matches(emailPattern.toRegex())
    }
}