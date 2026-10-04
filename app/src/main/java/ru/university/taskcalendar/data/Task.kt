package ru.university.taskcalendar.data

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Задача. Теперь это Room-Entity: класс = таблица «tasks», поле = столбец.
 *
 * @property id первичный ключ; autoGenerate = true — базу сама выдаёт уникальные id (при вставке передаём 0)
 * @property date дата в формате ISO «yyyy-MM-dd» (текстом удобно сравнивать, сортировать и искать: WHERE date = :date)
 * @property time время «HH:mm» или null, если время не задано
 */
@Entity(tableName = "tasks")
data class Task(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val title: String,
    val description: String = "",
    val date: String,
    val time: String? = null,
    val isDone: Boolean = false
)
