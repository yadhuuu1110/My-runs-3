package com.yadhuchoudhary.yadhu_choudhary_myruns3

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.yadhuchoudhary.yadhu_choudhary_myruns3.R
import com.yadhuchoudhary.yadhu_choudhary_myruns3.ExerciseEntry
import com.yadhuchoudhary.yadhu_choudhary_myruns3.Constants
import com.yadhuchoudhary.yadhu_choudhary_myruns3.UnitConverter
import java.text.SimpleDateFormat
import java.util.*

class ExerciseEntryAdapter(
    context: Context,
    private val exercises: List<ExerciseEntry>
) : ArrayAdapter<ExerciseEntry>(context, R.layout.list_item_exercise, exercises) {

    private val dateFormat = SimpleDateFormat("HH:mm:ss MMM dd yyyy", Locale.getDefault())

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: LayoutInflater.from(context)
            .inflate(R.layout.list_item_exercise, parent, false)

        val exercise = exercises[position]

        val tvFirstRow = view.findViewById<TextView>(R.id.tv_first_row)
        val tvSecondRow = view.findViewById<TextView>(R.id.tv_second_row)

        // First row: Input Type: Activity Type, Date
        val inputType = when (exercise.inputType) {
            Constants.INPUT_TYPE_MANUAL -> "Manual Entry"
            Constants.INPUT_TYPE_GPS -> "GPS"
            Constants.INPUT_TYPE_AUTOMATIC -> "Automatic"
            else -> "Unknown"
        }

        val activityType = if (exercise.activityType < Constants.ACTIVITY_TYPES.size) {
            Constants.ACTIVITY_TYPES[exercise.activityType]
        } else {
            "Unknown"
        }

        val dateStr = dateFormat.format(exercise.dateTime.time)
        tvFirstRow.text = "$inputType: $activityType, $dateStr"

        // Second row: Distance, Duration
        val distanceStr = UnitConverter.formatDistance(exercise.distance, context)
        val durationStr = UnitConverter.formatDuration(exercise.duration)
        tvSecondRow.text = "$distanceStr, $durationStr"

        return view
    }
}