package dev.jakal.codechallenge

import dev.jakal.codechallenge.common.extensions.humanize
import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockkStatic
import org.threeten.bp.LocalDateTime

class DateTimeExtensionsSpec : FreeSpec({

    val now = LocalDateTime.of(2021, 12, 4, 10, 30)

    beforeSpec {
        mockkStatic(LocalDateTime::class)
        every { LocalDateTime.now() } returns now
    }

    "LocalDateTime.humanize() extension function" - {

        listOf(
            Pair(now, "Today, 10:30"),
            Pair(now.plusHours(13), "Today, 23:30"),
            Pair(now.plusHours(14), "Tomorrow, 0:30"),
            Pair(now.plusDays(1), "Tomorrow, 10:30"),
            Pair(now.plusDays(2), "In two days"),
            Pair(now.plusDays(6), "In six days"),
            Pair(now.plusDays(7), "11.12.2021"),
            Pair(now.plusDays(10), "14.12.2021"),
            Pair(now.plusDays(31), "04.01.2022"),
            Pair(now.minusDays(1), "Yesterday, 10:30"),
            Pair(now.minusDays(2), "Two days ago"),
            Pair(now.minusDays(91), "04.09.2021"),
            Pair(now.minusDays(365), "04.12.2020")
        ).forEach { (localDateTime, expected) ->
            "should return $expected for LocalDateTime=$localDateTime while now is $now" {
                localDateTime.humanize() shouldBe expected
            }
        }
    }
})
