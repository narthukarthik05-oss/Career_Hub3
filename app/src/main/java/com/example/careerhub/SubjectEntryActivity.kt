package com.example.careerhub

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class SubjectEntryActivity : AppCompatActivity() {

    private lateinit var semester: Semester

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_subject_entry)

        semester = intent.getSerializableExtra("SELECTED_SEMESTER") as Semester

        val name = findViewById<EditText>(R.id.edtSubjectName)
        val credits = findViewById<EditText>(R.id.edtCredits)
        val grade = findViewById<EditText>(R.id.edtGrade)

        findViewById<Button>(R.id.btnAddSubject).setOnClickListener {
            semester.subjects.add(
                Subject(
                    name.text.toString(),
                    credits.text.toString().toInt(),
                    grade.text.toString()
                )
            )
            name.text.clear()
            credits.text.clear()
            grade.text.clear()
        }

        findViewById<Button>(R.id.btnSave).setOnClickListener {
            calculateSGPA()
            val intent = Intent()
            intent.putExtra("UPDATED_SEMESTER", semester)
            setResult(Activity.RESULT_OK, intent)
            finish()
        }
    }

    private fun calculateSGPA() {
        var points = 0.0
        var totalCredits = 0

        for (s in semester.subjects) {
            points += gradeToPoint(s.grade) * s.credits
            totalCredits += s.credits
        }
        semester.sgpa = if (totalCredits > 0) points / totalCredits else 0.0
    }

    private fun gradeToPoint(g: String): Double =
        when (g.uppercase()) {
            "O" -> 10.0
            "A+" -> 9.0
            "A" -> 8.0
            "B+" -> 7.0
            "B" -> 6.0
            else -> 0.0
        }
}
