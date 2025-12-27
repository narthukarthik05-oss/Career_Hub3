package com.example.careerhub

import java.io.Serializable

data class Subject(
    val name: String,
    val credits: Int,
    val grade: String
) : Serializable
