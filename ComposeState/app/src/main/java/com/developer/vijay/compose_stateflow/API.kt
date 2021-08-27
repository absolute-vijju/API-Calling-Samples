package com.developer.vijay.compose_stateflow

import retrofit2.http.GET

interface API {
    @GET("posts")
    suspend fun getPosts(): PostResponse
}