package dev.carrion.marvelheroes.models

/**
 * File keeps series related models on the same place.
 * This model currently is not used to show data.
 * But we can implement this feature in the future with little effort.
 */

/**
 * StoriesList model from Marvel API.
 */
data class StoriesList(val available: Int,
                       val returned: Int,
                       val collectionUri: String,
                       val items: List<StorySummary>)

/**
 * StorySummary model from Marvel API.
 */
data class StorySummary(val resourceURI: String,
                        val name: String,
                        val type: String)