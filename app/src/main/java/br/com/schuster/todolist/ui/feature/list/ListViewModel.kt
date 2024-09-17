package br.com.schuster.todolist.ui.feature.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.schuster.todolist.domain.TaskRepository
import br.com.schuster.todolist.navigation.AddOrEditScreenRoute
import br.com.schuster.todolist.ui.UiEvent
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ListViewModel(
    private val repository: TaskRepository
) : ViewModel() {

    val tasks = repository.getAll()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    private var _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onEvent(event: ListEvent) {
        when (event) {
            is ListEvent.DeleteTask -> {
                deleteTask(event.id)
            }
            is ListEvent.OnDoneChange -> {
                onDoneChange(event.id, event.isDone)
            }
            is ListEvent.AddOrEditTask -> {
               viewModelScope.launch {
                   _uiEvent.send(UiEvent.NavigateToRoute(AddOrEditScreenRoute(event.id)))
               }
            }
        }
    }

    private fun deleteTask(id: Long) {
        viewModelScope.launch {
            repository.delete(id)
        }
    }

    private fun onDoneChange(id: Long, isDone: Boolean) {
        viewModelScope.launch {
            repository.updateCheckedDone(id, isDone)
        }

    }
}