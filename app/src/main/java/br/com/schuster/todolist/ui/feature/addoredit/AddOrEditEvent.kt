package br.com.schuster.todolist.ui.feature.addoredit

sealed interface AddOrEditEvent {
    data class OnTitleChange(val title: String) : AddOrEditEvent
    data class OnDescriptionChange(val description: String) : AddOrEditEvent
    data object OnSaveClick : AddOrEditEvent
}