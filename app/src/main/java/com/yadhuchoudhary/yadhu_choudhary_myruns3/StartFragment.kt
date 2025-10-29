package com.yadhuchoudhary.yadhu_choudhary_myruns3

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import androidx.fragment.app.Fragment
import com.yadhuchoudhary.yadhu_choudhary_myruns3.ManualInputActivity
import com.yadhuchoudhary.yadhu_choudhary_myruns3.MapDisplayActivity
import com.yadhuchoudhary.yadhu_choudhary_myruns3.R
import com.yadhuchoudhary.yadhu_choudhary_myruns3.Constants

class StartFragment : Fragment() {

    private lateinit var spinnerInputType: Spinner
    private lateinit var spinnerActivityType: Spinner
    private lateinit var btnStart: Button

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_start, container, false)

        initializeViews(view)
        setupSpinners()
        setupStartButton()

        return view
    }

    private fun initializeViews(view: View) {
        spinnerInputType = view.findViewById(R.id.spinner_input_type)
        spinnerActivityType = view.findViewById(R.id.spinner_activity_type)
        btnStart = view.findViewById(R.id.btn_start)
    }

    private fun setupSpinners() {
        // Input Type Spinner
        val inputTypes = arrayOf("Manual Entry", "GPS", "Automatic")
        val inputAdapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_dropdown_item,
            inputTypes
        )
        spinnerInputType.adapter = inputAdapter

        // Activity Type Spinner
        val activityAdapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_dropdown_item,
            Constants.ACTIVITY_TYPES
        )
        spinnerActivityType.adapter = activityAdapter
    }

    private fun setupStartButton() {
        btnStart.setOnClickListener {
            val inputTypePosition = spinnerInputType.selectedItemPosition
            val activityTypePosition = spinnerActivityType.selectedItemPosition

            val intent = when (inputTypePosition) {
                Constants.INPUT_TYPE_MANUAL -> {
                    Intent(requireContext(), ManualInputActivity::class.java)
                }
                else -> {
                    // GPS or Automatic
                    Intent(requireContext(), MapDisplayActivity::class.java)
                }
            }

            intent.putExtra(Constants.EXTRA_INPUT_TYPE, inputTypePosition)
            intent.putExtra(Constants.EXTRA_ACTIVITY_TYPE, activityTypePosition)

            startActivity(intent)
        }
    }
}