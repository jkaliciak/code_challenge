package dev.jakal.codechallenge.infrastructure.common.database

import androidx.room.TypeConverter
import dev.jakal.codechallenge.common.extensions.formatToIso
import dev.jakal.codechallenge.common.extensions.toZonedDateTime
import org.threeten.bp.ZonedDateTime

object DatabaseTypeConverters {

    @TypeConverter
    @JvmStatic
    fun toZonedDateTime(value: String?): ZonedDateTime? = value?.toZonedDateTime()

    @TypeConverter
    @JvmStatic
    fun fromZonedDateTime(date: ZonedDateTime?): String? = date?.formatToIso()
}
