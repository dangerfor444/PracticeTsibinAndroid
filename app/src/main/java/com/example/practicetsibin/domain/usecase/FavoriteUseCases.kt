package com.example.practicetsibin.domain.usecase

import com.example.practicetsibin.data.FavoriteMovieRepository
import com.example.practicetsibin.data.Movie
import kotlinx.coroutines.flow.Flow

class GetAllFavoritesUseCase(
    private val favoriteMovieRepository: FavoriteMovieRepository
) {
    operator fun invoke(): Flow<List<Movie>> = favoriteMovieRepository.getAllFavorites()
}

class GetFavoriteByIdUseCase(
    private val favoriteMovieRepository: FavoriteMovieRepository
) {
    suspend operator fun invoke(movieId: String): Movie? = 
        favoriteMovieRepository.getFavoriteById(movieId)
}

class IsFavoriteUseCase(
    private val favoriteMovieRepository: FavoriteMovieRepository
) {
    suspend operator fun invoke(movieId: String): Boolean = 
        favoriteMovieRepository.isFavorite(movieId)
}

class AddToFavoritesUseCase(
    private val favoriteMovieRepository: FavoriteMovieRepository
) {
    suspend operator fun invoke(movie: Movie) = 
        favoriteMovieRepository.addToFavorites(movie)
}

class RemoveFromFavoritesUseCase(
    private val favoriteMovieRepository: FavoriteMovieRepository
) {
    suspend operator fun invoke(movieId: String) = 
        favoriteMovieRepository.removeFromFavorites(movieId)
}

class ClearAllFavoritesUseCase(
    private val favoriteMovieRepository: FavoriteMovieRepository
) {
    suspend operator fun invoke() = 
        favoriteMovieRepository.clearAllFavorites()
}
