package dev.tiebe.otarium.ui.home.debug

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Share
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import dev.tiebe.otarium.Data
import dev.tiebe.otarium.logic.home.children.debug.DebugComponent
import dev.tiebe.otarium.magister.refreshGrades
import dev.tiebe.otarium.setupNotifications
import dev.tiebe.otarium.ui.home.settings.utils.SettingRowIconButton
import dev.tiebe.otarium.ui.home.settings.utils.SettingsRowToggle
import dev.tiebe.otarium.utils.OtariumIcons
import dev.tiebe.otarium.utils.getClipboardText
import dev.tiebe.otarium.utils.otariumicons.BugOutline
import dev.tiebe.otarium.utils.refreshGradesBackground
import dev.tiebe.otarium.utils.sendNotification
import kotlinx.coroutines.launch
import kotlin.random.Random

@Composable
internal fun DebugScreen(component: DebugComponent) {
    Column(modifier = Modifier.padding(horizontal = 8.dp).verticalScroll(rememberScrollState())) {
        SettingRowIconButton(
            leftText = AnnotatedString("Ask notification permission"),
            icon = Icons.Default.Notifications,
            rowClickable = true,
        ) {
            setupNotifications()
        }

        SettingRowIconButton(
            leftText = AnnotatedString("Send test notification"),
            icon = Icons.Default.Notifications,
            rowClickable = true,
        ) {
            sendNotification("Test notification", "This is a test notification")
        }

        SettingRowIconButton(
            leftText = AnnotatedString("Remove random grade"),
            icon = Icons.Default.Delete,
            rowClickable = true,
        ) {
            val account = Data.selectedAccount
            val savedGrades = account.fullGradeList.toMutableList()

            savedGrades.removeAt(Random.nextInt(savedGrades.size))
            account.fullGradeList = savedGrades
        }

        SettingRowIconButton(
            leftText = AnnotatedString("Check grades"),
            icon = Icons.Default.Refresh,
            rowClickable = true,
        ) {
            component.scope.launch {
                Data.selectedAccount.refreshGrades()
            }
        }

        SettingRowIconButton(
            leftText = AnnotatedString("Check grades background"),
            icon = Icons.Default.Refresh,
            rowClickable = true,
        ) {
            component.scope.launch {
                refreshGradesBackground()
            }
        }

        SettingRowIconButton(
            leftText = AnnotatedString("Export accounts"),
            icon = Icons.Default.Share,
            rowClickable = true,
        ) {
            component.exportAccounts()
        }


        SettingRowIconButton(
            leftText = AnnotatedString("Import accounts"),
            icon = Icons.Default.Add,
            rowClickable = true,
        ) {
            component.importAccounts(getClipboardText())
        }

        SettingRowIconButton(
            leftText = AnnotatedString("Change language"),
            icon = Icons.Default.KeyboardArrowRight,
            rowClickable = true,
        ) {
            component.changeLanguage()
        }

        SettingRowIconButton(
            leftText = AnnotatedString("Test crash"),
            icon = OtariumIcons.BugOutline,
            rowClickable = true,
        ) {
            throw RuntimeException("Test crash")
        }

        SettingRowIconButton(
            leftText = AnnotatedString("Refresh tokens"),
            icon = Icons.Default.Refresh,
            rowClickable = true,
        ) {
            component.scope.launch {
                for (account in Data.accounts) {
                    account.refreshTokens()
                }
            }
        }

        val checked = remember { mutableStateOf(Data.debugNotifications) }

        SettingsRowToggle(
            leftText = AnnotatedString("Debug notifications"),
            rowClickable = true,
            checked = checked.value,
        ) {
            checked.value = it
            Data.debugNotifications = it
        }
    }

}