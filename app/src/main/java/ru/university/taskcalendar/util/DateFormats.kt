package ru.university.taskcalendar.util

import ru.university.taskcalendar.data.Task
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

private val displayFormatter: DateTimeFormatter =
    DateTimeFormatter.ofPattern("d MMMM yyyy", Locale("ru"))

/** Превращает «2026-10-05» в «5 октября 2026». Если строка не распознана — возвращает её как есть. */
fun formatIsoDate(iso: String): String =
    try {
        LocalDate.parse(iso).format(displayFormatter)
    } catch (e: Exception) {
        iso
    }

/** «5 октября 2026, 18:00» (время добавляется, только если оно задано). */
fun Task.displayDateTime(): String =
    formatIsoDate(date) + (time?.let { ", $it" } ?: "")
