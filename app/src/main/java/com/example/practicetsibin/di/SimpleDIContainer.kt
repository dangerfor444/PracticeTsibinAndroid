package com.example.practicetsibin.di

import com.example.practicetsibin.data.MovieRepository
import com.example.practicetsibin.data.MockMovieRepository

object SimpleDIContainer {
    
    private val _movieRepository: MovieRepository by lazy { MockMovieRepository() }
    
    fun getMovieRepository(): MovieRepository = _movieRepository
}
