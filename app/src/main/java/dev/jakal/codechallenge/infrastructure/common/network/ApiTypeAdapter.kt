package dev.jakal.codechallenge.infrastructure.common.network

import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import dagger.Reusable
import dev.jakal.codechallenge.common.extensions.formatToIso
import dev.jakal.codechallenge.common.extensions.toZonedDateTime
import org.threeten.bp.ZonedDateTime
import javax.inject.Inject

@Reusable
class ApiTypeAdapter @Inject constructor() {

    @FromJson
    fun toZonedDateTime(value: String): ZonedDateTime = value.toZonedDateTime()

    @ToJson
    fun fromZonedDateTime(date: ZonedDateTime?): String? = date?.formatToIso()
}
