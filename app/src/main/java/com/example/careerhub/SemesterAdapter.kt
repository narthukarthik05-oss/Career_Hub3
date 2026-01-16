package com.example.careerhub

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class SemesterAdapter(
    private val semesters: List<Semester>,
    private val onClick: (Semester) -> Unit
) : RecyclerView.Adapter<SemesterAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvSemester: TextView = view.findViewById(R.id.tvSemester)
        val tvSGPA: TextView = view.findViewById(R.id.tvSGPA)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_semester, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val semester = semesters[position]

        holder.tvSemester.text = "Semester ${semester.name}"
        holder.tvSGPA.text = "SGPA: %.2f".format(semester.sgpa)

        holder.itemView.setOnClickListener {
            onClick(semester)
        }
    }

    override fun getItemCount(): Int = semesters.size
}
