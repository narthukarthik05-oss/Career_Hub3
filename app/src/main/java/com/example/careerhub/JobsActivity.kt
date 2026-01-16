package com.example.careerhub

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class JobsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_jobs)

        // SharedPreferences
        val prefs = getSharedPreferences("settings", MODE_PRIVATE)
        val softwareEnabled = prefs.getBoolean("software_jobs", true)
        val governmentEnabled = prefs.getBoolean("government_jobs", true)

        // Views
        val btnFindJobs = findViewById<Button>(R.id.btnFindJobs)
        val btnNotificationSettings = findViewById<Button>(R.id.btnNotificationSettings)
        val txtResult = findViewById<TextView>(R.id.txtResult)

        // Skill CheckBoxes (Skill Name → Checkbox)
        val skillMap = mapOf(
            "Java" to findViewById<CheckBox>(R.id.skillJava),
            "Kotlin" to findViewById<CheckBox>(R.id.skillKotlin),
            "Python" to findViewById<CheckBox>(R.id.skillPython),
            "HTML & CSS" to findViewById<CheckBox>(R.id.skillHTML),
            "JavaScript" to findViewById<CheckBox>(R.id.skillJS),
            "SQL" to findViewById<CheckBox>(R.id.skillSQL),
            "Firebase" to findViewById<CheckBox>(R.id.skillFirebase),
            "UI / UX" to findViewById<CheckBox>(R.id.skillUI),
            "Machine Learning" to findViewById<CheckBox>(R.id.skillML),
            "Cloud" to findViewById<CheckBox>(R.id.skillCloud),
            "Spring Boot" to findViewById<CheckBox>(R.id.skillSpring),
            "ReactJS" to findViewById<CheckBox>(R.id.skillReact),
            "Angular" to findViewById<CheckBox>(R.id.skillAngular),
            "NodeJS" to findViewById<CheckBox>(R.id.skillNode),
            "DevOps" to findViewById<CheckBox>(R.id.skillDevOps),
            "Digital Marketing" to findViewById<CheckBox>(R.id.skillMarketing),
            "Finance" to findViewById<CheckBox>(R.id.skillFinance),
            "HR" to findViewById<CheckBox>(R.id.skillHR),
            "Content Writing" to findViewById<CheckBox>(R.id.skillContent),
            "Graphic Design" to findViewById<CheckBox>(R.id.skillDesign),
            "Sales" to findViewById<CheckBox>(R.id.skillSales)
        )

        // Skill → Job Mapping
        val skillToJobs = mapOf(
            "Java" to listOf("Android Developer", "Backend Developer"),
            "Kotlin" to listOf("Android Developer"),
            "Python" to listOf("Python Developer", "Data Analyst", "ML Engineer"),
            "HTML & CSS" to listOf("Frontend Developer", "Web Designer"),
            "JavaScript" to listOf("Frontend Developer", "Full Stack Developer"),
            "SQL" to listOf("Database Administrator", "Data Analyst"),
            "Firebase" to listOf("Android Developer", "Backend Developer"),
            "UI / UX" to listOf("UI Designer", "UX Researcher"),
            "Machine Learning" to listOf("ML Engineer", "AI Engineer"),
            "Cloud" to listOf("Cloud Engineer", "DevOps Engineer"),
            "Spring Boot" to listOf("Java Backend Developer"),
            "ReactJS" to listOf("React Developer", "Frontend Developer"),
            "Angular" to listOf("Angular Developer"),
            "NodeJS" to listOf("NodeJS Developer", "Backend Developer"),
            "DevOps" to listOf("DevOps Engineer"),
            "Digital Marketing" to listOf("Digital Marketing Executive"),
            "Finance" to listOf("Accountant", "Financial Analyst"),
            "HR" to listOf("HR Executive", "Recruiter"),
            "Content Writing" to listOf("Content Writer"),
            "Graphic Design" to listOf("Graphic Designer"),
            "Sales" to listOf("Sales Executive", "Business Development Executive")
        )

        // FIND JOBS BUTTON
        btnFindJobs.setOnClickListener {

            if (!softwareEnabled && !governmentEnabled) {
                Toast.makeText(
                    this,
                    "Job notifications are disabled in settings",
                    Toast.LENGTH_LONG
                ).show()
                return@setOnClickListener
            }

            // Selected skills
            val selectedSkills = skillMap
                .filter { it.value.isChecked }
                .map { it.key }

            if (selectedSkills.isEmpty()) {
                Toast.makeText(
                    this,
                    "Please select at least one skill",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            // Find matching jobs
            val matchedJobs = mutableSetOf<String>()
            selectedSkills.forEach { skill ->
                skillToJobs[skill]?.let { jobs ->
                    matchedJobs.addAll(jobs)
                }
            }

            val jobType = buildString {
                if (softwareEnabled) append("Software Jobs ")
                if (governmentEnabled) append("Government Jobs")
            }

            // Show result
            txtResult.text = """
                ✅ Selected Skills:
                ${selectedSkills.joinToString(", ")}

                💼 Matching Jobs:
                ${matchedJobs.joinToString("\n• ", prefix = "• ")}

                🔍 Category:
                $jobType
            """.trimIndent()

        }
    }
}
