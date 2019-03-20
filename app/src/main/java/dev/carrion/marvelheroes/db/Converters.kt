package dev.carrion.marvelheroes.db

import androidx.room.TypeConverter
import dev.carrion.marvelheroes.models.Image

class Converters {

    @TypeConverter
    fun fromImage(value: Image): String {
        return "${value.path}.${value.extension}"
    }

    @TypeConverter
    fun toImage(value: String): Image {
        return Image(value, "")
    }

}