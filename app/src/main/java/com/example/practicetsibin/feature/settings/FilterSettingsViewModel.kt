package com.example.practicetsibin.feature.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.practicetsibin.data.BadgeCache
import com.example.practicetsibin.data.FilterSettings
import com.example.practicetsibin.data.FilterSettingsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class FilterSettingsViewModel(
    private val filterSettingsRepository: FilterSettingsRepository,
    private val badgeCache: BadgeCache
) : ViewModel() {
    
    private val _filterSettings = MutableStateFlow(FilterSettings())
    val filterSettings: StateFlow<FilterSettings> = _filterSettings.asStateFlow()
    
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()
    
    init {
        loadSettings()
    }
    
    private fun loadSettings() {
        viewModelScope.launch {
            filterSettingsRepository.filterSettings.collect { settings ->
                _filterSettings.value = settings
            }
        }
    }
    
    fun updateTitleQuery(query: String) {
        _filterSettings.value = _filterSettings.value.copy(titleQuery = query)
    }
    
    fun toggleGenre(genre: String) {
        val currentGenres = _filterSettings.value.genres
        val newGenres = if (currentGenres.contains(genre)) {
            currentGenres - genre
        } else {
            currentGenres + genre
        }
        _filterSettings.value = _filterSettings.value.copy(genres = newGenres)
    }
    
    fun updateMinRating(rating: Double) {
        _filterSettings.value = _filterSettings.value.copy(minRating = rating)
    }
    
    fun updateMinYear(year: Int) {
        _filterSettings.value = _filterSettings.value.copy(minYear = year)
    }
    
    fun updateMaxYear(year: Int) {
        _filterSettings.value = _filterSettings.value.copy(maxYear = year)
    }
    
    fun applyFilters() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val settings = _filterSettings.value
                filterSettingsRepository.updateFilterSettings(settings)
                badgeCache.setHasActiveFilters(hasActiveFilters())
            } finally {
                _isLoading.value = false
            }
        }
    }
    
    fun clearFilters() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                filterSettingsRepository.clearSettings()
                _filterSettings.value = FilterSettings()
                badgeCache.setHasActiveFilters(false)
            } finally {
                _isLoading.value = false
            }
        }
    }
    
    private fun hasActiveFilters(): Boolean {
        val settings = _filterSettings.value
        return settings.titleQuery.isNotEmpty() ||
               settings.genres.isNotEmpty() ||
               settings.minRating > 0 ||
               settings.minYear > 0 ||
               settings.maxYear < 2024
    }
}
