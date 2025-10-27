package com.example.practicetsibin.di

import android.content.Context
import com.example.practicetsibin.data.BadgeCache
import com.example.practicetsibin.data.FavoriteMovieRepository
import com.example.practicetsibin.data.FilterSettings
import com.example.practicetsibin.data.FilterSettingsRepository
import com.example.practicetsibin.data.database.MovieDatabase
import com.example.practicetsibin.data.network.ImdbMovieRepository
import com.example.practicetsibin.domain.usecase.AddToFavoritesUseCase
import com.example.practicetsibin.domain.usecase.ClearAllFavoritesUseCase
import com.example.practicetsibin.domain.usecase.GetAllFavoritesUseCase
import com.example.practicetsibin.domain.usecase.GetFavoriteByIdUseCase
import com.example.practicetsibin.domain.usecase.GetMovieByIdUseCase
import com.example.practicetsibin.domain.usecase.GetPopularMoviesUseCase
import com.example.practicetsibin.domain.usecase.IsFavoriteUseCase
import com.example.practicetsibin.domain.usecase.RemoveFromFavoritesUseCase
import com.example.practicetsibin.domain.usecase.SearchMoviesUseCase

object DIContainer {
    
    private var context: Context? = null
    
    fun init(context: Context) {
        this.context = context
    }
    
    private val imdbRepository by lazy { ImdbMovieRepository() }
    private val filterSettingsRepository by lazy { FilterSettingsRepository(context!!) }
    private val movieDatabase by lazy { MovieDatabase.getDatabase(context!!) }
    private val favoriteMovieRepository by lazy { FavoriteMovieRepository(movieDatabase.favoriteMovieDao()) }
    
    val badgeCache = BadgeCache()
    
    val getPopularMoviesUseCase by lazy { GetPopularMoviesUseCase(imdbRepository) }
    val searchMoviesUseCase by lazy { SearchMoviesUseCase(imdbRepository) }
    val getMovieByIdUseCase by lazy { GetMovieByIdUseCase(imdbRepository) }
    val filterSettingsRepositoryInstance by lazy { filterSettingsRepository }
    
    val getAllFavoritesUseCase by lazy { GetAllFavoritesUseCase(favoriteMovieRepository) }
    val getFavoriteByIdUseCase by lazy { GetFavoriteByIdUseCase(favoriteMovieRepository) }
    val isFavoriteUseCase by lazy { IsFavoriteUseCase(favoriteMovieRepository) }
    val addToFavoritesUseCase by lazy { AddToFavoritesUseCase(favoriteMovieRepository) }
    val removeFromFavoritesUseCase by lazy { RemoveFromFavoritesUseCase(favoriteMovieRepository) }
    val clearAllFavoritesUseCase by lazy { ClearAllFavoritesUseCase(favoriteMovieRepository) }
}
