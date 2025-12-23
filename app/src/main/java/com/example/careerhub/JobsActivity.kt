package com.example.careerhub

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

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

        findJobs.setOnClickListener {

            val jobs = mutableSetOf<String>()

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

            if (jobs.isEmpty()) {
                result.text = "Please select at least one skill"
            } else {
                result.text = "Suggested Jobs:\n\n" + jobs.joinToString("\n• ", "• ")
            }
        }
    }
}
