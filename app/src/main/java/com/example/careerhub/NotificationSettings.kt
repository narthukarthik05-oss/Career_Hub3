package com.example.careerhub

import android.os.Bundle
import android.widget.Switch
import androidx.appcompat.app.AppCompatActivity

class NotificationSettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notification_settings)

        // ✅ Match IDs with XML
        val switchSoftware = findViewById<Switch>(R.id.switchSoftware)
        val switchGovernment = findViewById<Switch>(R.id.switchGovernment)

        val prefs = getSharedPreferences("settings", MODE_PRIVATE)

        // Load saved states
        switchSoftware.isChecked = prefs.getBoolean("software_jobs", true)
        switchGovernment.isChecked = prefs.getBoolean("government_jobs", true)

        // Save Software Jobs preference
        switchSoftware.setOnCheckedChangeListener { _, isChecked ->
            prefs.edit().putBoolean("software_jobs", isChecked).apply()
        }

        // Save Government Jobs preference
        switchGovernment.setOnCheckedChangeListener { _, isChecked ->
            prefs.edit().putBoolean("government_jobs", isChecked).apply()
        }
    }
}
