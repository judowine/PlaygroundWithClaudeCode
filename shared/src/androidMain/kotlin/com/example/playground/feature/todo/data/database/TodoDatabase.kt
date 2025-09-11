package com.example.playground.feature.todo.data.database

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import android.content.Context
import com.example.playground.feature.todo.data.database.dao.TodoDao
import com.example.playground.feature.todo.data.database.entity.TodoEntity

/**
 * Room database for the TODO application.
 * Manages the SQLite database and provides access to DAO interfaces.
 */
@Database(
    entities = [TodoEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class TodoDatabase : RoomDatabase() {
    
    /**
     * Provides access to TODO item data access object
     * @return TodoDao instance for database operations
     */
    abstract fun todoDao(): TodoDao
    
    companion object {
        /**
         * Database name as specified in the design document
         */
        const val DATABASE_NAME = "todo_database"
        
        /**
         * Singleton instance holder
         */
        @Volatile
        private var INSTANCE: TodoDatabase? = null
        
        /**
         * Get the singleton database instance
         * @param context Android application context
         * @return TodoDatabase instance
         */
        fun getInstance(context: Context): TodoDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    TodoDatabase::class.java,
                    DATABASE_NAME
                )
                .fallbackToDestructiveMigration(true) // TODO: Implement proper migration strategy
                .build()
                INSTANCE = instance
                instance
            }
        }
        
        /**
         * Create an in-memory database instance for testing
         * @param context Android application context
         * @return TodoDatabase instance backed by in-memory database
         */
        fun getInMemoryInstance(context: Context): TodoDatabase {
            return Room.inMemoryDatabaseBuilder(
                context.applicationContext,
                TodoDatabase::class.java
            ).build()
        }
    }
}

/**
 * Type converters for Room database.
 * Currently empty as all TODO entity fields use primitive types,
 * but can be extended for future complex type conversions.
 */
class Converters {
    // TODO: Add type converters if needed for complex data types
    // For example, Date/DateTime conversions, enums, etc.
}