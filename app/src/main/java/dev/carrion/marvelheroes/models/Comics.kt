package dev.carrion.marvelheroes.models

import androidx.room.Entity
import androidx.room.PrimaryKey

data class ComicList(val available: Int, val returned: Int, val collectionUri: String, val items: List<ComicSummary>)

@Entity(tableName = "comics")
data class ComicSummary(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val resourceURI: String,
    val name: String,
    val characterId: Int
)