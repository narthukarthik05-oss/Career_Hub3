package com.example.careerhub

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ResumePreviewActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_resume_preview)

        val resumeText = intent.getStringExtra("resume_data")
        val tvResume = findViewById<TextView>(R.id.tvResume)

        tvResume.text = resumeText
    }
}
