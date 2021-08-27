package com.developer.vijay.compose_stateflow

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class PostRepo @Inject constructor(private val api: API) {
    fun callAPI() = flow {
        emit(api.getPosts())
    }.flowOn(Dispatchers.IO)
}