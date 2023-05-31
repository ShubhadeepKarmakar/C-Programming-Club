package com.example.usertodatabase.utils

import android.content.Context
import android.content.SharedPreferences
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class AppPreference@Inject constructor(@ApplicationContext context: Context) {
private var sharedPreference=context.getSharedPreferences("login",Context.MODE_PRIVATE)

    fun setSharedPerferences(email:String){
        val editor = sharedPreference.edit()
        editor.putString("flag", email)
        editor.apply()
    }

    fun getSharedPerferences():String?{
return sharedPreference.getString("flag",null)
    }
}