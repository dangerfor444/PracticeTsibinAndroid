package com.example.practicetsibin.data

data class FilterSettings(
    val titleQuery: String = "",
    val genres: List<String> = emptyList(),
    val minRating: Double = 0.0,
    val minYear: Int = 0,
    val maxYear: Int = 2024
)
