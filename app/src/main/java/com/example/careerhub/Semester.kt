package com.example.careerhub

import java.io.Serializable

data class Semester(
    val title: String,
    val subjects: MutableList<Subject> = mutableListOf(),
    var sgpa: Double? = null,
    var backlogs: Int = 0   // ✅ ADD THIS
) : Serializable
