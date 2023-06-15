package com.example.cprogrammingclub

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.example.cprogrammingclub.authentication.LoginActivity
import com.example.usertodatabase.utils.AppPreference
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {

    @Inject
    lateinit var appPreference: AppPreference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)


        val handler = Handler(Looper.getMainLooper())
        handler.postDelayed({
            if (appPreference.getSharedPerferences() == null||appPreference.getSharedPerferences() == "") {
                intent = Intent(this, LoginActivity::class.java)
            } else {
                intent = Intent(this, MainActivity::class.java)
            }
            startActivity(intent)
            finish()
        }, 2500)





    }
}