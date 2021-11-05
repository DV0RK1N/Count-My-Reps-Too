package com.homeofficeprojects.countmyrepstoo

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.widget.Toolbar
import com.google.android.material.tabs.TabLayoutMediator
import com.homeofficeprojects.countmyrepstoo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val TAB = "tab"
    private var tabPosition = 0
    private lateinit var binding: ActivityMainBinding

    var minutes = ""
    var seconds = ""
    var millis = ""

    //TODO: Add SharePreferences for TabLayout, and figure out how to make TabLayout use it
    private lateinit var sharedPreferences: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        binding.viewPagerMain.adapter = PageAdapter(supportFragmentManager, lifecycle)
        sharedPreferences = getSharedPreferences(AppConstants.SHARED_PREFS, MODE_PRIVATE)


        getInfoFromIntent()
        setContentView(binding.root)
        letItTab()
        postponeEnterTransition()
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING)
        val toolbar = findViewById<Toolbar>(R.id.toolbar_main)
        setSupportActionBar(toolbar)
    }

    override fun onResume() {
        super.onResume()
        tabPosition = sharedPreferences.getInt(TAB, 0)
        binding.viewPagerMain.setCurrentItem(
            sharedPreferences.getInt(TAB, 0), false
        )
    }
    private fun getInfoFromIntent() {
        val extras = intent.extras
        if (extras != null) {
            minutes = extras.getString(AppConstants.STOPWATCH_MINUTES)!!
            seconds = extras.getString(AppConstants.STOPWATCH_SECONDS)!!
            millis = extras.getString(AppConstants.STOPWATCH_MILLIS)!!
        }
    }

    override fun onPause() {
        super.onPause()
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        tabPosition = binding.viewPagerMain.currentItem
        editor.putInt(TAB, tabPosition)
        editor.apply()
    }

    private fun letItTab() {
        binding.apply {
            TabLayoutMediator(tabLayout, viewPagerMain) { tab, position ->
                tab.text = when (position) {
                    0 -> {
                        "Reps"
                    }
                    1 -> {
                        "Stopwatch"
                    }
                    2 -> {
                        "Timer"
                    }
                    else -> {
                        "Tab ${position + 1}"
                    }
                }
            }.attach()
        }
    }
    //https://developer.android.com/guide/navigation/navigation-swipe-view?authuser=3#kotlin
}