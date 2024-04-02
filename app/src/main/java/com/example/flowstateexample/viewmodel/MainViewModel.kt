package com.example.flowstateexample.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.flowstateexample.api.client.RetrofitClient.createService
import com.example.flowstateexample.api.helper.ResponseHelper
import com.example.flowstateexample.api.helper.ResponseStatus
import com.example.flowstateexample.api.models.TodoModel
import com.example.flowstateexample.api.models.TodoModelResponse
import com.example.flowstateexample.api.services.TodoService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    private val api = createService(TodoService::class.java)

    private val _response = MutableStateFlow<ResponseHelper<TodoModelResponse>>(ResponseHelper())
    val response: StateFlow<ResponseHelper<TodoModelResponse>> = _response.asStateFlow()

    init {
        getTodos()
    }

    private fun getTodos() {
        viewModelScope.launch {
            try {
                val response = api.getTodos()

                if (response.isSuccessful) {
                    _response.value = ResponseHelper(
                        status = ResponseStatus.SUCCESS,
                        data = response.body()
                    )
                    return@launch
                }


                _response.value = ResponseHelper(
                    status = ResponseStatus.ERROR,
                    data = null,
                    message = response.errorBody().toString()
                )


            } catch (e: Exception) {
                _response.value = ResponseHelper(
                    status = ResponseStatus.ERROR,
                    data = null,
                    message = e.message
                )
            }
        }
    }

    fun createTodo(todo: TodoModel) {
        viewModelScope.launch {
            try {
                _response.value = ResponseHelper(
                    status = ResponseStatus.LOADING,
//                    data = _response.value.data
                )

                val response = api.createTodo(todo)

                if (response.isSuccessful) {
                    _response.value = ResponseHelper(
                        status = ResponseStatus.SUCCESS,
                        data = TodoModelResponse(
                            total = _response.value.data!!.total,
                            limit = _response.value.data!!.limit,
                            skip = _response.value.data!!.skip,
                            todos = (_response.value.data!!.todos + response.body()!!) as MutableList<TodoModel>
                        )
                    )

                    return@launch
                }
                _response.value = ResponseHelper(
                    status = ResponseStatus.ERROR,
                    data = null,
                    message = response.errorBody().toString()
                )


            } catch (e: Exception) {
                _response.value = ResponseHelper(
                    status = ResponseStatus.ERROR,
                    data = null,
                    message = e.message
                )
            }
        }
    }
}