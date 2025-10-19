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

    private val _movie = MutableStateFlow<Movie?>(null)
    val movie: StateFlow<Movie?> = _movie.asStateFlow()
    
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()
    
    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    fun loadMovieDetails(movieId: String) {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                _error.value = null
                
                val result = getMovieByIdUseCase(movieId)
                result.fold(
                    onSuccess = { movie ->
                        _movie.value = movie
                    },
                    onFailure = { exception ->
                        _error.value = "Ошибка загрузки деталей: ${exception.message}"
                    }
                )
            } finally {
                _isLoading.value = false
            }
        }
    }
    
    fun retry(movieId: String) {
        loadMovieDetails(movieId)
    }
}
