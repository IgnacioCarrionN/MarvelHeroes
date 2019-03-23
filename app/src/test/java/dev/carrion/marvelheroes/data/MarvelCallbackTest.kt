package dev.carrion.marvelheroes.data

import dev.carrion.marvelheroes.data.db.MarvelDao
import dev.carrion.marvelheroes.di.MarvelModule
import org.junit.Before
import org.koin.dsl.module.applicationContext
import org.koin.standalone.StandAloneContext.loadKoinModules
import org.koin.standalone.StandAloneContext.startKoin

import org.koin.test.KoinTest
import org.koin.test.declareMock


/**
 * This test does nothing right now.
 * I just left the code to give an example about how to initialize Koin library for tests.
 *
 */
class MarvelCallbackTest : KoinTest {


    /**
     * This method is launched before each test.
     * It starts Koin, declare a mock and overrides MarvelDao declaration in Koin MarvelModule.
     */
    @Before
    fun `set before test`(){
        startKoin(listOf(MarvelModule))
        val daoMock = declareMock<MarvelDao>()
        loadKoinModules(applicationContext {
            single(override = true) { daoMock }
        })
    }

}