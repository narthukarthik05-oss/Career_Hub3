package com.example.careerhub

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore   // ✅ ADDED

class ResumeFormActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_resume_form)

        val btnCreateResume = findViewById<Button>(R.id.btnCreateResume)

        val db = FirebaseFirestore.getInstance()   // ✅ ADDED

        btnCreateResume.setOnClickListener {

            // 🔹 ORIGINAL CODE (UNCHANGED)
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

            // ✅ FIREBASE DATA (ADDED, NO LOGIC REMOVED)
            val resumeData = hashMapOf(
                "name" to findViewById<EditText>(R.id.etName).text.toString(),
                "email" to findViewById<EditText>(R.id.etEmail).text.toString(),
                "phone" to findViewById<EditText>(R.id.etPhone).text.toString(),
                "summary" to findViewById<EditText>(R.id.etSummary).text.toString(),
                "experience" to findViewById<EditText>(R.id.etExperience).text.toString(),
                "education" to findViewById<EditText>(R.id.etEducation).text.toString(),
                "skills" to findViewById<EditText>(R.id.etSkills).text.toString(),
                "certifications" to findViewById<EditText>(R.id.etCertifications).text.toString(),
                "projects" to findViewById<EditText>(R.id.etProjects).text.toString(),
                "languages" to findViewById<EditText>(R.id.etLanguages).text.toString(),
                "awards" to findViewById<EditText>(R.id.etAwards).text.toString(),
                "volunteer" to findViewById<EditText>(R.id.etVolunteer).text.toString(),
                "createdAt" to System.currentTimeMillis()
            )

            // ✅ SAVE TO FIREBASE
            db.collection("resumes")
                .add(resumeData)
                .addOnSuccessListener {
                    Toast.makeText(this, "Resume Saved to Firebase", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener {
                    Toast.makeText(this, "Failed to save resume", Toast.LENGTH_SHORT).show()
                }

            // 🔹 ORIGINAL CODE (UNCHANGED)
            Toast.makeText(this, "Resume Saved Successfully", Toast.LENGTH_SHORT).show()

            val intent = Intent(this, ResumePreviewActivity::class.java)
            intent.putExtra("resume_data", resume)
            startActivity(intent)
        }
    }
}
