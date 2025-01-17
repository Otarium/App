package nl.tiebe.otarium

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import nl.tiebe.otarium.magister.MagisterAccount
import nl.tiebe.otarium.magister.ManualGrade
import nl.tiebe.otarium.ui.theme.CustomTheme
import nl.tiebe.otarium.ui.theme.defaultDarkTheme
import nl.tiebe.otarium.ui.theme.defaultLightTheme

object Data {
    var finishedOnboarding: Boolean
        get() = settings.getBoolean("finished_onboarding", false)
        set(value) = settings.putBoolean("finished_onboarding", value)

    var storeLoginBypass: Boolean
        get() = settings.getBoolean("bypass", false)
        set(value) = settings.putBoolean("bypass", value)

    var accounts: List<MagisterAccount>
        get() = settings.getString("accounts", "[]").let {
            Json.decodeFromString(it) }
        set(value) = settings.putString("accounts", Json.encodeToString(value))

    var selectedAccount: MagisterAccount
        get() = accounts.find { it.accountId == settings.getInt("selected_account", -1) } ?: accounts.firstOrNull() ?: run { throw RuntimeException("No accounts found") }
        set(value) = settings.putInt("selected_account", value.accountId)

    var decimals: Int
        get() = settings.getInt("decimals", 2)
        set(value) = settings.putInt("decimals", value)

    var customThemeEnabled: Boolean
        get() = settings.getBoolean("custom_theme", false)
        set(value) = settings.putBoolean("custom_theme", value)

    var customLightTheme: CustomTheme
        get() = settings.getStringOrNull("custom_light_theme")?.let {
            Json.decodeFromString(it) } ?: defaultLightTheme
        set(value) = settings.putString("custom_light_theme", Json.encodeToString(value))

    var customDarkTheme: CustomTheme
        get() = settings.getStringOrNull("custom_dark_theme")?.let {
            Json.decodeFromString(it) } ?: defaultDarkTheme
        set(value) = settings.putString("custom_dark_theme", Json.encodeToString(value))

    var dynamicTheme: Boolean
        get() = settings.getBoolean("dynamic_theme", false)
        set(value) = settings.putBoolean("dynamic_theme", value)

    var showCancelledLessons: Boolean
        get() = settings.getBoolean("show_cancelled_lessons", false)
        set(value) = settings.putBoolean("show_cancelled_lessons", value)

    var manualGrades: List<ManualGrade>
        get() = settings.getString("manual_grades", "[]").let {
            Json.decodeFromString(it) }
        set(value) = settings.putString("manual_grades", Json.encodeToString(value))

    var markGrades: Boolean
        get() = settings.getBoolean("mark_grades", false)
        set(value) = settings.putBoolean("mark_grades", value)

    var showWeekend: Boolean
        get() = settings.getBoolean("show_weekend", true)
        set(value) = settings.putBoolean("show_weekend", value)

    var timetableContrast: Boolean
        get() = settings.getBoolean("timetable_contrast", false)
        set(value) = settings.putBoolean("timetable_contrast", value)

    var passingGrade: Float
        get() = settings.getFloat("passing_grade", 5.5f)
        set(value) = settings.putFloat("passing_grade", value)

    var timetableRounding: Int
        get() = settings.getInt("timetable_rounding", 0)
        set(value) = settings.putInt("timetable_rounding", value)

    var debugNotifications: Boolean
        get() = settings.getBoolean("debug_notifications", false)
        set(value) = settings.putBoolean("debug_notifications", value)

    var cardList: Boolean
        get() = settings.getBoolean("card_list", true)
        set(value) = settings.putBoolean("card_list", value)

}
