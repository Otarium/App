package nl.tiebe.openbaarlyceumzeist

import com.russhwolf.settings.Settings

val settings: Settings = Settings()

class Main {

    fun setup() {
        val version = settings.getInt("version", 0)

        if (version == 0 || version < BuildKonfig.versionCode) {
            settings.putInt("version", BuildKonfig.versionCode)
        }
    }

    fun start() {
    }

}

