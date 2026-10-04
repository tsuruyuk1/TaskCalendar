package ru.university.taskcalendar.ui

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.university.taskcalendar.R
import ru.university.taskcalendar.data.AppDatabase
import ru.university.taskcalendar.data.Task
import ru.university.taskcalendar.util.displayDateTime
import java.time.LocalDate

/**
 * Отладочный экран этапа 3: проверка CRUD-операций Room.
 * Все результаты пишутся в Logcat (фильтр по тегу "TaskDb") и показываются на экране.
 * Все обращения к базе выполняются в Dispatchers.IO.
 */
class DebugActivity : UpNavigationActivity() {

    private val dao by lazy { AppDatabase.getInstance(this).taskDao() }
    private lateinit var tvOutput: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_debug)
        enableUpNavigation()

        tvOutput = findViewById(R.id.tvOutput)

        findViewById<android.view.View>(R.id.btnAdd).setOnClickListener { addSamples() }
        findViewById<android.view.View>(R.id.btnRead).setOnClickListener { refresh() }
        findViewById<android.view.View>(R.id.btnUpdate).setOnClickListener { toggleFirst() }
        findViewById<android.view.View>(R.id.btnDeleteFirst).setOnClickListener { deleteFirst() }
        findViewById<android.view.View>(R.id.btnClear).setOnClickListener { clearAll() }

        refresh()
    }

    /** INSERT: добавляем несколько тестовых задач. */
    private fun addSamples() {
        lifecycleScope.launch {
            val ids = withContext(Dispatchers.IO) { createSampleTasks().map { dao.insert(it) } }
            Log.d(TAG, "INSERT: добавлено ${ids.size} задач, id = $ids")
            showAll()
        }
    }

    /** UPDATE: переключаем isDone у первой задачи. */
    private fun toggleFirst() {
        lifecycleScope.launch {
            val changed = withContext(Dispatchers.IO) {
                val first = dao.getAll().firstOrNull() ?: return@withContext null
                first.copy(isDone = !first.isDone).also { dao.update(it) }
            }
            Log.d(TAG, "UPDATE: " + (changed?.toString() ?: "в базе нет задач"))
            showAll()
        }
    }

    /** DELETE: удаляем первую задачу. */
    private fun deleteFirst() {
        lifecycleScope.launch {
            val deleted = withContext(Dispatchers.IO) {
                dao.getAll().firstOrNull()?.also { dao.delete(it) }
            }
            Log.d(TAG, "DELETE: " + (deleted?.toString() ?: "в базе нет задач"))
            showAll()
        }
    }

    private fun clearAll() {
        lifecycleScope.launch {
            withContext(Dispatchers.IO) { dao.deleteAll() }
            Log.d(TAG, "DELETE ALL: таблица очищена")
            showAll()
        }
    }

    /** SELECT: читаем всё и выводим в Logcat + на экран. */
    private fun refresh() {
        lifecycleScope.launch { showAll() }
    }

    private suspend fun showAll() {
        val today = LocalDate.now().toString()
        val (all, todayTasks) = withContext(Dispatchers.IO) { dao.getAll() to dao.getByDate(today) }

        Log.d(TAG, "SELECT ALL: в базе ${all.size} задач")
        all.forEach { Log.d(TAG, "   $it") }
        Log.d(TAG, "SELECT BY DATE ($today): ${todayTasks.size} задач")

        tvOutput.text =
            if (all.isEmpty()) {
                getString(R.string.debug_empty)
            } else {
                getString(R.string.debug_summary, all.size, todayTasks.size) + "\n\n" +
                    all.joinToString("\n\n") {
                        "#${it.id} ${if (it.isDone) "[x]" else "[ ]"} ${it.title}\n    ${it.displayDateTime()}"
                    }
            }
    }

    private fun createSampleTasks(): List<Task> {
        val today = LocalDate.now()
        return listOf(
            Task(title = "Сдать лабораторную по Kotlin", description = "Закончить отчёт и загрузить проект в репозиторий.",
                date = today.toString(), time = "18:00"),
            Task(title = "Купить продукты", description = "Молоко, хлеб, яйца, фрукты.",
                date = today.toString(), time = "19:30"),
            Task(title = "Подготовиться к семинару", description = "Прочитать главу про жизненный цикл Activity.",
                date = today.plusDays(1).toString(), time = "10:00"),
            Task(title = "Сходить в спортзал", date = today.plusDays(2).toString(), time = "19:00"),
            Task(title = "Оплатить интернет", description = "Не забыть до конца недели.",
                date = today.plusDays(5).toString())
        )
    }

    private companion object {
        const val TAG = "TaskDb"
    }
}
