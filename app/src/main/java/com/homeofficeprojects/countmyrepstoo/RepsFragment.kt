package com.homeofficeprojects.countmyrepstoo

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
import com.homeofficeprojects.countmyrepstoo.databinding.FragmentRepsBinding


class RepsFragment : Fragment() {
    private var _binding: FragmentRepsBinding ?= null

    private val binding
        get() = _binding!!

    var rep = 0
    private lateinit var contextForRepsFragment: Context
    private lateinit var imm: InputMethodManager


    override fun onAttach(context: Context) {
        super.onAttach(context)
        contextForRepsFragment = context
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRepsBinding.inflate(inflater, container, false)
        val view = binding.root
        imm =
            activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        binding.apply {
            buttonAddOneRep.setOnClickListener {
                addRep(view)
            }
            buttonSubtractOneRep.setOnClickListener {
                subtractRep(view)
            }
            buttonResetReps.setOnClickListener {
                setRepsToZero(view)
            }
            buttonSetReps.setOnClickListener {
                openSetReps(view)
                editTextSetReps.requestFocus()
            }
        }
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun addRep(view: View) {
        getRep(view)
        returnRep(++rep, view)
    }

    private fun subtractRep(view: View) {
        getRep(view)
        returnRep(if (rep == 0) rep else --rep, view)
    }

    private fun openSetReps(view: View) {
        binding.apply {
            buttonSetReps.visibility = View.INVISIBLE
            editTextSetReps.apply {
                visibility = View.VISIBLE
                setOnFocusChangeListener { v, hasFocus ->
                    if (hasFocus) {
                        Toast.makeText(
                            contextForRepsFragment,
                            "Set number of Reps",
                            Toast.LENGTH_SHORT
                        )
                            .show()
                        imm.showSoftInput(editTextSetReps, InputMethodManager.SHOW_IMPLICIT)
                    } else {
                        imm.hideSoftInputFromWindow(view.windowToken, 0)
                        editTextSetReps.visibility = View.VISIBLE
                        visibility = View.INVISIBLE
                        getRep(view)
                        rep = editTextSetReps.text.toString().toInt()
                        returnRep(rep, view)
                    }
                }
            }
        }
    }

    private fun setRepsToZero(view: View) {
        getRep(view)
        rep = 0
        returnRep(rep, view)
    }

    private fun getRep(view: View): Int {
        return binding.textViewCurrentReps.text.toString().toInt()
    }

    private fun returnRep(rep: Int, view: View) {
        binding.textViewCurrentReps.text = rep.toString()
    }


}