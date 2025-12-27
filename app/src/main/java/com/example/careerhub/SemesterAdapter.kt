package com.example.careerhub

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class SemesterAdapter(
    private val semesters: List<Semester>,
    private val onClick: (Int) -> Unit
) : RecyclerView.Adapter<SemesterAdapter.SemesterViewHolder>() {

    inner class SemesterViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvTitle: TextView = view.findViewById(R.id.tvSemesterTitle)
        val tvSgpa: TextView = view.findViewById(R.id.tvSgpa)
        val tvBacklogs: TextView = view.findViewById(R.id.tvBacklogs)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SemesterViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_semester, parent, false)
        return SemesterViewHolder(view)
    }

    override fun onBindViewHolder(holder: SemesterViewHolder, position: Int) {
        val semester = semesters[position]

        holder.tvTitle.text = semester.title
        holder.tvSgpa.text = "SGPA: ${semester.sgpa ?: "0.00"}"
        holder.tvBacklogs.text = "Backlogs: ${semester.backlogs}"

        holder.itemView.setOnClickListener {
            onClick(position)
        }
    }

    override fun getItemCount(): Int = semesters.size
}
