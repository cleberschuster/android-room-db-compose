package br.com.schuster.todolist.domain

data class Task(
    val id: Long,
    val title: String,
    val description: String?,
    val isDone: Boolean
)

val todo1 = Task(
    id = 1,
    title = "Todo 1",
    description = "Description 1",
    isDone = false
)

val todo2 = Task(
    id = 2,
    title = "Todo 2",
    description = "Description 2",
    isDone = true
)

val todo3 = Task(
    id = 3,
    title = "Todo 3",
    description = "Description 3",
    isDone = false
)
