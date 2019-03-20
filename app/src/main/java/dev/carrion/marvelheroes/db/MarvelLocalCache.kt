package dev.carrion.marvelheroes.db

import android.provider.ContactsContract
import android.util.EventLog
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import dev.carrion.marvelheroes.models.Character
import dev.carrion.marvelheroes.models.CharacterDatabase
import dev.carrion.marvelheroes.models.ComicSummary
import dev.carrion.marvelheroes.models.EventSummary
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class MarvelLocalCache(private val marvelDao: MarvelDao) : CoroutineScope {

    private val job = Job()

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Default + job


    fun insertCharacters(characters: List<CharacterDatabase>, insertFinished: () -> Unit){
        if(characters.isNotEmpty()){
            launch {
                Log.d("LocalCache", "inserting: ${characters.size} characters")
                marvelDao.insertCharacters(characters)
                insertFinished()
            }
        }
    }

    fun getCharactersByName(name: String): DataSource.Factory<Int,CharacterDatabase> =
        marvelDao.getCharactersByName(name)


    fun getCharacters(): DataSource.Factory<Int, CharacterDatabase> = marvelDao.getCharacters()


    fun insertComics(comics: List<ComicSummary>, insertFinished: () -> Unit) {
        if(comics.isNotEmpty()){
            launch {
                marvelDao.insertComics(comics)
                insertFinished()
            }
        }
    }

    fun getComicsForCharacter(id: Int): LiveData<List<ComicSummary>> = marvelDao.getComicsForCharacter(id)

    fun insertEvents(events: List<EventSummary>, insertFinished: () -> Unit) {
        if(events.isNotEmpty()){
            launch {
                marvelDao.insertEvents(events)
                insertFinished()
            }
        }
    }

    fun getEventsForCharacter(id: Int): LiveData<List<EventSummary>> = marvelDao.getEventsForCharacter(id)

    fun clearCharacterTable() = launch {
        marvelDao.clearCharactersTable()
    }

    fun clearComicsTable() = launch {
        marvelDao.clearComicsTable()
    }

    fun clearEventsTable() = launch {
        marvelDao.clearEventsTable()
    }

}