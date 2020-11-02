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
import kotlinx.android.synthetic.main.fragment_reps.*
import kotlinx.android.synthetic.main.fragment_reps.view.*
import kotlinx.android.synthetic.main.toolbar_main.*


class RepsFragment : Fragment() {

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
    ): View? {
        val view = inflater.inflate(R.layout.fragment_reps, container, false)
        imm =
            activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        view.button_add_one_rep.setOnClickListener {
            addRep(view)
        }
        view.button_subtract_one_rep.setOnClickListener {
            subtractRep(view)
        }
        view.button_reset_reps.setOnClickListener {
            setRepsToZero(view)
        }
        view.button_set_reps.setOnClickListener {
            openSetReps(view)
            view.edit_text_set_reps.requestFocus()

        }

        return view


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
        view.button_set_reps.visibility = View.INVISIBLE
        view.edit_text_set_reps.apply {
            visibility = View.VISIBLE
            setOnFocusChangeListener { v, hasFocus ->
                if (hasFocus) {
                    Toast.makeText(contextForRepsFragment, "Set number of Reps", Toast.LENGTH_SHORT)
                        .show()
                    imm.showSoftInput(view.edit_text_set_reps, InputMethodManager.SHOW_IMPLICIT)
                } else {
                    imm.hideSoftInputFromWindow(view.windowToken, 0)
                    view.button_set_reps.visibility = View.VISIBLE
                    visibility = View.INVISIBLE
                    getRep(view)
                    rep = view.edit_text_set_reps.text.toString().toInt()
                    returnRep(rep, view)
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
        return view.textView_current_reps.text.toString().toInt()
    }

    private fun returnRep(rep: Int, view: View) {
        view.textView_current_reps.text = rep.toString()
    }


}