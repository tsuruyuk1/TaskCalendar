package ru.university.taskcalendar.data

/**
 * Модель задачи. На этапе 2 — обычный data-класс (без базы данных).
 * data class автоматически даёт equals/hashCode/toString/copy.
 *
 * @property date дата в формате ISO «yyyy-MM-dd» (так её удобно хранить и сортировать как строку)
 */
data class Task(
    val id: Long,
    val title: String,
    val description: String,
    val date: String,
    val isDone: Boolean = false
)
