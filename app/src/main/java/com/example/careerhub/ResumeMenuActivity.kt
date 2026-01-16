package com.example.careerhub

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class ResumeMenuActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_resume_menu)

        findViewById<Button>(R.id.btnCreateResume).setOnClickListener {
            startActivity(Intent(this, ResumeFormActivity::class.java))
        }

        findViewById<Button>(R.id.btnPreviewResume).setOnClickListener {
            startActivity(Intent(this, ResumePreviewActivity::class.java))
        }
    }
}
