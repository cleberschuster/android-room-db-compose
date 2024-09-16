package br.com.schuster.todolist.domain

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import br.com.schuster.todolist.data.TaskEntity
import kotlinx.coroutines.flow.Flow

interface TaskRepository {

    suspend fun insert(title: String, description: String?)

    suspend fun updateCheckedDone(id: Long, isDone: Boolean)

    suspend fun delete(id: Long)

    fun getAll(): Flow<List<Task>>

    suspend fun getById(id: Long): Task?
}