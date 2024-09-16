package br.com.schuster.todolist.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.schuster.todolist.domain.Task
import br.com.schuster.todolist.domain.todo1
import br.com.schuster.todolist.domain.todo2
import br.com.schuster.todolist.ui.theme.TaskListTheme

@Composable
fun TaskItem(
    modifier: Modifier = Modifier,
    task: Task,
    onDoneChange: (Boolean) -> Unit = {},
    onItemClick: () -> Unit = {},
    onDeleteClick: () -> Unit = {},
) {
    Surface(
        modifier = modifier,
        onClick = onItemClick,
        shape = MaterialTheme.shapes.medium,
        shadowElevation = 2.dp,
        border = BorderStroke(
            width = 1.dp,
            color = MaterialTheme.colorScheme.outline
        )
    ) {
        Row (
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ){
            Checkbox(
                checked = task.isDone,
                onCheckedChange = onDoneChange
            )

            Spacer(modifier = Modifier.width(8.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = task.title,
                    style = MaterialTheme.typography.titleMedium
                )

                task.description?.let {
                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = task.description,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }

            Spacer(modifier = Modifier.width(8.dp))

            IconButton(
                onClick = onDeleteClick
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Delete"
                )
            }
        }

    }

}

@Preview
@Composable
private fun TodoItemPreview() {
    TaskListTheme {
        TaskItem(
            task = todo1,
            onDoneChange = {},
            onItemClick = {},
            onDeleteClick = {}
        )
    }
}

@Preview
@Composable
private fun TodoItemDonePreview() {
    TaskListTheme {
        TaskItem(
            task = todo2,
            onDoneChange = {},
            onItemClick = {},
            onDeleteClick = {}
        )
    }
}