package nl.tiebe.otarium.utils

import kotlinx.datetime.*
import nl.tiebe.otarium.Data
import nl.tiebe.otarium.magister.GradeWithGradeInfo
import nl.tiebe.otarium.magister.MagisterAccount
import nl.tiebe.otarium.magister.refreshGrades

suspend fun loadUser(user: MagisterAccount) {
    Data.selectedAccount = user

    user.refreshFolders()
    user.refreshGrades { _, _ ->}
}

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


fun calculateAverageGrade(grades: List<GradeWithGradeInfo>, addedGrade: Float = 0f, addedGradeWeight: Float = 0f): Float {
    return calculateAverage(grades.map {
        (it.grade.grade?.replace(',', '.')?.toFloatOrNull() ?: 0f) to it.gradeInfo.weight.toFloat()
    }, addedGrade, addedGradeWeight)
}

fun calculateAverage(pairs: List<Pair<Float, Float>>, initialAverage: Float = 0f, initialWeight: Float = 0f): Float {
    var sum = initialAverage * initialWeight
    var weight = initialWeight

    pairs.forEach {
        sum += it.first * it.second
        weight += it.second
    }

    if (weight == 0f) return 0f

    return sum/weight
}

fun calculateNewGrade(grades: List<GradeWithGradeInfo>, newAverage: Float = 10f, newGradeWeight: Float = 1f): Float {
    return calculateNew(grades.map {
        (it.grade.grade?.replace(',', '.')?.toFloatOrNull() ?: 0f) to it.gradeInfo.weight.toFloat()
    }, newAverage, newGradeWeight)
}

fun calculateNew(pairs: List<Pair<Float, Float>>, newAverage: Float = 10f, newGradeWeight: Float = 1f): Float {
    var sum = 0f
    var weight = newGradeWeight

    pairs.forEach {
        sum += it.first * it.second
        weight += it.second
    }

    return ((newAverage * weight) - sum) / newGradeWeight
}

fun runCatchingLogError(block: () -> Unit) {
    runCatching {
        block()
    }.onFailure {
        it.printStackTrace()
    }
}
