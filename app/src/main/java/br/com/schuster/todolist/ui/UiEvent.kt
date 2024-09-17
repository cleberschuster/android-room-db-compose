package br.com.schuster.todolist.ui

sealed interface UiEvent {
    data class ShowSnackbar(val message: String) : UiEvent
    data object NavigateBack : UiEvent
    data class NavigateToRoute<T : Any>(val route: T) : UiEvent
}