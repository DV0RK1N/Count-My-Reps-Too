package com.homeofficeprojects.countmyrepstoo

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.SystemClock
import android.transition.Fade
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import com.homeofficeprojects.countmyrepstoo.databinding.ActivityStopwatchBinding
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main


class StopwatchActivity : AppCompatActivity() {

    private lateinit var binding: ActivityStopwatchBinding

    private var stopTheCount = false
    private var stopWatchRunning = false

    private lateinit var sharedPreferences: SharedPreferences
    //TODO: Stopwatch ends too late, at least one second after pressing stop

    //TODO: Add to SharedPreferences the Time from when the stopwatch stops
    private lateinit var lapList: MutableList<Long>

    var startTimeTotal = 0L
    var startTimeLap = 0L
    private lateinit var lapJob: Job
    private lateinit var totalJob: Job
    var pauseOffset = 0L
    var delay = 70L
    var timeInMillisLap = 0L
    var timeInMillisTotal = 0L


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStopwatchBinding.inflate(layoutInflater)
        setContentView(binding.root)
        sharedPreferences = getSharedPreferences(AppConstants.SHARED_PREFS, MODE_PRIVATE)

//region title
        title = "Activity Stopwatch"
        val fade = Fade()
        window.enterTransition = fade
        window.exitTransition = fade
//endregion

    }

    override fun onResume() {
        super.onResume()
        startTimer()
    }

    override fun onPause() {
        super.onPause()
        binding.apply {

            val editor: SharedPreferences.Editor = sharedPreferences.edit()
            editor.putString(
                AppConstants.STOPWATCH_MINUTES,
                textViewStopwatchMinutesActivity.text.toString()
            )
            editor.putString(
                AppConstants.STOPWATCH_SECONDS,
                textViewStopwatchSecondsActivity.text.toString()
            )
            editor.putString(
                AppConstants.STOPWATCH_MILLIS,
                textViewStopwatchMillisActivity.text.toString()
            )
            editor.apply()
        }
    }

    fun nextLap(view: View) {
        lapJob.cancel()
        lapJob = CoroutineScope(IO).launch {
            dialTone()
        }
    }

    private fun startTimer() {
        stopTheCount = false
        if (!stopWatchRunning) {
            stopWatchRunning = true
            startTimeTotal = SystemClock.elapsedRealtime()
            startTimeLap = SystemClock.elapsedRealtime()
            lapJob = CoroutineScope(IO).launch {
                dialTone()
            }
            totalJob = CoroutineScope(IO).launch {
                dialTune()
            }
        }
    }

    private suspend fun dialTone() {
        withContext(Main) {
            //TODO: How to make this work
            binding.apply {
                countTheLapTime(
                    textViewStopwatchMinutesActivity,
                    textViewStopwatchSecondsActivity,
                    textViewStopwatchMillisActivity
                )
            }
        }
    }

    private suspend fun dialTune() {
        withContext(Main) {
            binding.apply {
                countTheTotalTime(
                    textViewStopwatchMinutesActivityTotal,
                    textViewStopwatchSecondsActivityTotal,
                    textViewStopwatchMillisActivityTotal
                )
            }
        }
    }

    //Timer: Counts time Up and Prints it to The UI
    private suspend fun countTheLapTime(
        minutesView: TextView,
        secondsView: TextView,
        millisView: TextView
    ) {
        Log.d("Stopwatch", "This works 1")
        startTimeLap = SystemClock.elapsedRealtime() - pauseOffset

        while (!stopTheCount) {
            delay(delay)
            val elapsedTime = SystemClock.elapsedRealtime()
            timeInMillisLap = elapsedTime - startTimeLap

            val minutes = (timeInMillisLap / 1000).toInt() / 60
            val seconds = (timeInMillisLap / 1000).toInt() % 60
            val millis = timeInMillisLap % 100


            minutesView.text =
                if (minutes < 10) "0$minutes" else minutes.toString()
            secondsView.text =
                if (seconds < 10) "0$seconds" else seconds.toString()
            millisView.text =
                if (millis < 10) "0$millis" else millis.toString()
        }
        stopWatchRunning = false
        pauseOffset = SystemClock.elapsedRealtime() - startTimeLap
    }

    private suspend fun countTheTotalTime(
        minutesView: TextView,
        secondsView: TextView,
        millisView: TextView
    ) {
        Log.d("Stopwatch", "This works 1")
        startTimeTotal = SystemClock.elapsedRealtime() - pauseOffset

        while (!stopTheCount) {
            delay(delay)
            val elapsedTime = SystemClock.elapsedRealtime()
            timeInMillisTotal = elapsedTime - startTimeTotal

            val minutes = (timeInMillisTotal / 1000).toInt() / 60
            val seconds = (timeInMillisTotal / 1000).toInt() % 60
            val millis = timeInMillisTotal % 100


            minutesView.text =
                if (minutes < 10) "0$minutes" else minutes.toString()
            secondsView.text =
                if (seconds < 10) "0$seconds" else seconds.toString()
            millisView.text =
                if (millis < 10) "0$millis" else millis.toString()
        }
        stopWatchRunning = false
        pauseOffset = SystemClock.elapsedRealtime() - startTimeTotal
    }

    fun stopStopwatch(view: View) {
        totalJob.cancel()
        lapJob.cancel()

        val intent = Intent(this, MainActivity::class.java)
        binding.apply {

            val options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                this@StopwatchActivity,
                Pair.create(
                    textViewStopwatchMinutesActivity,
                    textViewStopwatchMinutesActivity.transitionName
                ),
                Pair.create(
                    textViewStopwatchSecondsActivity,
                    textViewStopwatchSecondsActivity.transitionName
                ),
                Pair.create(
                    textViewStopwatchMillisActivity,
                    textViewStopwatchMillisActivity.transitionName
                )
            )
            intent.putExtra(AppConstants.STOPWATCH_MINUTES, textViewStopwatchMinutesActivity.text)
            intent.putExtra(AppConstants.STOPWATCH_SECONDS, textViewStopwatchSecondsActivity.text)
            intent.putExtra(AppConstants.STOPWATCH_MILLIS, textViewStopwatchMillisActivity.text)

            startActivity(intent, options.toBundle())
        }
    }

    fun tempStartTime(view: View) {
        startTimer()
    }
}