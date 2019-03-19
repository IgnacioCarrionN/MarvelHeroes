package dev.carrion.marvelheroes.data

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import dev.carrion.marvelheroes.models.Character
import dev.carrion.marvelheroes.models.CharacterSearchResult
import dev.carrion.marvelheroes.network.MarvelApi

class MarvelRepository(
    private val marvelApi: MarvelApi
){
    val data: MutableLiveData<List<Character>> = MutableLiveData()


    fun searchCharacters(name: String?): CharacterSearchResult {
        Log.d("MarvelRepository", "New search: $name")



        val dataSourceFactory = object : DataSource.Factory<Int, Character>() {
            var networkErrors = MutableLiveData<String>()
            override fun create(): DataSource<Int, Character> {
                val latestSource = MarvelDataSource(marvelApi,name)
                networkErrors = latestSource.networkErrors
                return latestSource
            }

        }


        val pagedListConfig = PagedList.Config.Builder()
            .setEnablePlaceholders(true)
            .setPageSize(PAGE_SIZE)
            .setMaxSize(MAX_SIZE)
            .setPrefetchDistance(PREFETCH_DISTANCE)
            .build()

        val data = LivePagedListBuilder(dataSourceFactory,pagedListConfig).build()

        return CharacterSearchResult(data, dataSourceFactory.networkErrors)
    }

    companion object {
        private const val PAGE_SIZE = 100
        private const val MAX_SIZE = 200
        private const val PREFETCH_DISTANCE = 50
    }
}
