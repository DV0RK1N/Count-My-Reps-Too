package com.homeofficeprojects.countmyrepstoo

import android.content.Intent
import android.os.Bundle
import android.transition.Fade
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import kotlinx.android.synthetic.main.fragment_stopwatch.view.*


class StopwatchFragment : Fragment() {

    lateinit var minutesTextView: TextView
    lateinit var secondsTextView: TextView
    lateinit var millisTextView: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_stopwatch, container, false)

        val fade = Fade()

        activity?.title = "Fragment Stopwatch"

        minutesTextView = view.textView_stopwatch_minutes_fragment
        secondsTextView = view.textView_stopwatch_seconds_fragment
        millisTextView = view.textView_stopwatch_millis_fragment
        view.button_start_stopwatch_fragment.setOnClickListener {
            startStopwatchActivity()
        }

        activity?.window?.enterTransition = fade
        activity?.window?.exitTransition = fade
        return view
    }

    private fun startStopwatchActivity() {
        val intent = Intent(activity, StopwatchActivity::class.java)
        val options = ActivityOptionsCompat.makeSceneTransitionAnimation(
            activity!!,
            Pair.create(minutesTextView, minutesTextView.transitionName),
            Pair.create(secondsTextView, secondsTextView.transitionName),
            Pair.create(millisTextView, millisTextView.transitionName)
        )

        startActivity(intent, options.toBundle())
    }
}