package dev.carrion.marvelheroes.di

import dev.carrion.marvelheroes.MarvelViewModel
import dev.carrion.marvelheroes.data.MarvelRepository
import dev.carrion.marvelheroes.network.MarvelApi
import org.koin.android.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module

val MarvelModule = module {

    single { MarvelApi.create() }

    single { MarvelRepository(get()) }

    viewModel { MarvelViewModel(get()) }
}