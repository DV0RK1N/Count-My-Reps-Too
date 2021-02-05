package com.homeofficeprojects.countmyrepstoo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import android.transition.Fade
import android.view.View
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import kotlinx.android.synthetic.main.activity_stopwatch.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class StopwatchActivity : AppCompatActivity() {

    private var stopTheCount = false

    private var stopWatchRunning = false


    private lateinit var lapList: MutableList<Long>
    var startTime = 0L
    var pauseOffset = 0L
    var delay = 70L
    var timeInMillis = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stopwatch)

        title = "Activity Stopwatch"

        val fade = Fade()
        window.enterTransition = fade
        window.exitTransition = fade

    }

    override fun onResume() {
        super.onResume()
        startTimer()
    }



    private fun startTimer(){
        stopTheCount = false
        if(!stopWatchRunning){
            stopWatchRunning = true
            startTime = SystemClock.elapsedRealtime()
            CoroutineScope(IO).launch {
                dialTone()
            }
        }
    }

    private suspend fun dialTone(){
        withContext(Main){
            countTheMillis()
        }
    }

    //Timer: Counts time Up and Prints it to The UI
    private suspend fun countTheMillis() {
        startTime = SystemClock.elapsedRealtime() - pauseOffset

        while (!stopTheCount) {
            delay(delay)
            val elapsedTime = SystemClock.elapsedRealtime()
            timeInMillis = elapsedTime - startTime

            val minutes = (timeInMillis / 1000).toInt() / 60
            val seconds = (timeInMillis / 1000).toInt() % 60
            val millis = timeInMillis % 100

            textView_stopwatch_minutes_activity_total.text =
                if (minutes < 10) "0$minutes" else minutes.toString()
            textView_stopwatch_seconds_activity_total.text =
                if (seconds < 10) "0$seconds" else seconds.toString()
            textView_stopwatch_millis_activity_total.text =
                if (millis < 10) "0$millis" else millis.toString()
        }
        stopWatchRunning = false
        pauseOffset = SystemClock.elapsedRealtime() - startTime
    }


    fun stopStopwatch(view: View) {
        val intent = Intent(this, MainActivity::class.java)
        val options = ActivityOptionsCompat.makeSceneTransitionAnimation(
            this,
            Pair.create(
                textView_stopwatch_minutes_activity,
                textView_stopwatch_minutes_activity.transitionName
            ),
            Pair.create(
                textView_stopwatch_second_activity,
                textView_stopwatch_second_activity.transitionName
            ),
            Pair.create(
                textView_stopwatch_millis_activity,
                textView_stopwatch_millis_activity.transitionName
            )
        )
        startActivity(intent, options.toBundle())
    }
}