package com.anksite.movindex.api.model

import com.google.gson.annotations.SerializedName

data class ResponseReview(
    @SerializedName("page") val page: Int,
    @SerializedName("results") val results: List<Review>,
    @SerializedName("total_pages") val total_pages: Int,
)

data class Review(
    @SerializedName("author") val author: String,
    @SerializedName("author_details") val author_details: AuthorDetails,
    @SerializedName("content") val content: String,
    @SerializedName("updated_at") val updated_at: String,
)

data class AuthorDetails(
    @SerializedName("avatar_path") val avatar_path: String,
    @SerializedName("rating") val rating: String,
)
