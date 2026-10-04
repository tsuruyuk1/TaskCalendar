package ru.university.taskcalendar.ui

import android.os.Bundle
import ru.university.taskcalendar.R

/** Пустой экран-заглушка. Полноценная форма появится на этапе 5. */
class AddTaskActivity : UpNavigationActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_task)
        enableUpNavigation()
    }
}
