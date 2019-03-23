package dev.carrion.marvelheroes.data

import dev.carrion.marvelheroes.models.*
import dev.carrion.marvelheroes.toCharacterDatabaseList
import org.junit.Test

class ExtensionsTest {

    @Test
    fun `toCharacterDatabaseList extension receives list of type Character and returns list CharacterDatabase`() {
        // Create list type Character
        val character1 = createTestCharacter(id = 1)
        val character2 = createTestCharacter(id = 100)
        val characterList: List<Character> = listOf(character1,character2)

        // Create list type CharactetDatabase
        val characterDatabase1 = createTestCharacterDatabase(id = 1)
        val characterDatabase2 = createTestCharacterDatabase(id = 100)
        val characterDatabaseList: List<CharacterDatabase> = listOf(characterDatabase1, characterDatabase2)

        // Assert extension function returns CharacterDatabase list
        assert(characterDatabaseList == characterList.toCharacterDatabaseList())
    }

    /**
     * Methods to create Test data.
     */
    private fun createTestCharacterDatabase(
        id: Int = 1,
        name: String = "Test",
        description: String = "Test desc",
        modified: String = "Modified test",
        resourceURI: String = "Resource Uri test",
        thumbnail: Image = Image("testpath.jpg", "")
    ) = CharacterDatabase(
        id, name, description, modified, resourceURI, thumbnail
    )

    private fun createTestCharacter(
        id: Int = 1,
        name: String = "Test",
        description: String = "Test desc",
        modified: String = "Modified test",
        resourceURI: String = "Resource Uri test",
        urls: List<Url>? = emptyList(),
        thumbnail: Image = Image("testpath.jpg", ""),
        comics: ComicList = ComicList(1,1,"", emptyList()),
        stories: StoriesList = StoriesList(1, 1, "", emptyList()),
        events: EventList = EventList(1, 1, "", emptyList()),
        series: SeriesList = SeriesList(1, 1, "", emptyList())
    ) = Character(
        id, name, description, modified, resourceURI, urls, thumbnail, comics, stories, events, series
    )

}