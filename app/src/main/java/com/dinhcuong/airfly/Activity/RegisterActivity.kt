package com.dinhcuong.airfly.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.dinhcuong.airfly.API.ApiInterface
import com.dinhcuong.airfly.API.RetrofitClient
import com.dinhcuong.airfly.R

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val buttonRegister = findViewById<Button>(R.id.button_register)
        val switchLogin = findViewById<TextView>(R.id.switch_login)
        val editTextName = findViewById<EditText>(R.id.name_input)
        val editTextPhone = findViewById<EditText>(R.id.phone_input)
        val editTextEmail = findViewById<EditText>(R.id.email_input)
        val editTextPassword = findViewById<EditText>(R.id.password_input)

        switchLogin.setOnClickListener {
            val intent = Intent(applicationContext, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }

        buttonRegister.setOnClickListener {
            val name = editTextName.text.toString().trim()
            val phone = editTextPhone.text.toString().trim()
            val email = editTextEmail.text.toString().trim()
            val password = editTextPassword.text.toString().trim()
            if(name.isEmpty()){
                Toast.makeText(
                    this@RegisterActivity,
                    "Name required",
                    Toast.LENGTH_SHORT
                ).show()
                editTextName.requestFocus()
                return@setOnClickListener
            }
            if(phone.isEmpty()){
                Toast.makeText(
                    this@RegisterActivity,
                    "Phone required",
                    Toast.LENGTH_SHORT
                ).show()
                editTextPhone.requestFocus()
                return@setOnClickListener
            }
            if(email.isEmpty()){
                Toast.makeText(
                    this@RegisterActivity,
                    "Email required",
                    Toast.LENGTH_SHORT
                ).show()
                editTextEmail.requestFocus()
                return@setOnClickListener
            }
            if(password.isEmpty()){
                Toast.makeText(
                    this@RegisterActivity,
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
                    val response = apiInterface.create("create", name, phone, email, password)
                    if (response.isSuccessful) {
                        if(response.body()?.isSuccess == true){
                            Toast.makeText(
                                this@RegisterActivity,
                                "Your account has been created!",
                                Toast.LENGTH_LONG
                            ).show()
                        } else {
                            Toast.makeText(
                                this@RegisterActivity,
                                "Error. Account creation failed.!",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    } else {
                        Toast.makeText(
                            this@RegisterActivity,
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