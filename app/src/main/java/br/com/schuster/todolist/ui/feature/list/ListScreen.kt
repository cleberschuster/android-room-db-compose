package br.com.schuster.todolist.ui.feature.list

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import br.com.schuster.todolist.data.TaskDatabaseProvider
import br.com.schuster.todolist.data.TaskRepositoryImpl
import br.com.schuster.todolist.domain.Task
import br.com.schuster.todolist.domain.todo1
import br.com.schuster.todolist.domain.todo2
import br.com.schuster.todolist.domain.todo3
import br.com.schuster.todolist.navigation.AddOrEditScreenRoute
import br.com.schuster.todolist.ui.UiEvent
import br.com.schuster.todolist.ui.components.TaskItem
import br.com.schuster.todolist.ui.theme.TaskListTheme

@Composable
fun ListScreen(
    navigateToAddOrEditScreen: (Long?) -> Unit
) {

    val context = LocalContext.current.applicationContext
    val database = TaskDatabaseProvider.provide(context)
    val repository = TaskRepositoryImpl(
        dao = database.taskDao)

    val viewModel = viewModel<ListViewModel> {
        ListViewModel(
            repository = repository
        )
    }

    val tasks by viewModel.tasks.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.uiEvent.collect { uiEvent ->
            when (uiEvent) {
                is UiEvent.ShowSnackbar -> {}

                UiEvent.NavigateBack -> {}

                is UiEvent.NavigateToRoute<*> -> {
                    when (uiEvent.route) {
                        is AddOrEditScreenRoute -> {
                            navigateToAddOrEditScreen(uiEvent.route.id)
                        }
                    }
                }
            }
        }
    }

    ListContent(
        tasks = tasks,
        onEvent = viewModel::onEvent
    )
}

@Composable
fun ListContent(
    tasks: List<Task>,
    onEvent: (ListEvent) -> Unit
) {
    Scaffold (
        floatingActionButton = {
            FloatingActionButton(onClick = { onEvent(ListEvent.AddOrEditTask(null)) }) {
                Icon(Icons.Default.Add, contentDescription = "Add")
            }
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier.consumeWindowInsets(paddingValues),
            contentPadding = PaddingValues(16.dp)
        ) {
            itemsIndexed(tasks) { index, item ->
                TaskItem(
                    task = item,
                    onDoneChange = {
                        onEvent(ListEvent.OnDoneChange(item.id, it))
                    },
                    onItemClick = {
                        onEvent(ListEvent.AddOrEditTask(item.id))
                    },
                    onDeleteClick = {
                        onEvent(ListEvent.DeleteTask(item.id))
                    }
                )

                if (index < tasks.lastIndex) {
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
    }
}

@Preview
@Composable
private fun ListContentPreview() {
    TaskListTheme {
        ListContent(
            tasks = listOf(
                todo1,
                todo2,
                todo3
            ),
            onEvent = {}
        )
    }
}