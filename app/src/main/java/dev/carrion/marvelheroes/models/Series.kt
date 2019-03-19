package dev.carrion.marvelheroes.models

data class SeriesList(val available: Int,
                      val returned: Int,
                      val collectionURI: String,
                      val items: List<SeriesSummary>)

data class SeriesSummary(val resourceURI: String, val name: String)