package br.com.schuster.todolist.data

import br.com.schuster.todolist.domain.Task
import br.com.schuster.todolist.domain.TaskRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TaskRepositoryImpl(
    private val dao: TaskDao
) : TaskRepository {

    override suspend fun insert(title: String, description: String?, id: Long?) {
        val entity = id?.let {
            dao.getById(it)?.copy(
                title = title,
                description = description,
            )
        } ?: TaskEntity(
            title = title,
            description = description,
            isDone = false
        )
        dao.insert(entity)
    }

    override suspend fun updateCheckedDone(id: Long, isDone: Boolean) {
        val existingEntity = dao.getById(id) ?: return
        val updatedEntity = existingEntity.copy(isDone = isDone)
        dao.insert(updatedEntity)
    }

    override suspend fun delete(id: Long) {
        val existingEntity = dao.getById(id) ?: return
        dao.delete(existingEntity)
    }

    override fun getAll(): Flow<List<Task>> {
        return dao.getAll().map { entities ->
            entities.map { entity ->
                Task(
                    id = entity.id,
                    title = entity.title,
                    description = entity.description,
                    isDone = entity.isDone
                )
            }
        }
    }

    override suspend fun getById(id: Long): Task? {
        return dao.getById(id)?.let { entity ->
            Task(
                id = entity.id,
                title = entity.title,
                description = entity.description,
                isDone = entity.isDone
            )
        }
    }
}