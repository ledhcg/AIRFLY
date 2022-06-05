package com.dinhcuong.airfly

import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast

class SettingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)
        val buttonBack = findViewById<Button>(R.id.button_back)

        buttonBack.setOnClickListener {
            val intent = Intent(applicationContext, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }

    }

    fun radioButtonHandle(view: View) {
        if (view is RadioButton) {
            // Is the button now checked?
            val checked = view.isChecked
            // Check which radio button was clicked
            when (view.getId()) {
                R.id.theme1 -> {
                    if(checked){
                        uncheck(applicationContext, "theme2", "theme3")
                    }
                }
                R.id.theme2 -> {
                    if (checked) {
                        uncheck(applicationContext, "theme1", "theme3")
                    }
                }
                R.id.theme3 -> {
                    if (checked){
                        uncheck(applicationContext, "theme1", "theme2")
                    }
                }
            }
        }
    }

    fun uncheck(context: Context, idThemeA: String, idThemeB: String){
        val radioThemeA = findViewById<RadioButton>(resources.getIdentifier(
            idThemeA, "id",
            context.packageName,
        ))
        val radioThemeB = findViewById<RadioButton>(resources.getIdentifier(
            idThemeB, "id",
            context.packageName,
        ))
        radioThemeA.isChecked = false
        radioThemeB.isChecked = false
    }
}