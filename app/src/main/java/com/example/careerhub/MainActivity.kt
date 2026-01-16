package com.example.careerhub

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (FirebaseAuth.getInstance().currentUser == null) {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
            return
        }

        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.btnJobs).setOnClickListener {
            startActivity(Intent(this, JobsActivity::class.java))
        }

        findViewById<Button>(R.id.btnCgpa).setOnClickListener {
            startActivity(Intent(this, CgpaActivity::class.java))
        }

        findViewById<Button>(R.id.btnResume).setOnClickListener {
            startActivity(Intent(this, ResumeMenuActivity::class.java))
        }

        findViewById<Button>(R.id.btnNotification).setOnClickListener {
            startActivity(Intent(this, NotificationSettingsActivity::class.java))
        }
    }
}
