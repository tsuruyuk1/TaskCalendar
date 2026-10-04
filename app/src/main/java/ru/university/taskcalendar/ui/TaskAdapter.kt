package ru.university.taskcalendar.ui

import android.annotation.SuppressLint
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.university.taskcalendar.R
import ru.university.taskcalendar.data.Task
import ru.university.taskcalendar.util.displayDateTime

/**
 * Adapter связывает список данных (Task) с элементами RecyclerView.
 * onItemClick — лямбда-обработчик нажатия, передаётся снаружи (из Activity).
 */
class TaskAdapter(
    private var tasks: List<Task>,
    private val onItemClick: (Task) -> Unit
) : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    /**
     * ViewHolder хранит ссылки на View одного элемента списка.
     * findViewById вызывается один раз при создании holder'а, а не при каждой прокрутке.
     */
    class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val status: ImageView = itemView.findViewById(R.id.ivStatus)
        val title: TextView = itemView.findViewById(R.id.tvTaskTitle)
        val date: TextView = itemView.findViewById(R.id.tvTaskDate)
    }

    /** Вызывается, когда нужен НОВЫЙ элемент (создаём разметку из item_task.xml). */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_task, parent, false)
        return TaskViewHolder(view)
    }

    /** Вызывается для заполнения (в т.ч. ПЕРЕИСПОЛЬЗОВАННОГО) элемента данными конкретной задачи. */
    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = tasks[position]

        holder.title.text = task.title
        holder.date.text = task.displayDateTime()

        if (task.isDone) {
            holder.status.setImageResource(R.drawable.ic_status_done)
            holder.status.contentDescription = holder.itemView.context.getString(R.string.status_done)
            holder.title.paintFlags = holder.title.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
        } else {
            holder.status.setImageResource(R.drawable.ic_status_pending)
            holder.status.contentDescription = holder.itemView.context.getString(R.string.status_pending)
            holder.title.paintFlags = holder.title.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
        }

        // Лямбда-синтаксис Kotlin вместо анонимного класса object : View.OnClickListener { ... }
        holder.itemView.setOnClickListener { onItemClick(task) }
    }

    override fun getItemCount(): Int = tasks.size

    /** Заменяет список. На этапе 4 заменим на ListAdapter + DiffUtil. */
    @SuppressLint("NotifyDataSetChanged")
    fun submitTasks(newTasks: List<Task>) {
        tasks = newTasks
        notifyDataSetChanged()
    }
}
