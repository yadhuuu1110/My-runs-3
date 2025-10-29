package com.yadhuchoudhary.yadhu_choudhary_myruns3

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.yadhuchoudhary.yadhu_choudhary_myruns3.DisplayEntryActivity
import com.yadhuchoudhary.yadhu_choudhary_myruns3.MapDisplayActivity
import com.yadhuchoudhary.yadhu_choudhary_myruns3.R
import com.yadhuchoudhary.yadhu_choudhary_myruns3.ExerciseEntryAdapter
import com.yadhuchoudhary.yadhu_choudhary_myruns3.ExerciseDatabase
import com.yadhuchoudhary.yadhu_choudhary_myruns3.ExerciseEntry
import com.yadhuchoudhary.yadhu_choudhary_myruns3.ExerciseRepository
import com.yadhuchoudhary.yadhu_choudhary_myruns3.Constants

class HistoryFragment : Fragment() {

    private lateinit var listView: ListView
    private lateinit var repository: ExerciseRepository
    private var exercises = mutableListOf<ExerciseEntry>()
    private lateinit var adapter: ExerciseEntryAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_history, container, false)

        listView = view.findViewById(R.id.list_view_history)

        // Initialize database and repository
        val database = ExerciseDatabase.getDatabase(requireContext())
        repository = ExerciseRepository(database.exerciseDao())

        // Setup adapter
        adapter = ExerciseEntryAdapter(requireContext(), exercises)
        listView.adapter = adapter

        // Observe database changes
        repository.allExercises.observe(viewLifecycleOwner, Observer { exerciseList ->
            exerciseList?.let {
                exercises.clear()
                exercises.addAll(it)
                adapter.notifyDataSetChanged()
            }
        })

        // Setup item click listener
        listView.setOnItemClickListener { _, _, position, _ ->
            val exercise = exercises[position]

            val intent = if (exercise.inputType == Constants.INPUT_TYPE_MANUAL) {
                Intent(requireContext(), DisplayEntryActivity::class.java)
            } else {
                Intent(requireContext(), MapDisplayActivity::class.java)
            }

            intent.putExtra(Constants.EXTRA_EXERCISE_ID, exercise.id)
            startActivity(intent)
        }

        return view
    }
}