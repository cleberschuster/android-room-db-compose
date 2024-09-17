package br.com.schuster.todolist.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [TaskEntity::class],
    version = 1,
//    exportSchema = false
)
abstract class TaskDatabase : RoomDatabase() {

    abstract val taskDao: TaskDao

}

object TaskDatabaseProvider {

    @Volatile
    private var INSTANCE: TaskDatabase? = null

    fun provide(context: Context): TaskDatabase {
        return INSTANCE ?: synchronized(this) {
            val instance = Room.databaseBuilder(
                context.applicationContext,
                TaskDatabase::class.java,
                "task-app"
            ).build()
            INSTANCE = instance
            instance
        }
    }
}