package dev.carrion.marvelheroes.models

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

data class CharacterDataWrapper(
    val code: Int,
    val status: String,
    val copyright: String,
    val attributionText: String,
    val attributionHTML: String,
    val data: CharacterDataContainer,
    val etag: String
)

data class CharacterDataContainer(
    val offset: Int,
    val limit: Int,
    val total: Int,
    val count: Int,
    val results: List<Character>
)


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

data class Url(val type: String, val url: String)
data class Image(val path: String, val extension: String)

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
        fun fromCharacter(c: Character): CharacterDatabase =
            CharacterDatabase(c.id, c.name, c.description, c.modified, c.resourceURI, c.thumbnail)
    }
}