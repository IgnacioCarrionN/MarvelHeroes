package dev.carrion.marvelheroes.characterdetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import dev.carrion.marvelheroes.data.MarvelRepository
import dev.carrion.marvelheroes.models.CharacterDatabase

class CharacterDetailsViewModel(val repository: MarvelRepository, val characterId: Int): ViewModel() {

    val character: LiveData<CharacterDatabase> = repository.searchCharacterDetails(characterId)
}