package dev.carrion.marvelheroes.di

import dev.carrion.marvelheroes.MarvelViewModel
import dev.carrion.marvelheroes.data.MarvelRepository
import dev.carrion.marvelheroes.db.MarvelDao
import dev.carrion.marvelheroes.db.MarvelDatabase
import dev.carrion.marvelheroes.db.MarvelLocalCache
import dev.carrion.marvelheroes.network.MarvelApi
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module

val MarvelModule = module {

    single { MarvelApi.create() }

    bean { MarvelDatabase.getInstance(androidContext()).marvelDao()}

    single { MarvelLocalCache(get())}

    single { MarvelRepository(get(), get()) }

    viewModel { MarvelViewModel(get()) }
}