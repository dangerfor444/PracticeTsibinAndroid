package com.example.practicetsibin.navigation

object Routes {
    const val LIST = "list"
    const val DETAILS = "details/{movieId}"
    const val PROFILE = "profile"
    
    fun details(movieId: String) = "details/$movieId"
}


