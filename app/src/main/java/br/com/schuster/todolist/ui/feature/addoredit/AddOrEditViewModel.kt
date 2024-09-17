package br.com.schuster.todolist.ui.feature.addoredit

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.schuster.todolist.domain.TaskRepository
import br.com.schuster.todolist.ui.UiEvent
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class AddOrEditViewModel(
    private val repository: TaskRepository,
    private val id: Long? = null
) : ViewModel() {

    var title by mutableStateOf("")
    private set

    var description by mutableStateOf<String?>(null)
    private set

    private var _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        id?.let {
            viewModelScope.launch {
                val task = repository.getById(it)
                title = task?.title ?: ""
                description = task?.description
            }
        }
    }

    fun onEvent(event: AddOrEditEvent) {
        when (event) {
            is AddOrEditEvent.OnTitleChange -> {
                title = event.title
            }
            is AddOrEditEvent.OnDescriptionChange -> {
                description = event.description
            }
            AddOrEditEvent.OnSaveClick -> {
                saveTask()
            }
        }
    }

    private fun saveTask() {
        viewModelScope.launch {
            if (title.isBlank()) {
                _uiEvent.send(UiEvent.ShowSnackbar(message = "O titulo não pode estar em branco"))
                return@launch
            }
            repository.insert(title, description, id)
            _uiEvent.send(UiEvent.NavigateBack)

        }
    }
}