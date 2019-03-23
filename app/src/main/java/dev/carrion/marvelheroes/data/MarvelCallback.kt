package dev.carrion.marvelheroes.data

import android.util.Log
import androidx.annotation.VisibleForTesting
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagedList
import dev.carrion.marvelheroes.data.db.MarvelLocalCache
import dev.carrion.marvelheroes.models.Character
import dev.carrion.marvelheroes.models.CharacterDatabase
import dev.carrion.marvelheroes.models.ComicSummary
import dev.carrion.marvelheroes.models.EventSummary
import dev.carrion.marvelheroes.data.network.MarvelApi
import dev.carrion.marvelheroes.data.network.fetchCharacters
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
            cache.insertComics(getComicListWithId(it.comics.items, it.id)){}
            cache.insertEvents(getEventListWithId(it.events.items, it.id)){}
        }
        lastRequestedPage++
        isRequestInProgress = false
    }

    /**
     * Create new ComicSummary objects with CharacterId from Character JSON list.
     * @property comics List of ComicSummary without character ids.
     * @property characterId CharacterId to use as foreign key on DB.
     */
    private fun getComicListWithId(comics: List<ComicSummary>, characterId: Int): List<ComicSummary> {
        val comicsWithId = mutableListOf<ComicSummary>()
        comics.forEach {
            comicsWithId.add(setComicCharacterId(it, characterId))
        }
        return comicsWithId
    }

    private fun setComicCharacterId(comic: ComicSummary, characterId: Int): ComicSummary =
        ComicSummary(0, comic.resourceURI, comic.name, characterId)

    /**
     * Create new EventSummary objects with CharacterId from Character JSON list.
     * @property events List of EventSummary without character ids.
     * @property characterId CharacterId to use as foreign key on DB.
     */
    private fun getEventListWithId(events: List<EventSummary>, characterId: Int): List<EventSummary> {
        val eventsWithId = mutableListOf<EventSummary>()
        events.forEach {
            eventsWithId.add(setEventCharacterId(it, characterId))
        }
        return eventsWithId
    }

    private fun setEventCharacterId(event: EventSummary, characterId: Int): EventSummary =
        EventSummary(0, event.resourceURI, event.name, characterId)

    companion object {
        private const val NETWORK_PAGE_SIZE = 50
    }
}