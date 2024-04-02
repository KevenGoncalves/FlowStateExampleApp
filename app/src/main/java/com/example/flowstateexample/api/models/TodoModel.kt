package com.example.flowstateexample.api.models

import com.google.gson.annotations.SerializedName

class TodoModel(
    @SerializedName("id") var id: Int? = null,
    @SerializedName("todo") var todo: String,
    @SerializedName("completed") var completed: Boolean,
    @SerializedName("userId") var userId: Int
)

class TodoModelResponse(
    @SerializedName("todos") var todos: MutableList<TodoModel>,
    @SerializedName("total") var total: Int? = null,
    @SerializedName("limit") var limit: Int? = null,
    @SerializedName("skip") var skip: Int? = null,
)