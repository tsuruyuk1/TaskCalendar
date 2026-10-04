package ru.university.taskcalendar.ui

import androidx.appcompat.app.AppCompatActivity

/** Базовый класс для вторичных экранов: стрелка «назад» в ActionBar. */
abstract class UpNavigationActivity : AppCompatActivity() {

    /** Вызывать после setContentView(). */
    protected fun enableUpNavigation() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}
