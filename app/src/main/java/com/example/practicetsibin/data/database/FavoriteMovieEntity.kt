package com.example.practicetsibin.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_movies")
data class FavoriteMovieEntity(
    @PrimaryKey
    val id: String,
    val title: String,
    val year: Int,
    val posterUrl: String,
    val rating: Double,
    val genres: String,
    val description: String,
    val addedAt: Long = System.currentTimeMillis()
)
