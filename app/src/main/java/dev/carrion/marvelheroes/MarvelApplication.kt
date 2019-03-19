package dev.carrion.marvelheroes

import android.app.Application
import dev.carrion.marvelheroes.di.MarvelModule
import org.koin.android.ext.android.startKoin
import org.koin.android.logger.AndroidLogger

class MarvelApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin(this, listOf(MarvelModule), logger = AndroidLogger())
    }
}