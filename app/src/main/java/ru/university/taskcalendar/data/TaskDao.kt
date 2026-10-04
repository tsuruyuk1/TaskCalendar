package ru.university.taskcalendar.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

/**
 * Data Access Object — описывает операции с таблицей tasks.
 * Реализацию Room генерирует сам во время сборки.
 * suspend — функции можно вызывать только из корутины, поэтому они не блокируют главный поток.
 */
@Dao
interface TaskDao {

    /** Вставка. Возвращает id созданной записи. */
    @Insert
    suspend fun insert(task: Task): Long

    /** Обновление записи с тем же id. */
    @Update
    suspend fun update(task: Task)

    /** Удаление записи (ищется по первичному ключу). */
    @Delete
    suspend fun delete(task: Task)

    @Query("SELECT * FROM tasks ORDER BY date ASC, time ASC")
    suspend fun getAll(): List<Task>

    /** :date — параметр запроса, подставляется из аргумента функции. */
    @Query("SELECT * FROM tasks WHERE date = :date ORDER BY time ASC")
    suspend fun getByDate(date: String): List<Task>

    @Query("SELECT * FROM tasks WHERE id = :id")
    suspend fun getById(id: Long): Task?

    @Query("DELETE FROM tasks")
    suspend fun deleteAll()
}
