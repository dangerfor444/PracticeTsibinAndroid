package com.example.practicetsibin.data

import com.example.practicetsibin.data.database.FavoriteMovieDao
import com.example.practicetsibin.data.database.FavoriteMovieEntity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class FavoriteMovieRepository(
    private val favoriteMovieDao: FavoriteMovieDao
) {
    
    private val gson = Gson()
    
    fun getAllFavorites(): Flow<List<Movie>> {
        return favoriteMovieDao.getAllFavorites().map { entities ->
            entities.map { entityToMovie(it) }
        }
    }
    
    suspend fun getFavoriteById(movieId: String): Movie? {
        val entity = favoriteMovieDao.getFavoriteById(movieId)
        return entity?.let { entityToMovie(it) }
    }
    
    suspend fun isFavorite(movieId: String): Boolean {
        return favoriteMovieDao.isFavorite(movieId)
    }
    
    suspend fun addToFavorites(movie: Movie) {
        val entity = movieToEntity(movie)
        favoriteMovieDao.insertFavorite(entity)
    }
    
    suspend fun removeFromFavorites(movieId: String) {
        favoriteMovieDao.deleteFavoriteById(movieId)
    }
    
    suspend fun clearAllFavorites() {
        favoriteMovieDao.clearAllFavorites()
    }
    
    private fun entityToMovie(entity: FavoriteMovieEntity): Movie {
        val genresType = object : TypeToken<List<String>>() {}.type
        val genres: List<String> = gson.fromJson(entity.genres, genresType)
        
        return Movie(
            id = entity.id,
            title = entity.title,
            year = entity.year,
            posterUrl = entity.posterUrl,
            rating = entity.rating,
            genres = genres,
            description = entity.description
        )
    }
    
    private fun movieToEntity(movie: Movie): FavoriteMovieEntity {
        val genresJson = gson.toJson(movie.genres)
        
        return FavoriteMovieEntity(
            id = movie.id,
            title = movie.title,
            year = movie.year,
            posterUrl = movie.posterUrl,
            rating = movie.rating,
            genres = genresJson,
            description = movie.description
        )
    }
}
