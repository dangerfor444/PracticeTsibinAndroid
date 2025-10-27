package com.example.practicetsibin.feature.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.practicetsibin.data.Movie
import com.example.practicetsibin.di.DIContainer
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MovieDetailsViewModel : ViewModel() {

    private val getMovieByIdUseCase = DIContainer.getMovieByIdUseCase
    private val getFavoriteByIdUseCase = DIContainer.getFavoriteByIdUseCase
    private val isFavoriteUseCase = DIContainer.isFavoriteUseCase
    private val addToFavoritesUseCase = DIContainer.addToFavoritesUseCase
    private val removeFromFavoritesUseCase = DIContainer.removeFromFavoritesUseCase

    private val _movie = MutableStateFlow<Movie?>(null)
    val movie: StateFlow<Movie?> = _movie.asStateFlow()
    
    private val _isFavorite = MutableStateFlow(false)
    val isFavorite: StateFlow<Boolean> = _isFavorite.asStateFlow()
    
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()
    
    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    fun loadMovieDetails(movieId: String) {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                _error.value = null
                
                val favoriteMovie = getFavoriteByIdUseCase(movieId)
                if (favoriteMovie != null) {
                    _movie.value = favoriteMovie
                    _isFavorite.value = true
                } else {
                    val result = getMovieByIdUseCase(movieId)
                    result.fold(
                        onSuccess = { movie ->
                            _movie.value = movie
                            checkFavoriteStatus(movieId)
                        },
                        onFailure = { exception ->
                            _error.value = "Ошибка загрузки деталей: ${exception.message}"
                        }
                    )
                }
            } finally {
                _isLoading.value = false
            }
        }
    }
    
    private fun checkFavoriteStatus(movieId: String) {
        viewModelScope.launch {
            _isFavorite.value = isFavoriteUseCase(movieId)
        }
    }
    
    fun toggleFavorite() {
        val movie = _movie.value ?: return
        val isCurrentlyFavorite = _isFavorite.value
        
        viewModelScope.launch {
            try {
                if (isCurrentlyFavorite) {
                    removeFromFavoritesUseCase(movie.id)
                } else {
                    addToFavoritesUseCase(movie)
                }
                _isFavorite.value = !isCurrentlyFavorite
            } catch (e: Exception) {
                _error.value = "Ошибка изменения избранного: ${e.message}"
            }
        }
    }
    
    fun retry(movieId: String) {
        loadMovieDetails(movieId)
    }
}
