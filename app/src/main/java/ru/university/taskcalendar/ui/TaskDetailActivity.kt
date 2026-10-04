package ru.university.taskcalendar.ui

import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import ru.university.taskcalendar.R
import ru.university.taskcalendar.data.SampleTasks
import ru.university.taskcalendar.util.formatIsoDate

/** Экран деталей задачи. Получает id через Intent и показывает данные задачи. */
class TaskDetailActivity : UpNavigationActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_detail)
        enableUpNavigation()

        // Достаём id, переданный через putExtra; -1 — значение по умолчанию, если extra нет
        val taskId = intent.getLongExtra(EXTRA_TASK_ID, -1L)
        val task = SampleTasks.findById(taskId)

        if (task == null) {
            Toast.makeText(this, R.string.task_not_found, Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        findViewById<TextView>(R.id.tvDetailTitle).text = task.title
        findViewById<TextView>(R.id.tvDetailDate).text =
            getString(R.string.detail_date, formatIsoDate(task.date))
        findViewById<TextView>(R.id.tvDetailStatus).setText(
            if (task.isDone) R.string.status_done else R.string.status_pending
        )
        findViewById<TextView>(R.id.tvDetailDescription).text = task.description
    }

    companion object {
        const val EXTRA_TASK_ID = "extra_task_id"
    }
}
