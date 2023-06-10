package com.example.cprogrammingclub.authentication

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.lifecycle.ViewModelProvider
import com.example.cprogrammingclub.MainActivity
import com.example.cprogrammingclub.databinding.ActivityLoginBinding
import com.example.usertodatabase.utils.AppPreference
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.*
import javax.inject.Inject

@AndroidEntryPoint
class LoginActivity: AppCompatActivity() {
    @Inject
    lateinit var appPreference: AppPreference

    private lateinit var binding: ActivityLoginBinding

    private lateinit var authenticationViewModel: AuthenticationViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        authenticationViewModel = ViewModelProvider(this).get(AuthenticationViewModel::class.java)

        binding.btnLogin.setOnClickListener {
            hideKeyboard(this, it)
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()
            if (password.length >= 8) {
                if (isEmailValid(email)) {
                    val mIntent = Intent(this, MainActivity::class.java)
                    CoroutineScope(Dispatchers.IO).launch {
                        val flag = authenticationViewModel.login(email, password)
                        if (flag) {
                            appPreference.setSharedPerferences(email)
                            startActivity(mIntent)
                            finish()
                        } else {
                            val rootView = findViewById<View>(android.R.id.content)
                            Snackbar.make(rootView, "Invalid Credential", Snackbar.LENGTH_LONG)
                                .show()
                            binding.etPassword.setText("")
                        }
                    }


                } else {
                    binding.etEmail.error = "Invalid Email"
                }
            } else {
                binding.etPassword.error = "Password should contain at least 8 characters"
            }


        }
        binding.btnSignUp.setOnClickListener {
            intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }
        binding.btnPrivacy.setOnClickListener {

        }
        binding.btnForgetPassword.setOnClickListener {
            authenticationViewModel.forgetPassword("justforexample555@gmail.com")
        }
    }

    private fun isEmailValid(email: String): Boolean {
        val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
        return email.matches(emailPattern.toRegex())
    }

    fun hideKeyboard(context: Context, view: View) {
        val inputMethodManager =
            context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }

}