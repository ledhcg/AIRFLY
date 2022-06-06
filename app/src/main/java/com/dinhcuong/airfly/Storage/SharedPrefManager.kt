package com.dinhcuong.airfly.Storage

import android.annotation.SuppressLint
import android.content.Context
import com.dinhcuong.airfly.Model.Users

class SharedPrefManager private constructor(private val mCtx: Context) {

    val isLoggedIn: Boolean
        get() {
            val sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
            return sharedPreferences.getString("email", null) != null
        }


    val user: Users
        get() {
            val sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
            return Users(
                sharedPreferences.getString("email", null),
                sharedPreferences.getString("name", null),
                sharedPreferences.getString("phone", null),
                sharedPreferences.getString("score", null)
            )
        }


    fun saveUser(user: Users) {

        val sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        editor.putString("email", user.email)
        editor.putString("name", user.name)
        editor.putString("theme", DEFAULT_THEME)

        editor.apply()

    }

    fun changeBackground(background:  String) {
        val sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("theme", background)
        editor.apply()
    }

    fun clear() {
        val sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.clear()
        editor.apply()
    }

    @SuppressLint("StaticFieldLeak")
    companion object {
        val SHARED_PREF_NAME = "CONFIG_APP"
        val DEFAULT_THEME = "background_theme3"
        @SuppressLint("StaticFieldLeak")
        private var mInstance: SharedPrefManager? = null
        @Synchronized
        fun getInstance(mCtx: Context): SharedPrefManager {
            if (mInstance == null) {
                mInstance = SharedPrefManager(mCtx)
            }
            return mInstance as SharedPrefManager
        }
    }

}
