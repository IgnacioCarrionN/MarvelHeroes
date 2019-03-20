package dev.carrion.marvelheroes.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagedList
import dev.carrion.marvelheroes.db.MarvelLocalCache
import dev.carrion.marvelheroes.models.Character
import dev.carrion.marvelheroes.models.CharacterDatabase
import dev.carrion.marvelheroes.network.MarvelApi
import dev.carrion.marvelheroes.network.fetchCharacters

class MarvelCallback(private val name: String?,
                     private val api: MarvelApi,
                     private val cache: MarvelLocalCache) : PagedList.BoundaryCallback<CharacterDatabase>() {

    private var lastRequestedPage = 0

    private var isRequestInProgress = false

    private val _networkErrors = MutableLiveData<String>()

    val networkErrors: LiveData<String>
        get() = _networkErrors

    override fun onZeroItemsLoaded() {
        Log.d("Callback", "onZeroItemsLoaded")
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
                saveComics(characterDataWrapper.data.results)
            }
        }, { error ->
            Log.d("MarvelDataSource", error)
            isRequestInProgress = false
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

    private fun saveComics(characters: List<Character>){
        characters.forEach {
            cache.insertComics(it.comics.items){}
        }
        lastRequestedPage++
        isRequestInProgress = false
    }

    companion object {
        private const val NETWORK_PAGE_SIZE = 50
    }
}