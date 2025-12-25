package com.example.careerhub

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnCgpa = findViewById<Button>(R.id.btnCgpa)
        val btnJobs = findViewById<Button>(R.id.btnJobs)
        val btnResume = findViewById<Button>(R.id.btnResume)

        btnCgpa.setOnClickListener {
            startActivity(Intent(this, CgpaActivity::class.java))
        }

        btnJobs.setOnClickListener {
            startActivity(Intent(this, JobsActivity::class.java))
        }

        btnResume.setOnClickListener {
            startActivity(Intent(this, ResumeMenuActivity::class.java))
        }
    }
}
