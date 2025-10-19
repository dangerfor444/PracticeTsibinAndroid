package com.example.practicetsibin.data.network.dto

import com.google.gson.annotations.SerializedName

data class ImdbTitlesResponse(
    @SerializedName("titles")
    val titles: List<ImdbMovieDto>,
    @SerializedName("totalCount")
    val totalCount: Int,
    @SerializedName("nextPageToken")
    val nextPageToken: String?
)

data class ImdbMovieDto(
    @SerializedName("id")
    val id: String,
    @SerializedName("primaryTitle")
    val primaryTitle: String?,
    @SerializedName("originalTitle")
    val originalTitle: String?,
    @SerializedName("type")
    val type: String?,
    @SerializedName("startYear")
    val startYear: Int?,
    @SerializedName("primaryImage")
    val primaryImage: ImdbImageDto?,
    @SerializedName("genres")
    val genres: List<String>?,
    @SerializedName("rating")
    val rating: ImdbRatingDto?,
    @SerializedName("plot")
    val plot: String?
)

data class ImdbImageDto(
    @SerializedName("url")
    val url: String?,
    @SerializedName("width")
    val width: Int?,
    @SerializedName("height")
    val height: Int?
)

data class ImdbRatingDto(
    @SerializedName("aggregateRating")
    val aggregateRating: Double?,
    @SerializedName("voteCount")
    val voteCount: Int?
)