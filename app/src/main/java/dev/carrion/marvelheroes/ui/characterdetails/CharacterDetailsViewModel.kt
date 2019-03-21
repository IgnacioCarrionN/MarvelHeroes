package dev.carrion.marvelheroes.ui.characterdetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import dev.carrion.marvelheroes.data.MarvelRepository
import dev.carrion.marvelheroes.models.CharacterDatabase

/**
 * Character Details ViewModel
 *
 * This class communicates with the repository get LiveData of character details.
 *
 * @property repository MarvelRepository instance
 * @property id Character ID to search comics on DB.
 */
class CharacterDetailsViewModel(repository: MarvelRepository, characterId: Int): ViewModel() {

    val character: LiveData<CharacterDatabase> = repository.searchCharacterDetails(characterId)
}