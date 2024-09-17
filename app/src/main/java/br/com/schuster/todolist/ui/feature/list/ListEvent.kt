package br.com.schuster.todolist.ui.feature.list

sealed interface ListEvent {
    data class DeleteTask(val id: Long) : ListEvent
    data class OnDoneChange(val id: Long, val isDone: Boolean) : ListEvent
    data class AddOrEditTask(val id: Long?) : ListEvent
}