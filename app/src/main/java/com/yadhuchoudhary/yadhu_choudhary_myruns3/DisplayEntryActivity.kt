package com.yadhuchoudhary.yadhu_choudhary_myruns3

import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.yadhuchoudhary.yadhu_choudhary_myruns3.ExerciseDatabase
import com.yadhuchoudhary.yadhu_choudhary_myruns3.ExerciseEntry
import com.yadhuchoudhary.yadhu_choudhary_myruns3.ExerciseRepository
import com.yadhuchoudhary.yadhu_choudhary_myruns3.Constants
import com.yadhuchoudhary.yadhu_choudhary_myruns3.UnitConverter
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class DisplayEntryActivity : AppCompatActivity() {

    private lateinit var tvInputType: TextView
    private lateinit var tvActivityType: TextView
    private lateinit var tvDateTime: TextView
    private lateinit var tvDuration: TextView
    private lateinit var tvDistance: TextView
    private lateinit var tvCalories: TextView
    private lateinit var tvHeartRate: TextView
    private lateinit var tvComment: TextView
    private lateinit var btnDelete: Button

    private lateinit var repository: ExerciseRepository
    private var exerciseId: Long = -1
    private var currentExercise: ExerciseEntry? = null

    private val dateFormat = SimpleDateFormat("HH:mm:ss MMM dd yyyy", Locale.getDefault())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_display_entry)

        // Enable back button
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Entry Details"

        initializeViews()

        // Initialize database
        val database = ExerciseDatabase.getDatabase(applicationContext)
        repository = ExerciseRepository(database.exerciseDao())

        // Get exercise ID from intent
        exerciseId = intent.getLongExtra(Constants.EXTRA_EXERCISE_ID, -1)

        if (exerciseId != -1L) {
            loadExerciseEntry()
        }

        setupDeleteButton()
    }

    private fun initializeViews() {
        tvInputType = findViewById(R.id.tv_display_input_type)
        tvActivityType = findViewById(R.id.tv_display_activity_type)
        tvDateTime = findViewById(R.id.tv_display_date_time)
        tvDuration = findViewById(R.id.tv_display_duration)
        tvDistance = findViewById(R.id.tv_display_distance)
        tvCalories = findViewById(R.id.tv_display_calories)
        tvHeartRate = findViewById(R.id.tv_display_heart_rate)
        tvComment = findViewById(R.id.tv_display_comment)
        btnDelete = findViewById(R.id.btn_delete)
    }

    private fun loadExerciseEntry() {
        lifecycleScope.launch {
            try {
                currentExercise = repository.getExerciseById(exerciseId)
                currentExercise?.let { exercise ->
                    displayExerciseEntry(exercise)
                }
            } catch (e: Exception) {
                Toast.makeText(
                    this@DisplayEntryActivity,
                    "Error loading exercise: ${e.message}",
                    Toast.LENGTH_SHORT
                ).show()
                finish()
            }
        }
    }

    private fun displayExerciseEntry(exercise: ExerciseEntry) {
        // Input Type
        val inputTypeStr = when (exercise.inputType) {
            Constants.INPUT_TYPE_MANUAL -> "Manual Entry"
            Constants.INPUT_TYPE_GPS -> "GPS"
            Constants.INPUT_TYPE_AUTOMATIC -> "Automatic"
            else -> "Unknown"
        }
        tvInputType.text = inputTypeStr

        // Activity Type
        val activityTypeStr = if (exercise.activityType < Constants.ACTIVITY_TYPES.size) {
            Constants.ACTIVITY_TYPES[exercise.activityType]
        } else {
            "Unknown"
        }
        tvActivityType.text = activityTypeStr

        // Date and Time
        tvDateTime.text = dateFormat.format(exercise.dateTime.time)

        // Duration
        tvDuration.text = UnitConverter.formatDuration(exercise.duration)

        // Distance (with unit conversion)
        tvDistance.text = UnitConverter.formatDistance(exercise.distance, this)

        // Calories
        tvCalories.text = "${exercise.calorie.toInt()} cals"

        // Heart Rate
        tvHeartRate.text = "${exercise.heartRate.toInt()} bpm"

        // Comment
        tvComment.text = if (exercise.comment.isEmpty()) "No comment" else exercise.comment
    }

    private fun setupDeleteButton() {
        btnDelete.setOnClickListener {
            showDeleteConfirmationDialog()
        }
    }

    private fun showDeleteConfirmationDialog() {
        AlertDialog.Builder(this)
            .setTitle("Delete Exercise")
            .setMessage("Are you sure you want to delete this exercise entry?")
            .setPositiveButton("Delete") { _, _ ->
                deleteExerciseEntry()
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun deleteExerciseEntry() {
        lifecycleScope.launch {
            try {
                currentExercise?.let { exercise ->
                    repository.delete(exercise)
                    Toast.makeText(
                        this@DisplayEntryActivity,
                        "Exercise deleted successfully",
                        Toast.LENGTH_SHORT
                    ).show()
                    finish()
                }
            } catch (e: Exception) {
                Toast.makeText(
                    this@DisplayEntryActivity,
                    "Error deleting exercise: ${e.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}