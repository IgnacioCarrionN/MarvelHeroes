package dev.carrion.marvelheroes.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagedList
import dev.carrion.marvelheroes.db.MarvelLocalCache
import dev.carrion.marvelheroes.models.Character
import dev.carrion.marvelheroes.models.CharacterDatabase
import dev.carrion.marvelheroes.models.ComicSummary
import dev.carrion.marvelheroes.models.EventSummary
import dev.carrion.marvelheroes.network.MarvelApi
import dev.carrion.marvelheroes.network.fetchCharacters

class MarvelCallback(private val name: String?,
                     private val api: MarvelApi,
                     private val cache: MarvelLocalCache) : PagedList.BoundaryCallback<CharacterDatabase>() {

    private var lastRequestedPage = 0

    private var isRequestInProgress = false

    val loading: MutableLiveData<Boolean> = MutableLiveData()

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

    private fun requestAndSaveData(name: String?){
        if(isRequestInProgress) return

        isRequestInProgress = true
        val startPosition = lastRequestedPage * NETWORK_PAGE_SIZE

        fetchCharacters(api, name, startPosition, NETWORK_PAGE_SIZE, { characterDataWrapper ->
            val charactersDatabase = characterListToDatabaseList(characterDataWrapper.data.results)
            cache.insertCharacters(charactersDatabase) {
                saveComicsAndEvents(characterDataWrapper.data.results)
                loading.postValue(false)
            }
        }, { error ->
            Log.d("MarvelDataSource", error)
            isRequestInProgress = false
            loading.postValue(false)
            _networkErrors.postValue(error)
            requestAndSaveData(name)
        })
    }

    private fun characterListToDatabaseList(characters: List<Character>): List<CharacterDatabase> {
        val charactersDatabase = mutableListOf<CharacterDatabase>()
        characters.forEach {
            charactersDatabase.add(CharacterDatabase.fromCharacter(it))
        }
        return charactersDatabase
    }


    private fun saveComicsAndEvents(characters: List<Character>){
        characters.forEach {
            cache.insertComics(getComicListWithId(it.comics.items, it.id)){}
            cache.insertEvents(getEventListWithId(it.events.items, it.id)){}
        }
        lastRequestedPage++
        isRequestInProgress = false
    }

    private fun getComicListWithId(comics: List<ComicSummary>, characterId: Int): List<ComicSummary> {
        val comicsWithId = mutableListOf<ComicSummary>()
        comics.forEach {
            comicsWithId.add(setComicCharacterId(it, characterId))
        }
        return comicsWithId
    }

    private fun setComicCharacterId(comic: ComicSummary, characterId: Int): ComicSummary =
        ComicSummary(0, comic.resourceURI, comic.name, characterId)

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