package com.example.practicetsibin.feature.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.practicetsibin.data.Movie
import com.example.practicetsibin.di.DIContainer
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class FavoritesViewModel : ViewModel() {
    
    private val getAllFavoritesUseCase = DIContainer.getAllFavoritesUseCase
    private val clearAllFavoritesUseCase = DIContainer.clearAllFavoritesUseCase
    
    private val _movies = MutableStateFlow<List<Movie>>(emptyList())
    val movies: StateFlow<List<Movie>> = _movies.asStateFlow()
    
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()
    
    init {
        loadFavorites()
    }
    
    private fun loadFavorites() {
        viewModelScope.launch {
            getAllFavoritesUseCase().collect { favorites ->
                _movies.value = favorites
            }
        }
    }
    
    fun clearAllFavorites() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                clearAllFavoritesUseCase()
            } finally {
                _isLoading.value = false
            }
        }
    }
}
