package dev.carrion.marvelheroes.models

import androidx.lifecycle.LiveData
import androidx.paging.PagedList

/**
 * This class holds the result of the PagedList, the Network errors and the Loading Status.
 */
data class CharacterSearchResult(
    val data: LiveData<PagedList<CharacterDatabase>>,
    val networkErrors: LiveData<String>,
    val loading: LiveData<Boolean>
)