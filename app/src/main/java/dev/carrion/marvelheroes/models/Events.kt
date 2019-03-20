package dev.carrion.marvelheroes.models

import androidx.room.Entity
import androidx.room.PrimaryKey

data class EventList(val available: Int,
                     val returned: Int,
                     val collectionURI: String,
                     val items: List<EventSummary>)

@Entity(tableName = "events")
data class EventSummary(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val resourceURI: String,
    val name: String,
    val characterId: Int
)