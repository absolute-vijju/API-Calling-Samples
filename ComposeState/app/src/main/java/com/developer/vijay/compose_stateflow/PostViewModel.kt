package com.developer.vijay.compose_stateflow

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostViewModel @Inject constructor(private val postRepo: PostRepo) : ViewModel() {
    private val _apiResponse: MutableState<Resource<Any>> = mutableStateOf(Resource.Loading(null))
    val apiResponse = _apiResponse

    init {
        getPosts()
    }

    private fun getPosts() = viewModelScope.launch {
        postRepo.callAPI()
            .onStart {
                //
            }
            .catch {
                _apiResponse.value = Resource.Error(it.message!!, null)
            }
            .collect {
                _apiResponse.value = Resource.Success(it)
            }
    }
}