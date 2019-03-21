package dev.carrion.marvelheroes

import android.app.Application
import dev.carrion.marvelheroes.di.MarvelModule
import org.koin.android.ext.android.startKoin
import org.koin.android.logger.AndroidLogger


/**
 * MarvelApplication it's the base Application class for this app.
 * Handles the initialization of the Koin library.
 *
 * Android Studio warns about unused class so we annotate with @suppress
 */
@Suppress("unused")
class MarvelApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin(this, listOf(MarvelModule), logger = AndroidLogger())
    }
}