package ru.university.taskcalendar.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

/**
 * Точка входа в базу данных: перечисляет таблицы (entities) и отдаёт DAO.
 * version нужно увеличивать при изменении структуры таблиц (и писать миграцию).
 */
@Database(entities = [Task::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun taskDao(): TaskDao

    companion object {
        // @Volatile — изменения INSTANCE сразу видны всем потокам
        @Volatile
        private var INSTANCE: AppDatabase? = null

        /** Singleton: база создаётся один раз (создание экземпляра — дорогая операция). */
        fun getInstance(context: Context): AppDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,   // applicationContext — чтобы не удерживать Activity в памяти
                    AppDatabase::class.java,
                    "task_calendar.db"
                ).build().also { INSTANCE = it }
            }
    }
}
