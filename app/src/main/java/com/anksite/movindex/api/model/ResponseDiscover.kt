package com.anksite.movindex.api.model

import com.google.gson.annotations.SerializedName

data class ResponseDiscover(
    @SerializedName("page") val page: Int,
    @SerializedName("results") val results: List<Movie>,
    @SerializedName("total_pages") val total_pages: Int
)

data class Movie(
    @SerializedName("id") val id: String,
    @SerializedName("poster_path") val poster_path: String,
    @SerializedName("title") val title: String
)



