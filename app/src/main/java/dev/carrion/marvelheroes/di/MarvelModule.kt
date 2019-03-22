package dev.carrion.marvelheroes.di

import dev.carrion.marvelheroes.data.MarvelCallback
import dev.carrion.marvelheroes.ui.mainlist.CharactersListViewModel
import dev.carrion.marvelheroes.ui.characterdetails.CharacterDetailsViewModel
import dev.carrion.marvelheroes.ui.characterdetails.comicslist.ComicListViewModel
import dev.carrion.marvelheroes.ui.characterdetails.eventslist.EventListViewModel
import dev.carrion.marvelheroes.data.MarvelRepository
import dev.carrion.marvelheroes.data.db.MarvelDatabase
import dev.carrion.marvelheroes.data.db.MarvelLocalCache
import dev.carrion.marvelheroes.data.network.MarvelApi
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module

/**
 * Marvel Module
 *
 * This class handles the creation of instances for DI using Koin Library.
 *
 */
val MarvelModule = module {

    single { MarvelApi.create() }

    single { MarvelDatabase.getInstance(androidContext()).marvelDao()}

    single { MarvelLocalCache(get())}

    single { MarvelRepository(get(), get()) }

    factory { MarvelCallback(getProperty("name"),get(),get())}


    viewModel { CharactersListViewModel(get()) }

    viewModel { CharacterDetailsViewModel(get(), getProperty("id")) }

    viewModel { ComicListViewModel(get(), getProperty("id")) }

    viewModel { EventListViewModel(get(), getProperty("id")) }

}