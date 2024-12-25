package com.example.kotlin_sqlite.domain.models


data class PetsClient (
    val name: String? = null,
    val gender: String? = null,
    val dateOfBirth: String? = null,
    val distinctiveFeatures: String? = null,
    val medicalHistory: String? = null
)
