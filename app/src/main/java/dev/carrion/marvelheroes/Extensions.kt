package dev.carrion.marvelheroes

import dev.carrion.marvelheroes.models.Character
import dev.carrion.marvelheroes.models.CharacterDatabase
import dev.carrion.marvelheroes.models.ComicSummary
import dev.carrion.marvelheroes.models.EventSummary
import java.security.MessageDigest

/**
 * Extensions file.
 */

/**
 * Extension function creates a MD5 digest from the receiver String
 *
 * @receiver String from witch we want a MD5 digest.
 * @return MD5 digest string.
 */
fun String.md5(): String {
    val md = MessageDigest.getInstance("MD5")
    val digest = md.digest(toByteArray())
    return digest.joinToString("") {
        String.format("%02x", it)
    }
}

/**
 * Converts from JSON Character to CharacterDatabase.
 *
 * @receiver List of Characters from JSON parse.
 * @return List of characters with DB model.
 */
fun List<Character>.toCharacterDatabaseList(): List<CharacterDatabase> {
    val charactersDatabase = mutableListOf<CharacterDatabase>()
    this.forEach {
        charactersDatabase.add(it.toCharacterDatabase())
    }
    return charactersDatabase
}

/**
 * Create new ComicSummary objects with CharacterId from Character JSON list.
 * @receiver List of ComicSummary without character ids.
 * @property characterId CharacterId to use as foreign key on DB.
 */
fun List<ComicSummary>.getComicListWithId(characterId: Int): List<ComicSummary> {
    val comicsWithId = mutableListOf<ComicSummary>()
    this.forEach {
        comicsWithId.add(it.getComicWithCharacterId(characterId))
    }
    return comicsWithId
}

/**
 * Create new EventSummary objects with CharacterId from Character JSON list.
 * @receiver List of EventSummary without character ids.
 * @property characterId CharacterId to use as foreign key on DB.
 */
fun List<EventSummary>.getEventListWithId(characterId: Int): List<EventSummary> {
    val eventsWithId = mutableListOf<EventSummary>()
    this.forEach {
        eventsWithId.add(it.getEventWithCharacterId(characterId))
    }
    return eventsWithId
}