package com.example.careerhub

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class ResumeMenuActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // IMPORTANT: must match your XML file name
        setContentView(R.layout.activity_resume_menu)

        // Button IDs must exist in activity_resume_menu.xml
        val btnCreateResume = findViewById<Button>(R.id.btnCreateResume)
        val btnPreviewResume = findViewById<Button>(R.id.btnPreviewResume)

        // CREATE RESUME BUTTON
        btnCreateResume.setOnClickListener {
            val intent = Intent(
                this@ResumeMenuActivity,
                ResumeFormActivity::class.java
            )
            startActivity(intent)
        }

        // PREVIEW RESUME BUTTON (optional, safe)
        btnPreviewResume.setOnClickListener {
            val intent = Intent(
                this@ResumeMenuActivity,
                ResumePreviewActivity::class.java
            )
            startActivity(intent)
        }
    }
}
