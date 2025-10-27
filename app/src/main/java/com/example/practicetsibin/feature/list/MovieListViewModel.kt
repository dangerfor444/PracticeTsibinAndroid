package com.example.practicetsibin.feature.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.practicetsibin.data.FilterSettings
import com.example.practicetsibin.data.Movie
import com.example.practicetsibin.di.DIContainer
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MovieListViewModel : ViewModel() {

    private val getPopularMoviesUseCase = DIContainer.getPopularMoviesUseCase
    private val searchMoviesUseCase = DIContainer.searchMoviesUseCase
    private val filterSettingsRepository = DIContainer.filterSettingsRepositoryInstance

    private val _allMovies = MutableStateFlow<List<Movie>>(emptyList())
    private val _filterSettings = MutableStateFlow(FilterSettings())
    
    val movies: StateFlow<List<Movie>> = combine(
        _allMovies,
        _filterSettings
    ) { allMovies, filters ->
        applyFilters(allMovies, filters)
    }.stateIn(
        scope = viewModelScope,
        started = kotlinx.coroutines.flow.SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )
    
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
        loadFilterSettings()
    }
    
    private fun loadFilterSettings() {
        viewModelScope.launch {
            filterSettingsRepository.filterSettings.collect { settings ->
                _filterSettings.value = settings
            }
        }
    }
    
    private fun applyFilters(movies: List<Movie>, filters: FilterSettings): List<Movie> {
        return movies.filter { movie ->
            val titleMatch = filters.titleQuery.isEmpty() ||
                movie.title.contains(filters.titleQuery, ignoreCase = true)
            
            val genreMatch = filters.genres.isEmpty() ||
                filters.genres.any { selectedGenre ->
                    movie.genres.any { movieGenre ->
                        movieGenre.equals(selectedGenre, ignoreCase = true)
                    }
                }
            
            val ratingMatch = movie.rating >= filters.minRating
            val yearMatch = movie.year >= filters.minYear && movie.year <= filters.maxYear
            
            titleMatch && genreMatch && ratingMatch && yearMatch
        }
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
                        _allMovies.value = movies
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
                        _allMovies.value = movies
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
                        _allMovies.value = _allMovies.value + newMovies
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