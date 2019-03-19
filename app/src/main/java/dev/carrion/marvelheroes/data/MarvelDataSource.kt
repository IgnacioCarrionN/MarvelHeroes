package dev.carrion.marvelheroes.data

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.paging.PositionalDataSource
import dev.carrion.marvelheroes.models.Character
import dev.carrion.marvelheroes.network.MarvelApi
import dev.carrion.marvelheroes.network.fetchCharacters

class MarvelDataSource(private val service: MarvelApi, private val name: String?) : PositionalDataSource<Character>() {


    val networkErrors = MutableLiveData<String>()


    override fun loadInitial(params: LoadInitialParams, callback: LoadInitialCallback<Character>) {
        fetchCharacters(service, name, params.requestedStartPosition, params.pageSize, { characterDataWrapper ->
            val characters = characterDataWrapper.data.results
            callback.onResult(characters, characterDataWrapper.data.offset, characterDataWrapper.data.total)
        }, { error ->
            Log.d("MarvelDataSource", error)
            networkErrors.postValue(error)
        })

    }

    override fun loadRange(params: LoadRangeParams, callback: LoadRangeCallback<Character>) {
        fetchCharacters(service, name, params.startPosition, params.loadSize, { characterDataWrapper ->
            val characters = characterDataWrapper.data.results
            callback.onResult(characters)
        }, { error ->
            Log.d("MarvelDataSource", error)
            networkErrors.postValue(error)
        })
    }

}