package com.dinhcuong.airfly

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import com.dinhcuong.airfly.Model.ScreenSize
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
    }
}