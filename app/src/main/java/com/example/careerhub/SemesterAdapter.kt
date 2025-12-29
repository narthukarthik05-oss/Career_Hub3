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

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvSemesterTitle: TextView = view.findViewById(R.id.tvSemesterTitle)
        val tvSgpa: TextView = view.findViewById(R.id.tvSgpa)
        val tvBacklogs: TextView = view.findViewById(R.id.tvBacklogs)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_semester, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val semester = semesters[position]
        holder.tvSemesterTitle.text = semester.name
        holder.tvSgpa.text = "SGPA: %.2f".format(semester.sgpa)
        val backlogs = semester.subjects.count { it.grade.uppercase() !in listOf("O","A+","A","B+","B") }
        holder.tvBacklogs.text = "Backlogs: $backlogs"
        holder.itemView.setOnClickListener { onClick(semester) }
    }

    override fun getItemCount(): Int = semesters.size
}
