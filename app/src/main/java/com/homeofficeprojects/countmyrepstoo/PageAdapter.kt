package com.homeofficeprojects.countmyrepstoo

import android.util.Log
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

class PageAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {
    override fun getItemCount() = 3
    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> {
                RepsFragment()
            }
            1 -> {
                StopwatchFragment()
            }
            2 -> {
                TimerFragment()
            }
            else -> {
                RepsFragment()
            }
        }
    }
}