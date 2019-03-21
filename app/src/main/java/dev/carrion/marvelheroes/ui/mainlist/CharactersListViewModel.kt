package dev.carrion.marvelheroes.ui.mainlist

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import dev.carrion.marvelheroes.data.MarvelRepository
import dev.carrion.marvelheroes.models.CharacterDatabase
import dev.carrion.marvelheroes.models.CharacterSearchResult

/**
 * Character List ViewModel
 *
 * This class communicates with the repository and gets a CharacterSearchResult based on the query name.
 *
 * @property repository MarvelRepository instance
 */
class CharactersListViewModel(private val repository: MarvelRepository) : ViewModel() {


    private val nameLiveData = MutableLiveData<String?>()

    private val characterResult: LiveData<CharacterSearchResult> = Transformations.map(nameLiveData) {
        Log.d("ViewModel", "New search: $it")
        repository.searchCharacters(it)
    }

    var characterList: LiveData<PagedList<CharacterDatabase>> = Transformations.switchMap(characterResult) {
        Log.d("ViewModel", "Character List LiveData ${it.data.hasActiveObservers()}")
        it.data
    }

    val networkErrors: LiveData<String> = Transformations.switchMap(characterResult) { it.networkErrors}

    val loading: LiveData<Boolean> = Transformations.switchMap(characterResult) { it.loading }

    /**
     * When ViewModel it's created we search for null name to query all characters.
     */
    init {
        searchCharacters(null)
    }

    /**
     * Function called from fragment to change nameLiveData value and trigger a search.
     *
     * @property nameString Character starts with this name.
     */
    fun searchCharacters(nameString: String?){
        Log.d("ViewModel", "searchCharacters()")
        nameLiveData.postValue(nameString)
    }

}