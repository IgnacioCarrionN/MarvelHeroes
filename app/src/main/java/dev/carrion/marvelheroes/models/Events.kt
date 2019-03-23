package dev.carrion.marvelheroes.models

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * File keeps events related models on the same place.
 */

/**
 * EventList model from Marvel API
 */
data class EventList(val available: Int,
                     val returned: Int,
                     val collectionURI: String,
                     val items: List<EventSummary>)

/**
 * EventSummary model from Marvel API.
 * @PrimaryKey id autoIncrement
 * @ForeignKey characterId references id from Characters table.
 */
@Entity(tableName = "events")
data class EventSummary(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val resourceURI: String,
    val name: String,
    val characterId: Int
) {
    fun getEventWithCharacterId(id: Int): EventSummary =
        EventSummary(0, resourceURI, name, id)
}