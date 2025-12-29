package com.example.careerhub

data class Semester(
    var name: String = "",
    val subjects: MutableList<Subject> = mutableListOf(),
    var sgpa: Double = 0.0
)
