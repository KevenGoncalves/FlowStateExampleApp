package com.example.flowstateexample.api.helper

enum class ResponseStatus {
    SUCCESS,
    ERROR,
    LOADING
}

data class ResponseHelper<T>(
    val status: ResponseStatus = ResponseStatus.LOADING,
    val data: T? = null,
    val message: String? = null
)