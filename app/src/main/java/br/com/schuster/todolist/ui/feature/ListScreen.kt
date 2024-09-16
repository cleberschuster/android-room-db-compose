package br.com.schuster.todolist.ui.feature

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
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.schuster.todolist.domain.Task
import br.com.schuster.todolist.domain.todo1
import br.com.schuster.todolist.domain.todo2
import br.com.schuster.todolist.domain.todo3
import br.com.schuster.todolist.ui.components.TaskItem
import br.com.schuster.todolist.ui.theme.TaskListTheme

@Composable
fun ListScreen() {
    ListContent(tasks = emptyList())
}

@Composable
fun ListContent(
    tasks: List<Task>
) {
    Scaffold (
        floatingActionButton = {
            FloatingActionButton(onClick = { }) {
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
                    onDoneChange = {},
                    onItemClick = {},
                    onDeleteClick = {}
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
            )
        )
    }
}