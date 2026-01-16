package com.example.careerhub

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore

class SubjectEntryActivity : AppCompatActivity() {

    private lateinit var semester: Semester
    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_subject_entry)

        val semesterName = intent.getStringExtra("SEMESTER_NAME") ?: return finish()

        // 🔒 Initialize immediately to prevent crash
        semester = Semester(semesterName)

        db.collection("semesters").document(semesterName).get()
            .addOnSuccessListener { doc ->
                if (doc.exists()) {
                    semester = doc.toObject(Semester::class.java) ?: semester
                }
            }

        val edtName = findViewById<EditText>(R.id.edtSubjectName)
        val edtCredits = findViewById<EditText>(R.id.edtCredits)
        val edtGrade = findViewById<EditText>(R.id.edtGrade)

        findViewById<Button>(R.id.btnAddSubject).setOnClickListener {
            val name = edtName.text.toString()
            val credits = edtCredits.text.toString().toIntOrNull()
            val grade = edtGrade.text.toString()

            if (name.isBlank() || credits == null || grade.isBlank()) {
                Toast.makeText(this, "Enter valid details", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            semester.subjects.add(Subject(name, credits, grade))
            Toast.makeText(this, "Subject added", Toast.LENGTH_SHORT).show()

            edtName.text.clear()
            edtCredits.text.clear()
            edtGrade.text.clear()
        }

        findViewById<Button>(R.id.btnSave).setOnClickListener {
            calculateSGPA()
            db.collection("semesters").document(semester.name).set(semester)
            finish()
        }
    }

    private fun calculateSGPA() {
        var totalPoints = 0.0
        var totalCredits = 0

        semester.subjects.forEach {
            totalPoints += gradeToPoint(it.grade) * it.credits
            totalCredits += it.credits
        }

        semester.sgpa = if (totalCredits > 0) totalPoints / totalCredits else 0.0
    }

    private fun gradeToPoint(g: String): Double = when (g.uppercase()) {
        "O" -> 10.0
        "A+" -> 9.0
        "A" -> 8.0
        "B+" -> 7.0
        "B" -> 6.0
        else -> 0.0
    }
}
