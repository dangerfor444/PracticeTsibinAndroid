package com.example.practicetsibin.feature.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.practicetsibin.data.Movie
import com.example.practicetsibin.data.MovieRepository
import com.example.practicetsibin.di.SimpleDIContainer
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MovieListViewModel : ViewModel() {
    
    private val movieRepository: MovieRepository = SimpleDIContainer.getMovieRepository()

    private val _movies = MutableStateFlow<List<Movie>>(emptyList())
    val movies: StateFlow<List<Movie>> = _movies.asStateFlow()

    init {
        loadMovies()
    }

    private fun loadMovies() {
        viewModelScope.launch {
            _movies.value = movieRepository.getAllMovies()
        }
    }

    fun getMovieById(id: String): Movie? {
        return movieRepository.getMovieById(id)
    }
}
