package com.yadhuchoudhary.yadhu_choudhary_myruns3

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class ProfileActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        supportActionBar?.title = "Profile"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
}