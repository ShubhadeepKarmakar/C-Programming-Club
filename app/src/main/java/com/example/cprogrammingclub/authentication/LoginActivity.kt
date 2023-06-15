package com.example.cprogrammingclub.authentication

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.example.cprogrammingclub.MainActivity
import com.example.cprogrammingclub.R
import com.example.cprogrammingclub.databinding.ActivityLoginBinding
import com.example.cprogrammingclub.progressbar.ProgressViewModel
import com.example.usertodatabase.utils.AppPreference
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.*
import javax.inject.Inject

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {
    @Inject
    lateinit var appPreference: AppPreference

    private lateinit var binding: ActivityLoginBinding

    private lateinit var authenticationViewModel: AuthenticationViewModel
    private lateinit var progressViewModel: ProgressViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        authenticationViewModel = ViewModelProvider(this).get(AuthenticationViewModel::class.java)
        progressViewModel = ViewModelProvider(this).get(ProgressViewModel::class.java)

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
                            //creating progress status for this user
                            progressViewModel.initializeProgress(email)

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
            val url = "https://www.privacypolicies.com/live/a0dd363e-2639-46ad-8bbd-982c810a4841"
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(url)
            startActivity(intent)
        }
        binding.btnForgetPassword.setOnClickListener {
            showCustomDialog()
        }


        //OAuth2 section
        binding.btnGoogle.setOnClickListener {
            authenticationViewModel.createOAuth2Session(
                this, "google"
            )
        }
        binding.btnFacebook.setOnClickListener {
            authenticationViewModel.createOAuth2Session(
                this, "facebook"
            )
        }
        binding.btnLinkedin.setOnClickListener {
            authenticationViewModel.createOAuth2Session(
                this, "linkedin"
            )
        }
        binding.btnGithub.setOnClickListener {
            authenticationViewModel.createOAuth2Session(
                this, "github"
            )
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

    private fun showCustomDialog() {
        val dialog = Dialog(this)
        dialog.setContentView(R.layout.dialog_custom)

        val buttonClose = dialog.findViewById<Button>(R.id.buttonClose)
        val etEmail = dialog.findViewById<EditText>(R.id.dialogEmail)
        val textInputEditText = dialog.findViewById<TextInputLayout>(R.id.dialogTextInputEditText)
        val responseMessage = dialog.findViewById<TextView>(R.id.dialogResponseMessage)
        val prgressbar = dialog.findViewById<ProgressBar>(R.id.dialogProgressBar)

        buttonClose.setOnClickListener {
            val email = etEmail.text.toString()
            if (isEmailValid(email)) {
                textInputEditText.visibility = View.GONE
                prgressbar.visibility = View.VISIBLE

                CoroutineScope(Dispatchers.IO).launch {
                    val flag = authenticationViewModel.forgetPassword(email)
                    if (flag) {
                        runOnUiThread { //to post the UI update task to the main thread
                            responseMessage.text =
                                "A password reset link has been sent to your email."
                            prgressbar.visibility = View.GONE
                            responseMessage.visibility = View.VISIBLE
                            buttonClose.text = "Close"
                            buttonClose.setOnClickListener {
                                dialog.dismiss()
                            }
                        }
                    } else {
                        runOnUiThread {   //to post the UI update task to the main thread
                            responseMessage.text =
                                "The provided email does not belong to an existing user!"
                            prgressbar.visibility = View.GONE
                            responseMessage.visibility = View.VISIBLE
                            buttonClose.text = "Close"
                        }
                        buttonClose.setOnClickListener {
                            dialog.dismiss()
                        }
                    }
                }

            } else {
                etEmail.error = "Invalid Email"
            }
        }
        dialog.show()
    }
}