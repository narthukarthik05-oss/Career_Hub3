package com.example.careerhub

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class ResumeFormActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_resume_form)

        val btnCreateResume = findViewById<Button>(R.id.btnCreateResume)

        btnCreateResume.setOnClickListener {

            val resume = """
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

                Certifications:
                ${findViewById<EditText>(R.id.etCertifications).text}

                Projects:
                ${findViewById<EditText>(R.id.etProjects).text}

                Languages:
                ${findViewById<EditText>(R.id.etLanguages).text}

                Awards:
                ${findViewById<EditText>(R.id.etAwards).text}

                Volunteer:
                ${findViewById<EditText>(R.id.etVolunteer).text}
            """.trimIndent()

            Toast.makeText(this, "Resume Saved Successfully", Toast.LENGTH_SHORT).show()

            val intent = Intent(this, ResumePreviewActivity::class.java)
            intent.putExtra("resume_data", resume)
            startActivity(intent)
        }
    }
}
