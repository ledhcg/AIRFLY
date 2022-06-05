package com.dinhcuong.airfly

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.dinhcuong.airfly.API.ApiInterface
import com.dinhcuong.airfly.API.RetrofitClient
import com.dinhcuong.airfly.Storage.SharedPrefManager

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val buttonLogin = findViewById<Button>(R.id.button_login)
        val editTextEmail = findViewById<EditText>(R.id.email_input)
        val editTextPassword = findViewById<EditText>(R.id.password_input)

        buttonLogin.setOnClickListener {
            val email = editTextEmail.text.toString().trim()
            val password = editTextPassword.text.toString().trim()
            if(email.isEmpty()){
                Toast.makeText(
                    this@LoginActivity,
                    "Email required",
                    Toast.LENGTH_SHORT
                ).show()
                editTextEmail.requestFocus()
                return@setOnClickListener

            }
            if(password.isEmpty()){
                Toast.makeText(
                    this@LoginActivity,
                    "Password required",
                    Toast.LENGTH_SHORT
                ).show()
                editTextPassword.requestFocus()
                return@setOnClickListener
            }

            val retrofit = RetrofitClient.getInstance()
            val apiInterface = retrofit.create(ApiInterface::class.java)
            lifecycleScope.launchWhenCreated {
                try {
                    val response = apiInterface.check("login", email, password)
                    if (response.isSuccessful) {
                        if(response.body()?.isSuccess == true){
                            SharedPrefManager.getInstance(applicationContext).saveUser(response.body()!!)
                            val intent = Intent(applicationContext, MainActivity::class.java)
                            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                            startActivity(intent)
                        } else {
                            Toast.makeText(
                                this@LoginActivity,
                                "Email or password is incorrect!",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    } else {
                        Toast.makeText(
                            this@LoginActivity,
                            "Connection error!",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }catch (Ex:Exception){
                    Ex.localizedMessage?.let { Log.e("Error", it) }
                }
            }
        }
    }
}