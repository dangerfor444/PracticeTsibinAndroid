package com.example.practicetsibin.data

data class Movie(
    val id: String,
    val title: String,
    val year: Int,
    val posterUrl: String,
    val rating: Double,
    val genres: List<String>,
    val description: String
)


