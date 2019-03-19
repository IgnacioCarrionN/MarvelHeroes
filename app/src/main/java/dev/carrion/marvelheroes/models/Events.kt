package dev.carrion.marvelheroes.models

data class EventList(val available: Int,
                     val returned: Int,
                     val collectionURI: String,
                     val items: List<EventSummary>)

data class EventSummary(val resourceURI: String, val name: String)