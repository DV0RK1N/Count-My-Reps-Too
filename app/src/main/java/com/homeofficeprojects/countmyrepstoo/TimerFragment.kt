package com.homeofficeprojects.countmyrepstoo

import android.app.TimePickerDialog
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.transition.Fade
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.NumberPicker
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import com.homeofficeprojects.countmyrepstoo.databinding.FragmentTimerBinding


class TimerFragment : Fragment() {

    //TODO: Fix Sharedpreferences time entered
    private lateinit var sharedPreferences: SharedPreferences
    private var _binding: FragmentTimerBinding? = null
    private val binding
        get() = _binding!!

    private var pickedHour = 0
    private var pickedMinute = 0
    private var pickedSecond = 0

    override fun onPause() {
        super.onPause()
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.putInt(AppConstants.TIMER_HOURS, pickedHour)
        editor.putInt(AppConstants.TIMER_MINUTES, pickedMinute)
        editor.putInt(AppConstants.TIMER_SECONDS, pickedSecond)
        editor.apply()
    }

    override fun onResume() {
        super.onResume()
        binding.apply {
            pickedHour = sharedPreferences.getInt(AppConstants.TIMER_HOURS, 0)
            pickedMinute = sharedPreferences.getInt(AppConstants.TIMER_MINUTES, 0)
            pickedSecond = sharedPreferences.getInt(AppConstants.TIMER_SECONDS, 0)

            timePickerHour.value = pickedHour
            timePickerMinute.value = pickedMinute
            timePickerSecond.value = pickedSecond
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentTimerBinding.inflate(inflater, container, false)
        val view = binding.root
        sharedPreferences = requireActivity().getSharedPreferences(
            AppConstants.SHARED_PREFS,
            AppCompatActivity.MODE_PRIVATE
        )
                binding.apply {
            timePickerHour.apply {
                minValue = 0
                maxValue = 99
                setFormatter { i -> String.format("%02d", i) }
                setOnValueChangedListener { picker, oldVal, newVal ->
                    pickedHour = newVal
                }
            }
            timePickerMinute.apply {
                minValue = 0
                maxValue = 99
                setFormatter { i -> String.format("%02d", i) }
                setOnValueChangedListener { picker, oldVal, newVal ->
                    pickedMinute = newVal
                }
            }
            timePickerSecond.apply {
                minValue = 0
                maxValue = 99
                setFormatter { i -> String.format("%02d", i) }
                setOnValueChangedListener { picker, oldVal, newVal ->
                    pickedSecond = newVal
                }
            }
        }
        binding.buttonStartTimerFragment.setOnClickListener {
            startTimerActivity()
        }
        return view
    }

    private fun startTimerActivity() {
        val intent = Intent(activity, TimerActivity::class.java)
        intent.putExtra(AppConstants.TIMER_HOURS, pickedHour)
        intent.putExtra(AppConstants.TIMER_MINUTES, pickedMinute)
        intent.putExtra(AppConstants.TIMER_SECONDS, pickedSecond)
        startActivity(intent)
        /*binding.apply {
            val options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                requireActivity(),
                Pair.create(
                    textViewStopwatchMinutesFragment,
                    textViewStopwatchMinutesFragment.transitionName
                ),
                Pair.create(
                    textViewStopwatchSecondsFragment,
                    textViewStopwatchSecondsFragment.transitionName
                ),
                Pair.create(
                    textViewStopwatchMillisFragment,
                    textViewStopwatchMillisFragment.transitionName
                )
            )
            startActivity(intent, options.toBundle())
        }*/
    }


}