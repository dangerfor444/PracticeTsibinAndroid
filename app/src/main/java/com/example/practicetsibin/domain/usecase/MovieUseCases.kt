package com.example.practicetsibin.domain.usecase

import com.example.practicetsibin.data.Movie
import com.example.practicetsibin.data.network.ImdbMovieRepository

class SearchMoviesUseCase(
    private val imdbRepository: ImdbMovieRepository
) {
    suspend operator fun invoke(query: String, page: Int = 1): Result<List<Movie>> {
        return imdbRepository.searchMovies(query, page)
    }
}

class GetMovieByIdUseCase(
    private val imdbRepository: ImdbMovieRepository
) {
    suspend operator fun invoke(id: String): Result<Movie?> {
        return imdbRepository.getMovieById(id)
    }
}

class GetPopularMoviesUseCase(
    private val imdbRepository: ImdbMovieRepository
) {
    suspend operator fun invoke(page: Int = 1): Result<List<Movie>> {
        return imdbRepository.getPopularMovies(page)
    }
}
