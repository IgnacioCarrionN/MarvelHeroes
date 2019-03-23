package dev.carrion.marvelheroes.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagedList
import dev.carrion.marvelheroes.data.db.MarvelLocalCache
import dev.carrion.marvelheroes.models.Character
import dev.carrion.marvelheroes.models.CharacterDatabase
import dev.carrion.marvelheroes.data.network.MarvelApi
import dev.carrion.marvelheroes.data.network.fetchCharacters
import dev.carrion.marvelheroes.getComicListWithId
import dev.carrion.marvelheroes.getEventListWithId
import dev.carrion.marvelheroes.toCharacterDatabaseList

/**
 * Marvel Callback
 *
 * This class handles if it's needed to make a request to the remote data source and converts data to DB models.
 *
 * @property name Character name to search
 * @property api MarvelApi instance to make remote calls.
 * @property cache MarvelLocalCache instance to save data on local DB.
 */
class MarvelCallback(private val name: String?,
                     private val api: MarvelApi,
                     private val cache: MarvelLocalCache) : PagedList.BoundaryCallback<CharacterDatabase>() {

    private var lastRequestedPage = 0

    private var isRequestInProgress = false

    val loading: MutableLiveData<Boolean> = MutableLiveData()
    val attributionText: MutableLiveData<String> = MutableLiveData()

    private val _networkErrors = MutableLiveData<String>()

    val networkErrors: LiveData<String>
        get() = _networkErrors

    override fun onZeroItemsLoaded() {
        Log.d("Callback", "onZeroItemsLoaded")
        loading.postValue(true)
        requestAndSaveData(name)
    }

    override fun onItemAtEndLoaded(itemAtEnd: CharacterDatabase) {
        Log.d("Callback", "onItemAtEndLoaded")
        requestAndSaveData(name)
    }

    /**
     * Request data to the remote source and saves on DB
     *
     * @property name Character name for query search.
     */
    private fun requestAndSaveData(name: String?){
        if(isRequestInProgress) return

        isRequestInProgress = true
        val startPosition = lastRequestedPage * NETWORK_PAGE_SIZE

        fetchCharacters(api, name, startPosition, NETWORK_PAGE_SIZE, { characterDataWrapper ->
            val charactersDatabase = characterDataWrapper.data.results.toCharacterDatabaseList()
            cache.insertCharacters(charactersDatabase) {
                saveComicsAndEvents(characterDataWrapper.data.results)
                loading.postValue(false)
                attributionText.postValue(characterDataWrapper.attributionText)
            }
        }, { error ->
            Log.d("MarvelDataSource", error)
            isRequestInProgress = false
            loading.postValue(false)
            _networkErrors.postValue(error)
            requestAndSaveData(name)
        })
    }


    /**
     * Saves comics and events on the DB.
     * @property characters List of Characters from JSON conversion.
     */
    private fun saveComicsAndEvents(characters: List<Character>){
        characters.forEach {
            cache.insertComics(it.comics.items.getComicListWithId(it.id)){}
            cache.insertEvents(it.events.items.getEventListWithId(it.id)){}
        }
        lastRequestedPage++
        isRequestInProgress = false
    }


    companion object {
        private const val NETWORK_PAGE_SIZE = 50
    }
}