package com.anksite.movindex.api

import com.anksite.movindex.BuildConfig
import com.anksite.movindex.api.model.ResponseDiscover
import com.anksite.movindex.api.model.ResponseMovie
import com.anksite.movindex.api.model.ResponseReview
import com.anksite.movindex.api.model.ResponseVideo
import retrofit2.http.*

interface InterfaceApi {

    @GET("/3/discover/movie?api_key=${BuildConfig.KEY}&page=1")
    suspend fun discover(): Result<ResponseDiscover>

    @GET("/3/movie/{id}?api_key=${BuildConfig.KEY}")
    suspend fun movie(
        @Path("id") id: String
    ): Result<ResponseMovie>

    @GET("/3/movie/{id}/reviews?api_key=${BuildConfig.KEY}&page=1")
    suspend fun reviews(
        @Path("id") id: String
    ): Result<ResponseReview>

    @GET("/3/movie/{id}/videos?api_key=${BuildConfig.KEY}")
    suspend fun video(
        @Path("id") id: String
    ): Result<ResponseVideo>
}
