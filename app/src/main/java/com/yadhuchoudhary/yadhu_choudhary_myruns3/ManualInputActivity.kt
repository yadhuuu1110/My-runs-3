package com.yadhuchoudhary.yadhu_choudhary_myruns3

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.yadhuchoudhary.yadhu_choudhary_myruns3.ExerciseDatabase
import com.yadhuchoudhary.yadhu_choudhary_myruns3.ExerciseEntry
import com.yadhuchoudhary.yadhu_choudhary_myruns3.ExerciseRepository
import com.yadhuchoudhary.yadhu_choudhary_myruns3.MyRunsDialogFragment
import com.yadhuchoudhary.yadhu_choudhary_myruns3.Constants
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class ManualInputActivity : AppCompatActivity(), MyRunsDialogFragment.DialogListener {

    private lateinit var tvInputType: TextView
    private lateinit var tvActivityType: TextView
    private lateinit var tvDateTime: TextView
    private lateinit var tvDuration: TextView
    private lateinit var tvDistance: TextView
    private lateinit var tvCalories: TextView
    private lateinit var tvHeartRate: TextView
    private lateinit var tvComment: TextView
    private lateinit var btnSave: Button
    private lateinit var btnCancel: Button

    private lateinit var repository: ExerciseRepository
    private var currentEntry: ExerciseEntry = ExerciseEntry()

    private val dateFormat = SimpleDateFormat("HH:mm:ss MMM dd yyyy", Locale.getDefault())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manual_input)

        // Initialize database
        val database = ExerciseDatabase.getDatabase(applicationContext)
        repository = ExerciseRepository(database.exerciseDao())

        initializeViews()
        setupExerciseEntry()
        setupClickListeners()
    }

    private fun initializeViews() {
        tvInputType = findViewById(R.id.tv_input_type)
        tvActivityType = findViewById(R.id.tv_activity_type)
        tvDateTime = findViewById(R.id.tv_date_time)
        tvDuration = findViewById(R.id.tv_duration)
        tvDistance = findViewById(R.id.tv_distance)
        tvCalories = findViewById(R.id.tv_calories)
        tvHeartRate = findViewById(R.id.tv_heart_rate)
        tvComment = findViewById(R.id.tv_comment)
        btnSave = findViewById(R.id.btn_save)
        btnCancel = findViewById(R.id.btn_cancel)
    }

    private fun setupExerciseEntry() {
        val inputType = intent.getIntExtra(Constants.EXTRA_INPUT_TYPE, Constants.INPUT_TYPE_MANUAL)
        val activityType = intent.getIntExtra(Constants.EXTRA_ACTIVITY_TYPE, 0)

        currentEntry.inputType = inputType
        currentEntry.activityType = activityType
        currentEntry.dateTime = Calendar.getInstance()

        // Update UI
        tvInputType.text = "Manual Entry"
        tvActivityType.text = Constants.ACTIVITY_TYPES[activityType]
        tvDateTime.text = dateFormat.format(currentEntry.dateTime.time)
        tvDuration.text = "0secs"
        tvDistance.text = "0.0 Miles"
        tvCalories.text = "0 cals"
        tvHeartRate.text = "0 bpm"
        tvComment.text = ""
    }

    private fun setupClickListeners() {
        // Date and Time
        findViewById<View>(R.id.layout_date_time).setOnClickListener {
            showDateTimePicker()
        }

        // Duration
        findViewById<View>(R.id.layout_duration).setOnClickListener {
            val dialog = MyRunsDialogFragment.newInstance(
                Constants.DIALOG_DURATION,
                "Duration (seconds)"
            )
            dialog.show(supportFragmentManager, "DurationDialog")
        }

        // Distance
        findViewById<View>(R.id.layout_distance).setOnClickListener {
            val dialog = MyRunsDialogFragment.newInstance(
                Constants.DIALOG_DISTANCE,
                "Distance (miles)"
            )
            dialog.show(supportFragmentManager, "DistanceDialog")
        }

        // Calories
        findViewById<View>(R.id.layout_calories).setOnClickListener {
            val dialog = MyRunsDialogFragment.newInstance(
                Constants.DIALOG_CALORIES,
                "Calories"
            )
            dialog.show(supportFragmentManager, "CaloriesDialog")
        }

        // Heart Rate
        findViewById<View>(R.id.layout_heart_rate).setOnClickListener {
            val dialog = MyRunsDialogFragment.newInstance(
                Constants.DIALOG_HEART_RATE,
                "Heart Rate (bpm)"
            )
            dialog.show(supportFragmentManager, "HeartRateDialog")
        }

        // Comment
        findViewById<View>(R.id.layout_comment).setOnClickListener {
            val dialog = MyRunsDialogFragment.newInstance(
                Constants.DIALOG_COMMENT,
                "Comment",
                currentEntry.comment
            )
            dialog.show(supportFragmentManager, "CommentDialog")
        }

        // Save button
        btnSave.setOnClickListener {
            saveExerciseEntry()
        }

        // Cancel button
        btnCancel.setOnClickListener {
            finish()
        }
    }

    private fun showDateTimePicker() {
        val calendar = currentEntry.dateTime

        DatePickerDialog(
            this,
            { _, year, month, dayOfMonth ->
                calendar.set(Calendar.YEAR, year)
                calendar.set(Calendar.MONTH, month)
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)

                // Show time picker after date is selected
                TimePickerDialog(
                    this,
                    { _, hourOfDay, minute ->
                        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
                        calendar.set(Calendar.MINUTE, minute)
                        calendar.set(Calendar.SECOND, 0)

                        currentEntry.dateTime = calendar
                        tvDateTime.text = dateFormat.format(calendar.time)
                    },
                    calendar.get(Calendar.HOUR_OF_DAY),
                    calendar.get(Calendar.MINUTE),
                    true
                ).show()
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        ).show()
    }

    override fun onDialogPositiveClick(dialogId: Int, value: String) {
        when (dialogId) {
            Constants.DIALOG_DURATION -> {
                val duration = value.toDoubleOrNull() ?: 0.0
                currentEntry.duration = duration
                tvDuration.text = "${duration.toInt()}secs"
            }
            Constants.DIALOG_DISTANCE -> {
                val distance = value.toDoubleOrNull() ?: 0.0
                currentEntry.distance = distance
                tvDistance.text = String.format("%.2f Miles", distance)
            }
            Constants.DIALOG_CALORIES -> {
                val calories = value.toDoubleOrNull() ?: 0.0
                currentEntry.calorie = calories
                tvCalories.text = "${calories.toInt()} cals"
            }
            Constants.DIALOG_HEART_RATE -> {
                val heartRate = value.toDoubleOrNull() ?: 0.0
                currentEntry.heartRate = heartRate
                tvHeartRate.text = "${heartRate.toInt()} bpm"
            }
            Constants.DIALOG_COMMENT -> {
                currentEntry.comment = value
                tvComment.text = value
            }
        }
    }

    private fun saveExerciseEntry() {
        lifecycleScope.launch {
            try {
                repository.insert(currentEntry)
                Toast.makeText(
                    this@ManualInputActivity,
                    "Exercise saved successfully",
                    Toast.LENGTH_SHORT
                ).show()
                finish()
            } catch (e: Exception) {
                Toast.makeText(
                    this@ManualInputActivity,
                    "Error saving exercise: ${e.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}