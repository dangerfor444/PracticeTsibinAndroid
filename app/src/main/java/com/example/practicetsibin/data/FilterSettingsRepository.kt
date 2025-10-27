package com.example.practicetsibin.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringSetPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.core.doublePreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "filter_settings")

class FilterSettingsRepository(private val context: Context) {
    
    private val gson = Gson()
    private val titleQueryKey = stringPreferencesKey("title_query")
    private val genresKey = stringSetPreferencesKey("genres")
    private val minRatingKey = doublePreferencesKey("min_rating")
    private val minYearKey = intPreferencesKey("min_year")
    private val maxYearKey = intPreferencesKey("max_year")
    
    val filterSettings: Flow<FilterSettings> = context.dataStore.data.map { preferences ->
        val genresList = preferences[genresKey]?.toList() ?: emptyList()
        FilterSettings(
            titleQuery = preferences[titleQueryKey] ?: "",
            genres = genresList,
            minRating = preferences[minRatingKey] ?: 0.0,
            minYear = preferences[minYearKey] ?: 0,
            maxYear = preferences[maxYearKey] ?: 2024
        )
    }
    
    suspend fun updateFilterSettings(settings: FilterSettings) {
        context.dataStore.edit { preferences ->
            preferences[titleQueryKey] = settings.titleQuery
            preferences[genresKey] = settings.genres.toSet()
            preferences[minRatingKey] = settings.minRating
            preferences[minYearKey] = settings.minYear
            preferences[maxYearKey] = settings.maxYear
        }
    }
    
    suspend fun clearSettings() {
        context.dataStore.edit { preferences ->
            preferences.clear()
        }
    }
}
