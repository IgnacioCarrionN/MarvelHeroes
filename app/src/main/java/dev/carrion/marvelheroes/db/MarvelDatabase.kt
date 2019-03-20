package dev.carrion.marvelheroes.db

import android.content.Context
import androidx.room.*
import dev.carrion.marvelheroes.models.CharacterDatabase
import dev.carrion.marvelheroes.models.ComicSummary
import dev.carrion.marvelheroes.models.EventSummary

@Database(entities = [CharacterDatabase::class, ComicSummary::class, EventSummary::class], version = 4, exportSchema = false)
@TypeConverters(Converters::class)
abstract class MarvelDatabase  : RoomDatabase(){

    abstract fun marvelDao(): MarvelDao


    companion object {

        @Volatile
        private var INSTANCE: MarvelDatabase? = null

        fun getInstance(context: Context): MarvelDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE
                    ?: buildDatabase(context).also { INSTANCE = it }
            }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(context.applicationContext, MarvelDatabase::class.java, "Marvel.db")
                .fallbackToDestructiveMigration()
                .build()
    }
}