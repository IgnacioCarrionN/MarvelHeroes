package dev.carrion.marvelheroes.ui.characterdetails.comicslist

import androidx.lifecycle.ViewModel
import dev.carrion.marvelheroes.data.MarvelRepository

/**
 * Comic List ViewModel
 *
 * This class communicates with the repository and observe changes on the comic list on it.
 *
 * @property repository MarvelRepository instance
 * @property id Character ID to search comics on DB.
 */
class ComicListViewModel(repository: MarvelRepository, id: Int) : ViewModel() {

    val comicList = repository.searchComics(id)

}