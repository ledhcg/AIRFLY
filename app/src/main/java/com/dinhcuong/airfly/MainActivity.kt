package com.dinhcuong.airfly

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.dinhcuong.airfly.API.ApiInterface
import com.dinhcuong.airfly.API.RetrofitClient
import com.dinhcuong.airfly.Model.ScreenSize
import com.dinhcuong.airfly.Storage.SharedPrefManager
import com.dinhcuong.airfly.UI.PlayActivity

class MainActivity : AppCompatActivity() {
    private val TAG = "Main Activity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ScreenSize.getScreenSize(this)
        val buttonPlay = findViewById<Button>(R.id.button_play)
        buttonPlay.setOnClickListener {
            val iPlayGame = Intent(this@MainActivity, PlayActivity::class.java)
            startActivity(iPlayGame)
            finish()
            Log.d(TAG, "Button Play Activated")
        }
        val retrofit = RetrofitClient.getInstance()
        val apiInterface = retrofit.create(ApiInterface::class.java)
        lifecycleScope.launchWhenCreated {
            try {
                val response = apiInterface.getAllUsers()
                if (response.isSuccessful) {
                    response.body()?.email.toString()
                    Toast.makeText(
                        this@MainActivity,
                        response.body()?.email.toString(),
                        Toast.LENGTH_LONG
                    ).show()

                } else {
                    Toast.makeText(
                        this@MainActivity,
                        response.body().toString(),
                        Toast.LENGTH_LONG
                    ).show()
                }
            }catch (Ex:Exception){
                Ex.localizedMessage?.let { Log.e("Error", it) }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        if(!SharedPrefManager.getInstance(applicationContext).isLoggedIn){
            val intent = Intent(applicationContext, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }
    }
}