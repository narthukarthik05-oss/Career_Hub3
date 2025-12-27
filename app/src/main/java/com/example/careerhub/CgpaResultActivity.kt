package com.example.careerhub

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class CgpaResultActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // ✅ Correct layout
        setContentView(R.layout.activity_cgpa_result)

        val txtCgpa = findViewById<TextView>(R.id.txtCgpa)

        val cgpa = intent.getDoubleExtra("CGPA", 0.0)
        txtCgpa.text = "CGPA: %.2f".format(cgpa)

    }
}
