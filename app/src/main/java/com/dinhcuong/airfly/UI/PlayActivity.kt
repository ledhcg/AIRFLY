package com.dinhcuong.airfly.UI

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity


class PlayActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val playView = PlayView(this)
        setContentView(playView)
    }
}
