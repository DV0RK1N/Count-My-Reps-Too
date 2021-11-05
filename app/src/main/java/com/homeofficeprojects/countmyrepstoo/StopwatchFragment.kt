package com.homeofficeprojects.countmyrepstoo

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.transition.Fade
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import com.homeofficeprojects.countmyrepstoo.databinding.FragmentStopwatchBinding


class StopwatchFragment : Fragment() {
    private var _binding: FragmentStopwatchBinding? = null
    private val binding
        get() = _binding!!

    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStopwatchBinding.inflate(inflater, container, false)
        val view = binding.root
        val fade = Fade()

        sharedPreferences = requireActivity().getSharedPreferences(
            AppConstants.SHARED_PREFS,
            AppCompatActivity.MODE_PRIVATE
        )
        binding.apply {
            textViewStopwatchMinutesFragment.text =
                sharedPreferences.getString(AppConstants.STOPWATCH_MINUTES, "00")
            textViewStopwatchSecondsFragment.text =
                sharedPreferences.getString(AppConstants.STOPWATCH_SECONDS, "00")
            textViewStopwatchMillisFragment.text =
                sharedPreferences.getString(AppConstants.STOPWATCH_MILLIS, "00")
        }
        activity?.title = "Fragment Stopwatch"
        binding.buttonStartStopwatchFragment.setOnClickListener {
            startStopwatchActivity()
        }

        activity?.window?.enterTransition = fade
        activity?.window?.exitTransition = fade

        view.viewTreeObserver.addOnPreDrawListener(object : ViewTreeObserver.OnPreDrawListener {
            override fun onPreDraw(): Boolean {
                view.viewTreeObserver.removeOnPreDrawListener(this)
                activity!!.startPostponedEnterTransition()
                return true
            }
        })
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun startStopwatchActivity() {
        val intent = Intent(activity, StopwatchActivity::class.java)
        binding.apply {
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
        }
    }
}