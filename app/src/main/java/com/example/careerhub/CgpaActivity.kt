package com.example.careerhub

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore

class CgpaActivity : AppCompatActivity() {

    private val semesters = mutableListOf<Semester>()
    private lateinit var adapter: SemesterAdapter
    private lateinit var rvSemesters: RecyclerView
    private lateinit var tvFinalCGPA: TextView
    private lateinit var btnViewCgpa: Button
    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cgpa)

        rvSemesters = findViewById(R.id.rvSemesters)
        tvFinalCGPA = findViewById(R.id.tvFinalCGPA)
        btnViewCgpa = findViewById(R.id.btnViewCgpa)

        adapter = SemesterAdapter(semesters) { semester ->
            val intent = Intent(this, SubjectEntryActivity::class.java)
            intent.putExtra("SEMESTER_NAME", semester.name)
            startActivity(intent)
        }

        rvSemesters.layoutManager = LinearLayoutManager(this)
        rvSemesters.adapter = adapter

        loadSemesters()

        btnViewCgpa.setOnClickListener {
            val cgpa = calculateFinalCGPA()
            val intent = Intent(this, CgpaResultActivity::class.java)
            intent.putExtra("CGPA", cgpa)
            startActivity(intent)
        }
    }

    private fun loadSemesters() {
        val names = listOf("1-1","1-2","2-1","2-2","3-1","3-2","4-1","4-2")
        semesters.clear()

        names.forEach { name ->
            db.collection("semesters").document(name).get()
                .addOnSuccessListener { doc ->
                    val sgpa = doc.getDouble("sgpa") ?: 0.0
                    semesters.add(Semester(name, sgpa))
                    adapter.notifyDataSetChanged()
                    updateFinalCGPAUI()
                }
                .addOnFailureListener {
                    semesters.add(Semester(name, 0.0))
                    adapter.notifyDataSetChanged()
                    updateFinalCGPAUI()
                }
        }
    }

    private fun calculateFinalCGPA(): Double {
        val valid = semesters.map { it.sgpa }.filter { it > 0 }
        return if (valid.isNotEmpty()) valid.average() else 0.0
    }

    private fun updateFinalCGPAUI() {
        tvFinalCGPA.text = "Final CGPA: %.2f".format(calculateFinalCGPA())
    }
}
