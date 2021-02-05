package com.homeofficeprojects.countmyrepstoo

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import android.widget.TableLayout
import androidx.appcompat.widget.Toolbar
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private val SHARED_PREFS = "sharePrefs"
    private val TAB = "tab"
    private var tabPosition = 0

    //TODO: Add SharePreferences for TabLayout, and figure out how to make TabLayout use it

    lateinit var sharedPreferences: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE)

        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING)
        val toolbar = findViewById<Toolbar>(R.id.toolbar_main)
        setSupportActionBar(toolbar)

        view_pager_main.adapter = PageAdapter(supportFragmentManager, lifecycle)
    }

    override fun onResume() {
        super.onResume()
        letItTab()
        tabPosition = sharedPreferences.getInt(TAB, 0)
        view_pager_main.currentItem = tabPosition
        Log.d("Main", "Position: $tabPosition")
    }

    override fun onPause() {
        super.onPause()
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        tabPosition = view_pager_main.currentItem
        editor.putInt(TAB, tabPosition)

        editor.apply()

    }

    private fun letItTab() {
        TabLayoutMediator(tab_layout, view_pager_main) { tab, position ->
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

    //https://developer.android.com/guide/navigation/navigation-swipe-view?authuser=3#kotlin
}