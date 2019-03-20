package dev.carrion.marvelheroes.db

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.*
import dev.carrion.marvelheroes.models.Character
import dev.carrion.marvelheroes.models.CharacterDatabase
import dev.carrion.marvelheroes.models.ComicSummary

@Dao
interface MarvelDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCharacters(characters: List<CharacterDatabase>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertComics(comics: List<ComicSummary>)

    @Query("SELECT * FROM characters WHERE name LIKE :nameString ORDER BY name")
    fun getCharactersByName(nameString: String): DataSource.Factory<Int,CharacterDatabase>

    @Query("SELECT * FROM characters ORDER BY name")
    fun getCharacters(): DataSource.Factory<Int, CharacterDatabase>

    @Query("SELECT * FROM comics WHERE characterId = :id")
    fun getComicsForCharacter(id: Int): LiveData<List<ComicSummary>>

    @Query("DELETE FROM characters")
    fun clearCharactersTable()

    @Query("DELETE FROM comics")
    fun clearComicsTable()
}