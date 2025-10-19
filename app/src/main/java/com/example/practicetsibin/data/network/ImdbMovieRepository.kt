package com.example.practicetsibin.data.network

import com.example.practicetsibin.data.Movie
import com.example.practicetsibin.data.network.api.ImdbApi
import com.example.practicetsibin.data.network.mapper.ImdbMapper
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import com.google.gson.GsonBuilder

class ImdbMovieRepository {
    
    private val imdbApi: ImdbApi
    
    init {
        val gson = GsonBuilder().setLenient().create()
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
        
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.imdbapi.dev/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            
        imdbApi = retrofit.create(ImdbApi::class.java)
    }
    
    suspend fun searchMovies(query: String, page: Int = 1): Result<List<Movie>> {
        return try {
            val response = imdbApi.getTitles(page, 50)
            
            val filteredMovies = response.titles.filter { movie ->
                movie.primaryTitle?.contains(query, ignoreCase = true) == true ||
                movie.originalTitle?.contains(query, ignoreCase = true) == true
            }
            
            val movies = ImdbMapper.toDomainList(filteredMovies)
            
            Result.success(movies)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    suspend fun getMovieById(id: String): Result<Movie?> {
        return try {
            val movieDto = imdbApi.getTitleById(id)
            
            val movie = ImdbMapper.toDomain(movieDto)
            Result.success(movie)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    suspend fun getPopularMovies(page: Int = 1): Result<List<Movie>> {
        return try {
            val response = imdbApi.getTitles(page, 50)
            
            val movies = ImdbMapper.toDomainList(response.titles)

            Result.success(movies)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
