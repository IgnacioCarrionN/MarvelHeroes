package dev.carrion.marvelheroes.models

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * File keeps comics related models on the same place.
 */

/**
 * ComicList model from Marvel API
 */
data class ComicList(val available: Int, val returned: Int, val collectionUri: String, val items: List<ComicSummary>)


/**
 * ComicSummary model from Marvel API.
 * @PrimaryKey id autoIncrement
 * @ForeignKey characterId references id from Characters table.
 */
@Entity(tableName = "comics")
data class ComicSummary(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val resourceURI: String,
    val name: String,
    val characterId: Int
) {
    fun getComicWithCharacterId(characterId: Int): ComicSummary =
        ComicSummary(0, resourceURI, name, characterId)
}