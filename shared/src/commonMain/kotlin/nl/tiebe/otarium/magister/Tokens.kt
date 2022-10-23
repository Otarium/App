package nl.tiebe.otarium.magister

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import nl.tiebe.otarium.settings
import nl.tiebe.otarium.utils.server.LoginResponse

object Tokens {
    fun checkTokens(): Boolean {
        if (getPastTokens() == null) return false
        

        return true
    }

    fun saveTokens(tokens: LoginResponse) {
        settings.putString("login_tokens", Json.encodeToString(tokens))
    }


    fun getPastTokens(): LoginResponse? {
        return Json.decodeFromString(settings.getStringOrNull("login_tokens") ?: return null)
    }


    fun clearTokens() {
        settings.remove("login_tokens")
    }
}