package dev.carrion.marvelheroes.models

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

/**
 * File keeps character related models on the same place.
 */


/**
 * CharacterDataWrapper model from Marvel API.
 */
data class CharacterDataWrapper(
    val code: Int,
    val status: String,
    val copyright: String,
    val attributionText: String,
    val attributionHTML: String,
    val data: CharacterDataContainer,
    val etag: String
)

/**
 * CharacterDataContainer model from Marvel API.
 */
data class CharacterDataContainer(
    val offset: Int,
    val limit: Int,
    val total: Int,
    val count: Int,
    val results: List<Character>
)

/**
 * Character model from Marvel API.
 */
data class Character(
    val id: Int,
    val name: String,
    val description: String,
    val modified: String,
    val resourceURI: String,
    val urls: List<Url>?,
    val thumbnail: Image,
    val comics: ComicList,
    val stories: StoriesList,
    val events: EventList,
    val series: SeriesList
)
/**
 * Url and Image model from Marvel API.
 */
data class Url(val type: String, val url: String)
data class Image(val path: String, val extension: String)

/**
 * CharacterDatabase is the model used to store characters on database.
 */
@Entity(tableName = "characters")
data class CharacterDatabase(
    @PrimaryKey
    val id: Int,
    val name: String,
    val description: String,
    val modified: String,
    val resourceURI: String,
    val thumbnail: Image
){
    companion object {
        /**
         * Converts Character from Marvel API to a CharacterDatabase object.
         */
        fun fromCharacter(c: Character): CharacterDatabase =
            CharacterDatabase(c.id, c.name, c.description, c.modified, c.resourceURI, c.thumbnail)
    }
}