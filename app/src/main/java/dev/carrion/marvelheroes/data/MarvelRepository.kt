package dev.carrion.marvelheroes.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import dev.carrion.marvelheroes.db.MarvelLocalCache
import dev.carrion.marvelheroes.models.Character
import dev.carrion.marvelheroes.models.CharacterSearchResult
import dev.carrion.marvelheroes.models.ComicSummary
import dev.carrion.marvelheroes.models.EventSummary
import dev.carrion.marvelheroes.network.MarvelApi

class MarvelRepository(
    private val marvelApi: MarvelApi,
    private val cache: MarvelLocalCache
){

    fun searchCharacters(name: String?): CharacterSearchResult {
        Log.d("MarvelRepository", "New search: $name")
        cache.clearCharacterTable()
        cache.clearComicsTable()
        cache.clearEventsTable()

        val dataSourceFactory =
            if(name == null)
                cache.getCharacters()
            else
                cache.getCharactersByName(nameWithWildcard(name))


        val boundaryCallback = MarvelCallback(name, marvelApi, cache)
        val networkErrors = boundaryCallback.networkErrors

        val pagedListConfig = createPagedConfig()

        val data = LivePagedListBuilder(dataSourceFactory,pagedListConfig)
            .setBoundaryCallback(boundaryCallback)
            .build()

        return CharacterSearchResult(data, networkErrors)
    }

    private fun createPagedConfig() = PagedList.Config.Builder()
        .setEnablePlaceholders(true)
        .setPageSize(PAGE_SIZE)
        .setMaxSize(MAX_SIZE)
        .setPrefetchDistance(PREFETCH_DISTANCE)
        .build()

    private fun nameWithWildcard(name: String) = "$name%"


    fun searchComics(characterId: Int): LiveData<List<ComicSummary>> = cache.getComicsForCharacter(characterId)

    fun searchEvents(characterId: Int): LiveData<List<EventSummary>> = cache.getEventsForCharacter(characterId)

    companion object {
        private const val PAGE_SIZE = 20
        private const val MAX_SIZE = 200
        private const val PREFETCH_DISTANCE = 50
    }
}
