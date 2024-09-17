package br.com.schuster.todolist.ui.feature.addoredit

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import br.com.schuster.todolist.data.TaskDatabaseProvider
import br.com.schuster.todolist.data.TaskRepositoryImpl
import br.com.schuster.todolist.ui.UiEvent
import br.com.schuster.todolist.ui.theme.TaskListTheme

@Composable
fun AddOrEditScreen(
    navigateBack: () -> Unit,
    id: Long?
) {

    val context = LocalContext.current.applicationContext
    val database = TaskDatabaseProvider.provide(context)
    val repository = TaskRepositoryImpl(
        dao = database.taskDao)

    val viewModel = viewModel<AddOrEditViewModel> {
        AddOrEditViewModel(
            repository = repository,
            id = id
        )
    }

    val title = viewModel.title
    val description = viewModel.description
    val snackbarHostState = remember {
        SnackbarHostState()
    }
    
    LaunchedEffect(Unit) {
        viewModel.uiEvent.collect { uiEvent ->
            when (uiEvent) {
                is UiEvent.ShowSnackbar -> {
                    snackbarHostState.showSnackbar(
                        message = uiEvent.message
                    )
                }

                UiEvent.NavigateBack -> {
                    navigateBack()
                }

                is UiEvent.NavigateToRoute<*> -> {

                }
            }
        }
    }

    AddOrEditContent(
        title = title,
        description = description,
        snackbarHostState = snackbarHostState,
        onEvent = viewModel::onEvent
    )
}

@Composable
fun AddOrEditContent(
    title: String,
    description: String?,
    snackbarHostState: SnackbarHostState,
    onEvent: (AddOrEditEvent) -> Unit
) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    onEvent(AddOrEditEvent.OnSaveClick)
                }
            ) {
                Icon(Icons.Default.Check, contentDescription = "Save")
            }
        },
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState )
        }
    ) { paddingValues ->
        Column (
            modifier = Modifier
                .consumeWindowInsets(paddingValues)
                .padding(16.dp)
        ) {
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = title,
                onValueChange = {
                    onEvent(AddOrEditEvent.OnTitleChange(it))
                },
                placeholder = {
                    Text(text = "Title")
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = description ?: "",
                onValueChange = {
                    onEvent(AddOrEditEvent.OnDescriptionChange(it))
                },
                placeholder = {
                    Text(text = "Description (optional)")
                }
            )
        }
    }
}

@Preview
@Composable
private fun AddOrEditContentPreview() {
    TaskListTheme {
        AddOrEditContent(
            title = "Title",
            description = "Description",
            snackbarHostState =  SnackbarHostState(),
            onEvent = {}
        )
    }
}