// Корневой build-файл: только объявляет версии плагинов, применяются они в модуле app.
plugins {
    id("com.android.application") version "8.5.2" apply false
    id("org.jetbrains.kotlin.android") version "2.0.21" apply false
    // KSP — обработчик аннотаций: по @Entity/@Dao/@Database он генерирует код Room
    id("com.google.devtools.ksp") version "2.0.21-1.0.28" apply false
}
