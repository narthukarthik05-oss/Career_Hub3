package com.example.careerhub

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore

class ResumeFormActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_resume_form)

        val btnCreateResume = findViewById<Button>(R.id.btnCreateResume)
        val db = FirebaseFirestore.getInstance()

        btnCreateResume.setOnClickListener {

            val resumeText = """
                Name: ${findViewById<EditText>(R.id.etName).text}
                Email: ${findViewById<EditText>(R.id.etEmail).text}
                Phone: ${findViewById<EditText>(R.id.etPhone).text}

                Summary:
                ${findViewById<EditText>(R.id.etSummary).text}

                Experience:
                ${findViewById<EditText>(R.id.etExperience).text}

                Education:
                ${findViewById<EditText>(R.id.etEducation).text}

                Skills:
                ${findViewById<EditText>(R.id.etSkills).text}
            """.trimIndent()

            val resumeData = hashMapOf(
                "resumeText" to resumeText,
                "createdAt" to System.currentTimeMillis()
            )

            // ✅ SAVE FIRST, THEN OPEN PREVIEW
            db.collection("resumes")
                .add(resumeData)
                .addOnSuccessListener {
                    Toast.makeText(this, "Resume Saved", Toast.LENGTH_SHORT).show()

                    val intent = Intent(this, ResumePreviewActivity::class.java)
                    intent.putExtra("resume_data", resumeText)
                    startActivity(intent)
                }
                .addOnFailureListener {
                    Toast.makeText(this, "Failed to save resume", Toast.LENGTH_SHORT).show()
                }
        }
    }
}
