package com.yadhuchoudhary.yadhu_choudhary_myruns3

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.yadhuchoudhary.yadhu_choudhary_myruns3.ProfileActivity
import com.yadhuchoudhary.yadhu_choudhary_myruns3.R

class SettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preferences, rootKey)

        // User Profile preference
        val profilePreference = findPreference<Preference>("profile")
        profilePreference?.setOnPreferenceClickListener {
            val intent = Intent(requireContext(), ProfileActivity::class.java)
            startActivity(intent)
            true
        }

        // Class Webpage preference
        val webpagePreference = findPreference<Preference>("webpage")
        webpagePreference?.setOnPreferenceClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.example.com"))
            startActivity(intent)
            true
        }
    }
}