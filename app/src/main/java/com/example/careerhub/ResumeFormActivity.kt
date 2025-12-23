package com.example.careerhub

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class ResumeFormActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_resume_form)

        val name = findViewById<EditText>(R.id.etName)
        val email = findViewById<EditText>(R.id.etEmail)
        val save = findViewById<Button>(R.id.btnSave)

        save.setOnClickListener {
            Toast.makeText(this,
                "Saved: ${name.text}",
                Toast.LENGTH_SHORT).show()
        }
    }
}
