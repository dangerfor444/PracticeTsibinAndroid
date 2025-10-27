package com.example.practicetsibin.data

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class BadgeCache {
    
    private val _hasActiveFilters = MutableStateFlow(false)
    val hasActiveFilters: StateFlow<Boolean> = _hasActiveFilters.asStateFlow()
    
    fun setHasActiveFilters(hasFilters: Boolean) {
        _hasActiveFilters.value = hasFilters
    }
}
