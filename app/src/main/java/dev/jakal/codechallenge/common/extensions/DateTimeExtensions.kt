package dev.jakal.codechallenge.common.extensions

import org.threeten.bp.Instant
import org.threeten.bp.LocalDateTime
import org.threeten.bp.ZoneId
import org.threeten.bp.ZonedDateTime
import org.threeten.bp.format.DateTimeFormatter
import org.threeten.bp.temporal.ChronoUnit
import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.absoluteValue

private const val AND_STRING = "and"
private const val SPACE_STRING = " "
private const val ONE_DAY = 1L
private const val ONE_WEEK = 7L
private const val TIME_PATTERN = "H:mm"
private const val DATE_PATTERN = "dd.MM.yyyy"
private val timeFormatter = DateTimeFormatter.ofPattern(TIME_PATTERN)
private val dateFormatter = DateTimeFormatter.ofPattern(DATE_PATTERN)
private val unitsMap = listOf(
    "zero",
    "one",
    "two",
    "three",
    "four",
    "five",
    "six",
    "seven",
    "eight",
    "nine",
    "ten",
    "eleven",
    "twelve",
    "thirteen",
    "fourteen",
    "fifteen",
    "sixteen",
    "seventeen",
    "eighteen",
    "nineteen"
)
private val tensMap = listOf(
    "zero",
    "ten",
    "twenty",
    "thirty",
    "forty",
    "fifty",
    "sixty",
    "seventy",
    "eighty",
    "ninety"
)

@Suppress("MagicNumbers")
private val orderMap = mapOf(
    1000000000 to "billion",
    1000000 to "million",
    1000 to "thousand",
    100 to "hundred"
)

fun String.toZonedDateTime(): ZonedDateTime = Instant.parse(this).atZone(ZoneId.of("UTC"))

fun ZonedDateTime.formatToIso(): String = format(DateTimeFormatter.ISO_INSTANT)

fun LocalDateTime.formatToTime(): String = format(timeFormatter)

fun LocalDateTime.formatToDate(): String = format(dateFormatter)

fun LocalDateTime.humanize(): String {
    val now = LocalDateTime.now()
    val days = ChronoUnit.DAYS.between(now.toLocalDate(), this.toLocalDate())
    return when {
        days.absoluteValue < ONE_DAY -> "Today, ${formatToTime()}"
        days == ONE_DAY -> "Tomorrow, ${formatToTime()}"
        days == -ONE_DAY -> "Yesterday, ${formatToTime()}"
        days.absoluteValue >= ONE_WEEK -> formatToDate()
        days < -ONE_DAY -> "${days.absoluteValue.humanize().capitalize()} days ago"
        else -> "In ${days.absoluteValue.humanize()} days"
    }
}

@Suppress("ReturnCount")
private fun Long.humanize(): String {
    if (this == 0L) return unitsMap[0]
    if (this < 0) return "minus ${(this * -1).humanize()}"

    var number = this
    val parts = ArrayList<String>()
    orderMap.forEach { (order, orderString) ->
        if ((number / order) > 0) {
            parts.add("${(number / order).humanize()} $orderString")
            number %= order
        }
    }

    if (number > 0) {
        if (parts.count() != 0) parts.add(AND_STRING)

        if (number < 20) {
            parts.add(unitsMap[number.toInt()])
        } else {
            var lastPart = tensMap[number.toInt() / 10]
            if ((number % 10) > 0) lastPart += "-${unitsMap[number.toInt() % 10]}"
            parts.add(lastPart)
        }
    }

    return parts.joinToString(SPACE_STRING).trimStart()
}

private fun String.capitalize(): String =
    replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }
