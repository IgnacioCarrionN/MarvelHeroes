package dev.carrion.marvelheroes.characterdetails.comicslist

import android.util.Log
import androidx.lifecycle.ViewModel
import dev.carrion.marvelheroes.data.MarvelRepository

class ComicListViewModel(repository: MarvelRepository, id: Int) : ViewModel() {

    val comicList = repository.searchComics(id)

    init {
        comicList.observeForever {
            Log.d("ComicViewModel", "List size: ${it.size}")
        }
    }
}