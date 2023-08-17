package nl.tiebe.otarium.logic.data

import com.russhwolf.settings.Settings
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import nl.tiebe.otarium.logic.data.wrapper.Account
import nl.tiebe.otarium.logic.home.children.grades.children.calculation.GradeCalculationChildComponent
import nl.tiebe.otarium.logic.home.children.settings.children.ui.children.colors.CustomTheme
import nl.tiebe.otarium.logic.home.children.settings.children.ui.children.colors.defaultDarkTheme
import nl.tiebe.otarium.logic.home.children.settings.children.ui.children.colors.defaultLightTheme

object Data {
    val settings = Settings()

    var finishedOnboarding: Boolean
        get() = settings.getBoolean("finished_onboarding", false)
        set(value) = settings.putBoolean("finished_onboarding", value)

    var storeLoginBypass: Boolean
        get() = settings.getBoolean("bypass", false)
        set(value) = settings.putBoolean("bypass", value)

    var showAds: Boolean
        get() = settings.getBoolean("show_ads", false)
        set(value) = settings.putBoolean("show_ads", value)

    var ageOfConsent: Boolean
        get() = settings.getBoolean("age_of_consent", false)
        set(value) = settings.putBoolean("age_of_consent", value)

    var accounts: List<Account>
        get() = settings.getString("accounts", "[]").let {
            Json.decodeFromString(it) }
        set(value) = settings.putString("accounts", Json.encodeToString(value))

    var selectedAccount: Account
        get() = accounts.find { it.id == settings.getInt("selected_account", -1) } ?: accounts.firstOrNull() ?: run { throw RuntimeException("No accounts found") }
        set(value) = settings.putInt("selected_account", value.id)

    var decimals: Int
        get() = settings.getInt("decimals", 2)
        set(value) = settings.putInt("decimals", value)

    var customThemeEnabled: Boolean
        get() = settings.getBoolean("custom_theme", false)
        set(value) = settings.putBoolean("custom_theme", value)

    var customLightTheme: CustomTheme
        get() = settings.getStringOrNull("custom_light_theme")?.let {
            Json.decodeFromString(it) } ?: defaultLightTheme.value
        set(value) = settings.putString("custom_light_theme", Json.encodeToString(value))

    var customDarkTheme: CustomTheme
        get() = settings.getStringOrNull("custom_dark_theme")?.let {
            Json.decodeFromString(it) } ?: defaultDarkTheme.value
        set(value) = settings.putString("custom_dark_theme", Json.encodeToString(value))

    var dynamicTheme: Boolean
        get() = settings.getBoolean("dynamic_theme", false)
        set(value) = settings.putBoolean("dynamic_theme", value)

    var showCancelledLessons: Boolean
        get() = settings.getBoolean("show_cancelled_lessons", false)
        set(value) = settings.putBoolean("show_cancelled_lessons", value)

    var manualGrades: List<GradeCalculationChildComponent.ManualGrade>
        get() = settings.getString("manual_grades", "[]").let {
            Json.decodeFromString(it) }
        set(value) = settings.putString("manual_grades", Json.encodeToString(value))

    var markGrades: Boolean
        get() = settings.getBoolean("mark_grades", false)
        set(value) = settings.putBoolean("mark_grades", value)

    var passingGrade: Float
        get() = settings.getFloat("passing_grade", 5.5f)
        set(value) = settings.putFloat("passing_grade", value)

    var debugNotifications: Boolean
        get() = settings.getBoolean("debug_notifications", false)
        set(value) = settings.putBoolean("debug_notifications", value)

}