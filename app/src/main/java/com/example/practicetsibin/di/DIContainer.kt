package com.example.practicetsibin.di

import com.example.practicetsibin.data.network.ImdbMovieRepository
import com.example.practicetsibin.domain.usecase.GetMovieByIdUseCase
import com.example.practicetsibin.domain.usecase.GetPopularMoviesUseCase
import com.example.practicetsibin.domain.usecase.SearchMoviesUseCase

object DIContainer {
    
    private val imdbRepository by lazy { ImdbMovieRepository() }
    
    val getPopularMoviesUseCase by lazy { GetPopularMoviesUseCase(imdbRepository) }
    val searchMoviesUseCase by lazy { SearchMoviesUseCase(imdbRepository) }
    val getMovieByIdUseCase by lazy { GetMovieByIdUseCase(imdbRepository) }
}
