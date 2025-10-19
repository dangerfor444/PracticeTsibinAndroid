package com.example.practicetsibin.data.network.api

import com.example.practicetsibin.data.network.dto.ImdbTitlesResponse
import com.example.practicetsibin.data.network.dto.ImdbMovieDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ImdbApi {
    
    @GET("titles")
    suspend fun getTitles(
        @Query("page") page: Int = 1,
        @Query("limit") limit: Int = 50
    ): ImdbTitlesResponse
    
    @GET("titles/{titleId}")
    suspend fun getTitleById(
        @Path("titleId") titleId: String
    ): ImdbMovieDto
}
