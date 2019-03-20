package dev.carrion.marvelheroes.models

import androidx.lifecycle.LiveData
import androidx.paging.PagedList

data class CharacterSearchResult(
    val data: LiveData<PagedList<CharacterDatabase>>,
    val networkErrors: LiveData<String>
)