package ru.university.taskcalendar.ui

import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.university.taskcalendar.R
import ru.university.taskcalendar.data.AppDatabase
import ru.university.taskcalendar.util.displayDateTime

/** Экран деталей задачи. Получает id через Intent и загружает задачу из базы данных. */
class TaskDetailActivity : UpNavigationActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_detail)
        enableUpNavigation()

        val taskId = intent.getLongExtra(EXTRA_TASK_ID, -1L)
        val dao = AppDatabase.getInstance(this).taskDao()

        lifecycleScope.launch {
            val task = withContext(Dispatchers.IO) { dao.getById(taskId) }

            if (task == null) {
                Toast.makeText(this@TaskDetailActivity, R.string.task_not_found, Toast.LENGTH_SHORT).show()
                finish()
                return@launch
            }

            findViewById<TextView>(R.id.tvDetailTitle).text = task.title
            findViewById<TextView>(R.id.tvDetailDate).text =
                getString(R.string.detail_date, task.displayDateTime())
            findViewById<TextView>(R.id.tvDetailStatus).setText(
                if (task.isDone) R.string.status_done else R.string.status_pending
            )
            findViewById<TextView>(R.id.tvDetailDescription).text =
                task.description.ifBlank { getString(R.string.no_description) }
        }
    }

    companion object {
        const val EXTRA_TASK_ID = "extra_task_id"
    }
}
