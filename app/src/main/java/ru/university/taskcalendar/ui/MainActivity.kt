package ru.university.taskcalendar.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import ru.university.taskcalendar.R
import ru.university.taskcalendar.data.SampleTasks

/** Главный экран: список задач (пока — тестовые данные) и кнопка «добавить». */
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val adapter = TaskAdapter(SampleTasks.tasks) { task ->
            // Явный Intent: переходим на экран деталей и передаём id задачи
            val intent = Intent(this, TaskDetailActivity::class.java)
            intent.putExtra(TaskDetailActivity.EXTRA_TASK_ID, task.id)
            startActivity(intent)
        }

        val recyclerView = findViewById<RecyclerView>(R.id.rvTasks)
        recyclerView.layoutManager = LinearLayoutManager(this)  // вертикальный список
        recyclerView.adapter = adapter

        findViewById<FloatingActionButton>(R.id.fabAdd).setOnClickListener {
            startActivity(Intent(this, AddTaskActivity::class.java))
        }
    }
}
