package dev.carrion.marvelheroes.data.db

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import dev.carrion.marvelheroes.models.CharacterDatabase
import dev.carrion.marvelheroes.models.ComicSummary
import dev.carrion.marvelheroes.models.EventSummary
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

/**
 * Marvel Local Cache
 *
 * This class handles the calls to the local DB.
 *
 * @property marvelDao MarvelDao instance to make calls to DB.
 */
class MarvelLocalCache(private val marvelDao: MarvelDao) : CoroutineScope {

    private val job = Job()

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Default + job


    /**
     *
     * Insert characters in local DB and notifies when it ends.
     *
     * @property characters List of CharacterDatabase.
     * @property insertFinished Function called on insertion completed.
     */
    fun insertCharacters(characters: List<CharacterDatabase>, insertFinished: () -> Unit){
        if(characters.isNotEmpty()){
            launch {
                Log.d("LocalCache", "inserting: ${characters.size} characters")
                marvelDao.insertCharacters(characters)
                insertFinished()
            }
        }
    }

    /**
     * Get Characters from DB by name.
     *
     * @property name Character Name.
     * @return DataSource for Paging Library
     */
    fun getCharactersByName(name: String): DataSource.Factory<Int,CharacterDatabase> =
        marvelDao.getCharactersByName(name)


    /**
     * Get all Characters from DB.
     *
     * @return DataSource for Paging Library
     */
    fun getCharacters(): DataSource.Factory<Int, CharacterDatabase> = marvelDao.getCharacters()

    /**
     * Insert Comics on DB and notifies when it ends.
     *
     * @property comics ComicList to insert on DB
     * @property insertFinished Function called on insertion completed.
     */
    fun insertComics(comics: List<ComicSummary>, insertFinished: () -> Unit) {
        if(comics.isNotEmpty()){
            launch {
                marvelDao.insertComics(comics)
                insertFinished()
            }
        }
    }

    /**
     * Get all Comics for given character from DB.
     *
     * @property id Character ID to search on DB.
     * @return LiveData with comic list.
     */
    fun getComicsForCharacter(id: Int): LiveData<List<ComicSummary>> = marvelDao.getComicsForCharacter(id)

    /**
     * Insert Events on DB and notifies when it ends.
     *
     * @property events Event List to insert on DB.
     * @property insertFinished Function called on insertion completed.
     */
    fun insertEvents(events: List<EventSummary>, insertFinished: () -> Unit) {
        if(events.isNotEmpty()){
            launch {
                marvelDao.insertEvents(events)
                insertFinished()
            }
        }
    }

    /**
     * Get all Events for given character from DB.
     *
     * @property id Character ID to search on DB.
     * @return LiveData with eveny list.
     */
    fun getEventsForCharacter(id: Int): LiveData<List<EventSummary>> = marvelDao.getEventsForCharacter(id)

    /**
     * Get character details for given id.
     *
     * @property id Character ID to search on DB.
     * @return LiveData with character details.
     */
    fun getCharacterDetails(id: Int): LiveData<CharacterDatabase> = marvelDao.getCharacterById(id)

    /**
     * Delete data on Character table
     */
    fun clearCharacterTable() = launch {
        marvelDao.clearCharactersTable()
    }

    /**
     * Delete data on Comics table
     */
    fun clearComicsTable() = launch {
        marvelDao.clearComicsTable()
    }

    /**
     * Delete data on Events table
     */
    fun clearEventsTable() = launch {
        marvelDao.clearEventsTable()
    }


}