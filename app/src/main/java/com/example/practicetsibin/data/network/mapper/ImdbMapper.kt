package com.example.practicetsibin.data.network.mapper

import com.example.practicetsibin.data.Movie
import com.example.practicetsibin.data.network.dto.ImdbMovieDto

object ImdbMapper {

    fun toDomain(dto: ImdbMovieDto): Movie {
        return Movie(
            id = dto.id,
            title = dto.primaryTitle ?: dto.originalTitle ?: "Без названия",
            year = dto.startYear ?: 0,
            posterUrl = dto.primaryImage?.url ?: "",
            rating = dto.rating?.aggregateRating ?: 0.0,
            genres = dto.genres ?: emptyList(),
            description = dto.plot ?: "Описание недоступно"
        )
    }

    fun toDomainList(dtoList: List<ImdbMovieDto>): List<Movie> {
        return dtoList.map { toDomain(it) }
    }
}