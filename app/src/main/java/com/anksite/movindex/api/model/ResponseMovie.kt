package com.anksite.movindex.api.model

import com.google.gson.annotations.SerializedName

data class ResponseMovie(
    @SerializedName("backdrop_path") val backdrop_path: String,
    @SerializedName("genres") val genres: List<Genre>,
    @SerializedName("id") val id: String,
    @SerializedName("overview") val overview: String,
    @SerializedName("poster_path") val poster_path: String,
    @SerializedName("release_date") val release_date: String,
    @SerializedName("runtime") val runtime: Int,
    @SerializedName("title") val title: String,
    @SerializedName("vote_average") val vote_average: Float,
    @SerializedName("vote_count") val vote_count: Int,
)

data class Genre(
    @SerializedName("name") val name: String,
)
