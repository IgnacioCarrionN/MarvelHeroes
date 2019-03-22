package dev.carrion.marvelheroes.models

import androidx.lifecycle.LiveData
import androidx.paging.PagedList

/**
 * This class holds the result of the PagedList, Network errors, Loading Status and Attribution Text.
 */
data class CharacterSearchResult(
    val data: LiveData<PagedList<CharacterDatabase>>,
    val networkErrors: LiveData<String>,
    val loading: LiveData<Boolean>,
    val attributionText: LiveData<String>
)