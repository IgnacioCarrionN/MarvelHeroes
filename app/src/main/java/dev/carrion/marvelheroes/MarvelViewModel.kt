package dev.carrion.marvelheroes

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import dev.carrion.marvelheroes.data.MarvelRepository
import dev.carrion.marvelheroes.models.CharacterDatabase
import dev.carrion.marvelheroes.models.CharacterSearchResult

class MarvelViewModel(private val repository: MarvelRepository) : ViewModel() {


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

    init {
        searchCharacters(null)
        characterList.observeForever {
            Log.d("ViewModel", "List size on observer: ${it.size}")
        }
    }

    fun searchCharacters(nameString: String?){
        Log.d("ViewModel", "searchCharacters()")
        nameLiveData.postValue(nameString)
    }

    fun lastNameValue(): String? = nameLiveData.value
}