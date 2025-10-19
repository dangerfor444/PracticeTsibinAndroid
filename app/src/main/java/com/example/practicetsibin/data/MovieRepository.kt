package com.example.practicetsibin.data

interface MovieRepository {
    fun getAllMovies(): List<Movie>
    fun getMovieById(id: String): Movie?
}
