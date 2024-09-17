package br.com.schuster.todolist.domain

import kotlinx.coroutines.flow.Flow

interface TaskRepository {

    suspend fun insert(title: String, description: String?, id: Long? = null)

    suspend fun updateCheckedDone(id: Long, isDone: Boolean)

    suspend fun delete(id: Long)

    fun getAll(): Flow<List<Task>>

    suspend fun getById(id: Long): Task?
}