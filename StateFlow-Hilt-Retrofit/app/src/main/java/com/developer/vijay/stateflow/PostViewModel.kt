package com.developer.vijay.stateflow

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostViewModel @Inject constructor(private val postRepo: PostRepo) : ViewModel() {
    private val _apiResponse: MutableStateFlow<Resource<Any>> =
        MutableStateFlow(Resource.Loading(null))
    val apiResponse = _apiResponse

    fun getPosts() = viewModelScope.launch {
        postRepo.callAPI()
            .catch {
                _apiResponse.value = Resource.Error(it.message!!, null)
            }
            .collect {
                _apiResponse.value = Resource.Success(it)
            }
    }
}