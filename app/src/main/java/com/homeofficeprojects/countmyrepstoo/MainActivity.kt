package com.homeofficeprojects.countmyrepstoo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import android.widget.TableLayout
import androidx.appcompat.widget.Toolbar
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING)
        val toolbar = findViewById<Toolbar>(R.id.toolbar_main)
        setSupportActionBar(toolbar)

        view_pager_main.adapter = PageAdapter(supportFragmentManager, lifecycle)
        letItTab()

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