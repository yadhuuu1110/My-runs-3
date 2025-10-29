package com.yadhuchoudhary.yadhu_choudhary_myruns3

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayout
import com.yadhuchoudhary.yadhu_choudhary_myruns3.HistoryFragment
import com.yadhuchoudhary.yadhu_choudhary_myruns3.SettingsFragment
import com.yadhuchoudhary.yadhu_choudhary_myruns3.StartFragment

class MainActivity : AppCompatActivity() {

    private lateinit var tabLayout: TabLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tabLayout = findViewById(R.id.tab_layout)

        // Add tabs
        tabLayout.addTab(tabLayout.newTab().setText("START"))
        tabLayout.addTab(tabLayout.newTab().setText("HISTORY"))
        tabLayout.addTab(tabLayout.newTab().setText("SETTINGS"))

        // Set default fragment
        replaceFragment(StartFragment())

        // Handle tab selection
        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when (tab?.position) {
                    0 -> replaceFragment(StartFragment())
                    1 -> replaceFragment(HistoryFragment())
                    2 -> replaceFragment(SettingsFragment())
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }
}