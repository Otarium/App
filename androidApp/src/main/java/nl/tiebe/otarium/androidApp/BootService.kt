package nl.tiebe.otarium.androidApp

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent


class BootService : BroadcastReceiver() {

    @SuppressLint("UnsafeProtectedBroadcastReceiver")
    override fun onReceive(context: Context?, intent: Intent?) {
        if (context == null) return

        setupTokenBackgroundTask(context)
        setupGradesBackgroundTask(context)
        setupMessagesBackgroundTask(context)
    }
}
