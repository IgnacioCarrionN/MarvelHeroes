package dev.carrion.marvelheroes.data

import dev.carrion.marvelheroes.data.db.MarvelDao
import dev.carrion.marvelheroes.di.MarvelModule
import dev.carrion.marvelheroes.models.*
import org.junit.Before
import org.junit.Test
import org.koin.dsl.module.applicationContext
import org.koin.standalone.StandAloneContext.loadKoinModules
import org.koin.standalone.StandAloneContext.startKoin
import org.koin.standalone.get
import org.koin.standalone.setProperty

import org.koin.test.KoinTest
import org.koin.test.declareMock

class MarvelCallbackTest : KoinTest {

    @Before
    fun `set before test`(){
        startKoin(listOf(MarvelModule))
        val daoMock = declareMock<MarvelDao>()
        loadKoinModules(applicationContext {
            single(override = true) { daoMock }
        })
    }


    @Test
    fun `characterListToDatabaseList method gets list of type Character and returns list CharacterDatabase`() {
        setProperty("name", "Test")
        val marvelCallback: MarvelCallback = get()
        // Create list type Character
        val characterList = mutableListOf<Character>()
        val character1 = Character(
            0,
            "Test",
            "Test desc",
            "12",
            "12",
            emptyList(),
            Image("test","jpg"),
            ComicList(0,0,"", emptyList()),
            StoriesList(0, 0, "", emptyList()),
            EventList(0, 0, "", emptyList()),
            SeriesList(0, 0, "", emptyList())
        )
        characterList.add(character1)

        // Create list type CharactetDatabase
        val characterDatabaseList = mutableListOf<CharacterDatabase>()
        val characterDatabase1 = CharacterDatabase(
            0,
            "Test",
            "Test desc",
            "12",
            "12",
            Image("test", "jpg"))

        characterDatabaseList.add(characterDatabase1)

        // Assert method returns CharacterDatabase list
        assert(characterDatabaseList == marvelCallback.characterListToDatabaseListTesting(characterList))
    }
}