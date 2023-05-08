package nl.tiebe.otarium.utils

import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime
import io.ktor.utils.io.*
import kotlinx.datetime.*

expect fun copyToClipboard(text: String)

expect fun getClipboardText(): String

expect fun openUrl(url: String)

expect fun writeFile(id: String, fileName: String, data: ByteArray)

expect fun openFileFromCache(id: String, fileName: String)

fun LocalDateTime.toFormattedString(): String {
    val dateTime = this.toInstant(TimeZone.UTC)

    return dateTime.toFormattedString()
}

fun LocalDateTime.toFormattedStringDate(): String {
    val dateTime = this.toInstant(TimeZone.UTC)

    return dateTime.toFormattedStringDate()
}

fun LocalDateTime.toFormattedStringTime(): String {
    val dateTime = this.toInstant(TimeZone.UTC)

    return dateTime.toFormattedStringTime()
}

fun Instant.toFormattedString(): String {
    return this.toFormattedStringDate() + " " + this.toFormattedStringTime()
}

fun Instant.toFormattedStringDate(): String {
    val dateTime = this.toLocalDateTime(TimeZone.of("Europe/Amsterdam"))

    return "${dateTime.dayOfMonth.toFormattedString()}-${dateTime.monthNumber.toFormattedString()}-${dateTime.year.toFormattedString()}"
}

fun Instant.toFormattedStringTime(): String {
    val dateTime = this.toLocalDateTime(TimeZone.of("Europe/Amsterdam"))

    return "${dateTime.hour.toFormattedString()}:${dateTime.minute.toFormattedString()}:${dateTime.second.toFormattedString()}"
}

fun Int.toFormattedString(): String {
    return if (this < 10) "0$this" else "$this"
}

fun largeLog(content: String) {
    if (content.length > 4000) {
        println(content.substring(0, 4000))
        largeLog(content.substring(4000))
    } else {
        println(content)
    }
}