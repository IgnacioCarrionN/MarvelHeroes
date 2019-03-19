package dev.carrion.marvelheroes.models

data class ComicList(val available: Int, val returned: Int, val collectionUri: String, val items: List<ComicSummary>)

data class ComicSummary(val resourceUri: String, val name: String)