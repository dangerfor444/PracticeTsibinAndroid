package com.example.practicetsibin.navigation

object Routes {
    const val LIST = "list"
    const val DETAILS = "details/{movieId}"
    const val PROFILE = "profile"
    const val FILTER_SETTINGS = "filter_settings"
    const val FAVORITES = "favorites"
    
    fun details(movieId: String) = "details/$movieId"
}


