package com.example.careerhub

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore   // ✅ ADDED

class JobsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_jobs)

        val java = findViewById<CheckBox>(R.id.skillJava)
        val kotlin = findViewById<CheckBox>(R.id.skillKotlin)
        val python = findViewById<CheckBox>(R.id.skillPython)
        val html = findViewById<CheckBox>(R.id.skillHTML)
        val ui = findViewById<CheckBox>(R.id.skillUI)

        val findJobs = findViewById<Button>(R.id.btnFindJobs)
        val result = findViewById<TextView>(R.id.txtResult)

        val db = FirebaseFirestore.getInstance()   // ✅ ADDED

        findJobs.setOnClickListener {

            val jobs = mutableSetOf<String>()
            val selectedSkills = mutableListOf<String>()   // ✅ ADDED

            // 🔹 ORIGINAL LOGIC (UNCHANGED)
            if (java.isChecked || kotlin.isChecked) {
                jobs.add("Android Developer")
            }

            if (html.isChecked) {
                jobs.add("Web Developer")
            }

            if (python.isChecked) {
                jobs.add("Data Analyst")
            }

            if (ui.isChecked) {
                jobs.add("UI/UX Designer")
            }

            // ✅ FIREBASE SKILLS COLLECTION (ADDED)
            if (java.isChecked) selectedSkills.add("Java")
            if (kotlin.isChecked) selectedSkills.add("Kotlin")
            if (python.isChecked) selectedSkills.add("Python")
            if (html.isChecked) selectedSkills.add("HTML")
            if (ui.isChecked) selectedSkills.add("UI/UX")

            if (jobs.isEmpty()) {
                result.text = "Please select at least one skill"
            } else {
                result.text = "Suggested Jobs:\n\n" + jobs.joinToString("\n• ", "• ")

                // ✅ SAVE USER SKILLS + JOBS TO FIREBASE
                val jobData = hashMapOf(
                    "skills" to selectedSkills,
                    "suggestedJobs" to jobs.toList(),
                    "timestamp" to System.currentTimeMillis()
                )

                db.collection("job_searches")
                    .add(jobData)
            }
        }
    }
}
