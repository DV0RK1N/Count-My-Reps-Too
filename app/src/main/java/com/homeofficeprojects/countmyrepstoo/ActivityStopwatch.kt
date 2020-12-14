package com.homeofficeprojects.countmyrepstoo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.transition.Fade

class ActivityStopwatch : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stopwatch)

        title = "Activity Stopwatch"

        val fade = Fade()
        window.enterTransition = fade
        window.exitTransition = fade
    }
}