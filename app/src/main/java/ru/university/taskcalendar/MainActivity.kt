package ru.university.taskcalendar

import android.os.Bundle
import android.util.Log
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

/**
 * Этап 1. Главный экран со статичным списком задач-заглушек.
 * Все методы жизненного цикла пишут сообщения в Logcat (фильтр по тегу "Lifecycle").
 */
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.d(TAG, "onCreate: Activity создана, разметка загружена")

        // val — переменная, которую нельзя переназначить (используем по умолчанию)
        val tasksContainer = findViewById<LinearLayout>(R.id.llTasks)
        val subtitle = findViewById<TextView>(R.id.tvSubtitle)

        // childCount — количество «задач-заглушек» внутри контейнера
        subtitle.text = getString(R.string.subtitle_count, tasksContainer.childCount)

        demoNullSafety()
    }

    /** Небольшая демонстрация null safety и val/var — для отчёта. */
    private fun demoNullSafety() {
        var counter = 0            // var — можно менять
        counter++
        val note: String? = null   // тип со знаком ? допускает null
        val length = note?.length ?: 0   // ?. — безопасный вызов, ?: — значение по умолчанию (elvis)
        Log.d(TAG, "demoNullSafety: counter=$counter, длина заметки=$length")
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart: экран становится видимым")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume: экран активен, пользователь может с ним взаимодействовать")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause: экран теряет фокус (например, открылось другое окно)")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop: экран больше не виден")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy: Activity уничтожается (закрыта или пересоздаётся при повороте)")
    }

    private companion object {
        const val TAG = "Lifecycle"
    }
}
