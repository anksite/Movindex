package com.anksite.movindex.api.model

import com.google.gson.annotations.SerializedName

data class ResponseVideo(
    @SerializedName("results") val results: List<Video>,
)

data class Video(
    @SerializedName("name") val name: String,
    @SerializedName("key") val key: String,
    @SerializedName("site") val site: String,
    @SerializedName("type") val type: String,
    @SerializedName("official") val official: Boolean,
    @SerializedName("published_at") val published_at: String,
)
