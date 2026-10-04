package ru.university.taskcalendar.ui

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.university.taskcalendar.R
import ru.university.taskcalendar.data.AppDatabase

/**
 * Главный экран: список задач из базы данных Room.
 * Пока Activity обращается к DAO напрямую — на этапе 4 это будет вынесено в ViewModel/Repository.
 */
class MainActivity : AppCompatActivity() {

    private val dao by lazy { AppDatabase.getInstance(this).taskDao() }
    private lateinit var adapter: TaskAdapter
    private lateinit var tvEmpty: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvEmpty = findViewById(R.id.tvEmpty)

        adapter = TaskAdapter(emptyList()) { task ->
            val intent = Intent(this, TaskDetailActivity::class.java)
            intent.putExtra(TaskDetailActivity.EXTRA_TASK_ID, task.id)
            startActivity(intent)
        }

        val recyclerView = findViewById<RecyclerView>(R.id.rvTasks)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        findViewById<FloatingActionButton>(R.id.fabAdd).setOnClickListener {
            startActivity(Intent(this, AddTaskActivity::class.java))
        }
    }

    /** Перечитываем данные при каждом возвращении на экран (после отладочного экрана, формы и т. д.). */
    override fun onResume() {
        super.onResume()
        loadTasks()
    }

    private fun loadTasks() {
        lifecycleScope.launch {
            // Запрос к БД — в потоке для ввода-вывода (Dispatchers.IO), а не в главном (UI) потоке
            val tasks = withContext(Dispatchers.IO) { dao.getAll() }
            // После withContext мы снова в главном потоке — можно трогать View
            adapter.submitTasks(tasks)
            tvEmpty.visibility = if (tasks.isEmpty()) View.VISIBLE else View.GONE
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean =
        when (item.itemId) {
            R.id.action_debug -> {
                startActivity(Intent(this, DebugActivity::class.java))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
}
