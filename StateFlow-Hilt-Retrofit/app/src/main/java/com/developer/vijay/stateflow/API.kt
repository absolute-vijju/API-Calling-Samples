package com.developer.vijay.stateflow

import retrofit2.http.GET

interface API {
    @GET("posts")
    suspend fun getPosts(): PostResponse
}