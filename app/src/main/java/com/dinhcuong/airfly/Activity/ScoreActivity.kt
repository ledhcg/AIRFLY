package com.dinhcuong.airfly.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dinhcuong.airfly.API.ApiInterface
import com.dinhcuong.airfly.API.RetrofitClient
import com.dinhcuong.airfly.Adapter.ScoreApdater
import com.dinhcuong.airfly.Model.Users
import com.dinhcuong.airfly.R
import com.dinhcuong.airfly.Storage.SharedPrefManager

class ScoreActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_score)

        val progressCircular = findViewById<ProgressBar>(R.id.progress_circular)
        val buttonBack = findViewById<Button>(R.id.button_back)

        buttonBack.setOnClickListener {
            val intent = Intent(applicationContext, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }
        val recyclerview = findViewById<RecyclerView>(R.id.recycler_view)

        // this creates a vertical layout Manager
        recyclerview.layoutManager = LinearLayoutManager(this)

        val retrofit = RetrofitClient.getInstance()
        val apiInterface = retrofit.create(ApiInterface::class.java)
        lifecycleScope.launchWhenCreated {
            try {
                val response = apiInterface.getScore("get-all")
                if (response.isSuccessful) {
                    val data: List<Users>? = response.body()
                    val adapter = data?.let { ScoreApdater(it) }
                    // Setting the Adapter with the recyclerview
                    recyclerview.adapter = adapter
                    progressCircular.visibility = View.GONE
                } else {
                    Toast.makeText(
                        this@ScoreActivity,
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