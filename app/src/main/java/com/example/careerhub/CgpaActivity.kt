package com.example.careerhub

import android.content.Intent
import android.os.Bundle
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
    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cgpa)

        tvFinalCGPA = findViewById(R.id.tvFinalCGPA)
        rvSemesters = findViewById(R.id.rvSemesters)

        adapter = SemesterAdapter(semesters) { semester ->
            val intent = Intent(this, SubjectEntryActivity::class.java)
            intent.putExtra("SEMESTER_NAME", semester.name)
            startActivity(intent)
        }

        rvSemesters.layoutManager = LinearLayoutManager(this)
        rvSemesters.adapter = adapter

        loadSemesters()
    }

    private fun loadSemesters() {
        val names = listOf("1-1","1-2","2-1","2-2","3-1","3-2","4-1","4-2")
        semesters.clear()
        names.forEach { name ->
            db.collection("semesters").document(name).get()
                .addOnSuccessListener { doc ->
                    val semester = doc.toObject(Semester::class.java) ?: Semester(name)
                    semesters.add(semester)
                    adapter.notifyDataSetChanged()
                    calculateFinalCGPA()
                }
                .addOnFailureListener {
                    semesters.add(Semester(name))
                    adapter.notifyDataSetChanged()
                    calculateFinalCGPA()
                }
        }
    }

    private fun calculateFinalCGPA() {
        val valid = semesters.mapNotNull { it.sgpa.takeIf { it > 0.0 } }
        val avg = if (valid.isNotEmpty()) valid.average() else 0.0
        tvFinalCGPA.text = "Final CGPA: %.2f".format(avg)
    }
}
