package com.example.careerhub

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore

class SubjectEntryActivity : AppCompatActivity() {

    private lateinit var semester: Semester
    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_subject_entry)

        val semesterName = intent.getStringExtra("SEMESTER_NAME") ?: "Unknown"

        // Load semester from Firebase
        db.collection("semesters").document(semesterName).get()
            .addOnSuccessListener { doc ->
                semester = doc.toObject(Semester::class.java) ?: Semester(semesterName)
            }
            .addOnFailureListener {
                semester = Semester(semesterName)
            }

        val edtName = findViewById<EditText>(R.id.edtSubjectName)
        val edtCredits = findViewById<EditText>(R.id.edtCredits)
        val edtGrade = findViewById<EditText>(R.id.edtGrade)
        val btnAdd = findViewById<Button>(R.id.btnAddSubject)
        val btnSave = findViewById<Button>(R.id.btnSave)

        btnAdd.setOnClickListener {
            if (!::semester.isInitialized) return@setOnClickListener
            val name = edtName.text.toString()
            val credits = edtCredits.text.toString().toIntOrNull() ?: 0
            val grade = edtGrade.text.toString()

            if (name.isNotEmpty() && credits > 0 && grade.isNotEmpty()) {
                semester.subjects.add(Subject(name, credits, grade))
                Toast.makeText(this, "Subject added", Toast.LENGTH_SHORT).show()
                edtName.text.clear()
                edtCredits.text.clear()
                edtGrade.text.clear()
            } else {
                Toast.makeText(this, "Enter valid details", Toast.LENGTH_SHORT).show()
            }
        }

        btnSave.setOnClickListener {
            if (!::semester.isInitialized) return@setOnClickListener
            calculateSGPA()
            db.collection("semesters").document(semester.name).set(semester)
            finish()
        }
    }

    private fun calculateSGPA() {
        var points = 0.0
        var totalCredits = 0
        semester.subjects.forEach {
            points += gradeToPoint(it.grade) * it.credits
            totalCredits += it.credits
        }
        semester.sgpa = if (totalCredits > 0) points / totalCredits else 0.0
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
private fun calculateAndUpdateSGPA(semesterId: String) {
    val db = FirebaseFirestore.getInstance()

    db.collection("semesters")
        .document(semesterId)
        .collection("subjects")
        .get()
        .addOnSuccessListener { result ->

            var totalPoints = 0.0
            var totalCredits = 0.0

            for (doc in result) {
                val credits = doc.getLong("credits") ?: 0
                val gradePoint = doc.getLong("gradePoint") ?: 0

                totalCredits += credits
                totalPoints += credits * gradePoint
            }

            val sgpa =
                if (totalCredits > 0) totalPoints / totalCredits else 0.0

            db.collection("semesters")
                .document(semesterId)
                .update("sgpa", sgpa)
        }
}
