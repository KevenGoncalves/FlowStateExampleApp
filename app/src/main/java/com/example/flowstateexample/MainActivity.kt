package com.example.flowstateexample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.example.flowstateexample.api.helper.ResponseHelper
import com.example.flowstateexample.api.helper.ResponseStatus
import com.example.flowstateexample.api.models.TodoModel
import com.example.flowstateexample.ui.theme.FlowStateExampleTheme
import com.example.flowstateexample.viewmodel.MainViewModel

class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FlowStateExampleTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    TodoList(viewModel)
                }
            }
        }
    }
}

@Composable
fun TodoList(viewModel: MainViewModel) {
    val response by viewModel.response.collectAsState(ResponseHelper())

    val todo = TodoModel(
        todo = "New Todo",
        completed = false,
        userId = 43
    )

    Column {
        Row {
            TextButton(onClick = {
                viewModel.createTodo(todo)
            }) {
                Text(text = "Create Todo")
            }
        }

        when (response.status) {
            ResponseStatus.SUCCESS -> {
                Row {
                    LazyColumn {
                        response.data?.let {
                            items(it.todos) { model ->
                                Text("${model.id}." + model.todo)
                            }
                        }
                    }
                }
            }

            ResponseStatus.LOADING -> {
                Text(text = "Loading...")
            }

            ResponseStatus.ERROR -> {
                Text(text = response.message.toString())
            }
        }
    }
}