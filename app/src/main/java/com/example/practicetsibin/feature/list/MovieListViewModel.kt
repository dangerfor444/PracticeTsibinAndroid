package com.example.practicetsibin.feature.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.practicetsibin.data.Movie
import com.example.practicetsibin.di.DIContainer
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MovieListViewModel : ViewModel() {

    private val getPopularMoviesUseCase = DIContainer.getPopularMoviesUseCase
    private val searchMoviesUseCase = DIContainer.searchMoviesUseCase

    private val _movies = MutableStateFlow<List<Movie>>(emptyList())
    val movies: StateFlow<List<Movie>> = _movies.asStateFlow()
    
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()
    
    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()
    
    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()
    
    private val _currentPage = MutableStateFlow(1)
    val currentPage: StateFlow<Int> = _currentPage.asStateFlow()

    init {
        loadPopularMovies()
    }

    fun loadPopularMovies() {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                _error.value = null
                _searchQuery.value = ""
                _currentPage.value = 1
                
                val result = getPopularMoviesUseCase(1)
                result.fold(
                    onSuccess = { movies ->
                        _movies.value = movies
                    },
                    onFailure = { exception ->
                        _error.value = "Ошибка загрузки фильмов: ${exception.message}"
                    }
                )
            } finally {
                _isLoading.value = false
            }
        }
    }
    
    fun searchMovies(query: String) {
        if (query.isBlank()) {
            loadPopularMovies()
            return
        }
        
        viewModelScope.launch {
            try {
                _isLoading.value = true
                _error.value = null
                _searchQuery.value = query
                _currentPage.value = 1
                
                val result = searchMoviesUseCase(query, 1)
                result.fold(
                    onSuccess = { movies ->
                        _movies.value = movies
                    },
                    onFailure = { exception ->
                        _error.value = "Ошибка поиска: ${exception.message}"
                    }
                )
            } finally {
                _isLoading.value = false
            }
        }
    }
    
    fun loadMoreMovies() {
        val currentQuery = _searchQuery.value
        val nextPage = _currentPage.value + 1
        
        viewModelScope.launch {
            try {
                _isLoading.value = true
                _error.value = null
                
                val result = if (currentQuery.isBlank()) {
                    getPopularMoviesUseCase(nextPage)
                } else {
                    searchMoviesUseCase(currentQuery, nextPage)
                }
                
                result.fold(
                    onSuccess = { newMovies ->
                        _movies.value = _movies.value + newMovies
                        _currentPage.value = nextPage
                    },
                    onFailure = { exception ->
                        _error.value = "Ошибка загрузки: ${exception.message}"
                    }
                )
            } finally {
                _isLoading.value = false
            }
        }
    }
    
    fun retry() {
        val currentQuery = _searchQuery.value
        if (currentQuery.isBlank()) {
            loadPopularMovies()
        } else {
            searchMovies(currentQuery)
        }
    }
    
    fun clearSearch() {
        loadPopularMovies()
    }
}