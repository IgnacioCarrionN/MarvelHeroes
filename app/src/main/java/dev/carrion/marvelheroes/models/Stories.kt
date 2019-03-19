package dev.carrion.marvelheroes.models

data class StoriesList(val available: Int,
                       val returned: Int,
                       val collectionUri: String,
                       val items: List<StorySummary>)

data class StorySummary(val resourceURI: String,
                        val name: String,
                        val type: String)