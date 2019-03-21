package dev.carrion.marvelheroes.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import dev.carrion.marvelheroes.data.db.MarvelLocalCache
import dev.carrion.marvelheroes.models.*
import dev.carrion.marvelheroes.data.network.MarvelApi

/**
 * Marvel Callback
 *
 * This class handles the Local and Remote data sources..
 *
 * @property marvelApi MarvelApi instance to make remote calls.
 * @property cache LocalCache instance to save an load data from local DB.
 */
class MarvelRepository(
    private val marvelApi: MarvelApi,
    private val cache: MarvelLocalCache
){

    /**
     *  Search characters from the local DB and create Callback to handle remote calls when needed.
     *
     * @property name Character name to search
     * @return CharacterSearchResult with data, network errors and loading status.
     */
    fun searchCharacters(name: String?): CharacterSearchResult {
        Log.d("MarvelRepository", "New search: $name")
        clearTables()

        val dataSourceFactory =
            if(name == null)
                cache.getCharacters()
            else
                cache.getCharactersByName(nameWithWildcard(name))


        val boundaryCallback = MarvelCallback(name, marvelApi, cache)
        val networkErrors = boundaryCallback.networkErrors
        val loading = boundaryCallback.loading

        val pagedListConfig = createPagedConfig()

        val data = LivePagedListBuilder(dataSourceFactory,pagedListConfig)
            .setBoundaryCallback(boundaryCallback)
            .build()

        return CharacterSearchResult(data, networkErrors, loading)
    }

    /**
     *  Clear Character, Comics and Events tables.
     */
    private fun clearTables(){
        cache.clearCharacterTable()
        cache.clearComicsTable()
        cache.clearEventsTable()
    }

    /**
     * PagedList Configuration
     */
    private fun createPagedConfig() = PagedList.Config.Builder()
        .setEnablePlaceholders(true)
        .setPageSize(PAGE_SIZE)
        .setMaxSize(MAX_SIZE)
        .setPrefetchDistance(PREFETCH_DISTANCE)
        .build()

    /**
     *  Searching on the DB for a name starting by:
     *  @property name
     */
    private fun nameWithWildcard(name: String) = "$name%"


    /**
     *  Searching on the DB for comics
     *  @property characterId CharacterId to search comics.
     *  @return LiveData with comics
     */
    fun searchComics(characterId: Int): LiveData<List<ComicSummary>> = cache.getComicsForCharacter(characterId)

    /**
     *  Searching on the DB for events
     *  @property characterId CharacterId to search events.
     *  @return LiveData with events
     */
    fun searchEvents(characterId: Int): LiveData<List<EventSummary>> = cache.getEventsForCharacter(characterId)

    /**
     *  Searching on the DB for CharacterDetails
     *  @property characterId CharacterId to search details.
     *  @return LiveData with CharacterDetails
     */
    fun searchCharacterDetails(characterId: Int): LiveData<CharacterDatabase> = cache.getCharacterDetails(characterId)

    companion object {
        private const val PAGE_SIZE = 20
        private const val MAX_SIZE = 200
        private const val PREFETCH_DISTANCE = 50
    }
}
