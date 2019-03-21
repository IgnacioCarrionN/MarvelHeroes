package dev.carrion.marvelheroes.models

/**
 * File keeps series related models on the same place.
 * This model currently is not used to show data.
 * But we can implement this feature in the future with little effort.
 */

/**
 * SeriesList model from Marvel API.
 */

data class SeriesList(val available: Int,
                      val returned: Int,
                      val collectionURI: String,
                      val items: List<SeriesSummary>)

/**
 * SeriesSummary model from Marvel API.
 */
data class SeriesSummary(val resourceURI: String, val name: String)