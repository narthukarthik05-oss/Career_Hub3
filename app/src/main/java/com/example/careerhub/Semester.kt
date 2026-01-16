package com.example.careerhub

data class Semester(
    var name: String = "",
    var sgpa: Double = 0.0,
    var subjects: MutableList<Subject> = mutableListOf()
)
