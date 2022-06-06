package com.dinhcuong.airfly.Activity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.RadioButton
import androidx.viewpager.widget.ViewPager
import com.dinhcuong.airfly.Adapter.SettingViewPagerAdapter
import com.dinhcuong.airfly.Fragment.SettingAccountFragment
import com.dinhcuong.airfly.Fragment.SettingThemeFragment
import com.dinhcuong.airfly.R
import com.dinhcuong.airfly.Storage.SharedPrefManager
import com.google.android.material.tabs.TabLayout

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

//        val sharedPreferences = applicationContext.getSharedPreferences(SharedPrefManager.SHARED_PREF_NAME, Context.MODE_PRIVATE)
//        val nameImageBackground = sharedPreferences.getString("theme",
//            SharedPrefManager.DEFAULT_THEME
//        )
//        when(nameImageBackground){
//            "background_theme1" -> checkTheme(applicationContext, "theme1")
//            "background_theme2" -> checkTheme(applicationContext, "theme2")
//            "background_theme3" -> checkTheme(applicationContext, "theme3")
//        }


        val pager = findViewById<ViewPager>(R.id.view_pager)
        val tabLayout = findViewById<TabLayout>(R.id.tab_layout)


        // Initializing the ViewPagerAdapter
        val adapter = SettingViewPagerAdapter(supportFragmentManager)

        // add fragment to the list
        adapter.addFragment(SettingThemeFragment(), "THEME")
        adapter.addFragment(SettingAccountFragment(), "ACCOUNT")

        // Adding the Adapter to the ViewPager
        pager.adapter = adapter

        // bind the viewPager with the TabLayout.
        tabLayout.setupWithViewPager(pager)


    }

    fun radioButtonHandle(view: View) {
        if (view is RadioButton) {
            // Is the button now checked?
            val checked = view.isChecked
            // Check which radio button was clicked
            when (view.getId()) {
                R.id.theme1 -> {
                    if(checked){
                        SharedPrefManager.getInstance(applicationContext).changeBackground("background_theme1")
                        uncheck(applicationContext, "theme2", "theme3")
                    }
                }
                R.id.theme2 -> {
                    if (checked) {
                        SharedPrefManager.getInstance(applicationContext).changeBackground("background_theme2")
                        uncheck(applicationContext, "theme1", "theme3")
                    }
                }
                R.id.theme3 -> {
                    if (checked){
                        SharedPrefManager.getInstance(applicationContext).changeBackground("background_theme3")
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

    fun checkTheme(context: Context, idTheme: String){
        val radioTheme = findViewById<RadioButton>(resources.getIdentifier(
            idTheme, "id",
            context.packageName,
        ))
        radioTheme.isChecked = true
    }
}