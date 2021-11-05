package com.homeofficeprojects.countmyrepstoo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.transition.Fade
import android.util.Log
import android.view.View
import android.webkit.RenderProcessGoneDetail
import androidx.transition.Visibility
import com.homeofficeprojects.countmyrepstoo.databinding.ActivityTimerBinding

class TimerActivity : AppCompatActivity() {
    //TODO: Set timer in activity when going back to the main activity
    private lateinit var binding: ActivityTimerBinding
    private lateinit var countDownTimer: CountDownTimer
    private var totalTime = 0
    private var remainingTime = 0L
    private var hours = 0
    private var minutes = 0
    private var seconds = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTimerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonStopTimer.apply {
            setOnClickListener {
                when (this.text) {
                    "Resume" -> {
                        this.text = "Stop"
                        runTimer(remainingTime)
                    }
                    "Stop" -> {
                        this.text = "Resume"
                        countDownTimer.cancel()
                    }
                }
            }
        }

        val extras = intent.extras
        if (extras != null) {
            hours = extras.getInt(AppConstants.TIMER_HOURS)
            minutes = extras.getInt(AppConstants.TIMER_MINUTES)
            seconds = extras.getInt(AppConstants.TIMER_SECONDS)

            totalTime =
                (hours * 3600000) + (minutes * 60000) + (seconds * 1000)
            formatTime(hours, minutes, seconds)
        }
        Log.d("TimerFrag", totalTime.toString())
        runTimer(totalTime.toLong())
        binding.buttonCancelTimer.setOnClickListener {
            goBackToMainActivity()
        }
    }

    private fun runTimer(totalTime: Long) {
        countDownTimer = object : CountDownTimer(totalTime, 100) {
            override fun onTick(millisUntilFinished: Long) {
                remainingTime = millisUntilFinished
                val hour = (millisUntilFinished / 3600000) % 24
                val minute = (millisUntilFinished / 60000) % 60
                val second = (millisUntilFinished / 1000) % 60
                val millis = millisUntilFinished % 100

                if (millisUntilFinished < 3600000) {
                    binding.apply {
                        textViewTimerHoursActivity.visibility = View.GONE
                        columnBetweenMinutesHours.visibility = View.GONE
                        millisSeparator.visibility = View.VISIBLE
                        textviewTimeMillisActivity.visibility = View.VISIBLE

                        textviewTimeMillisActivity.text =
                            if (millis < 10) "0$millis" else "$millis"
                    }
                }
                formatTime(hour.toInt(), minute.toInt(), second.toInt())
            }

            override fun onFinish() {
                binding.textviewTimeMillisActivity.text = "00"
                goBackToMainActivity()
                //formatTime(hours, minutes, seconds)
            }
        }.start()
    }

    private fun formatTime(hour: Int, minute: Int, second: Int) {
        binding.apply {
            textViewTimerHoursActivity.text =
                if (hour < 10) "0$hour" else "$hour"
            textViewTimerMinutesActivity.text =
                if (minute < 10) "0$minute" else "$minute"
            textViewTimerSecondsActivity.text =
                if (second < 10) "0$second" else "$second"
        }
    }

    private fun goBackToMainActivity(){
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}