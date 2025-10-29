package com.yadhuchoudhary.yadhu_choudhary_myruns3

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MapDisplayActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map_display)

        supportActionBar?.title = "Map Display"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        findViewById<Button>(R.id.btn_map_cancel).setOnClickListener {
            finish()
        }
    }
}