package nl.tiebe.otarium.ui.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.jetbrains.subscribeAsState
import nl.tiebe.otarium.ui.home.debug.DebugComponent
import nl.tiebe.otarium.ui.home.debug.DebugScreen
import nl.tiebe.otarium.ui.home.elo.ELOComponent
import nl.tiebe.otarium.ui.home.elo.ELOScreen
import nl.tiebe.otarium.ui.home.grades.GradesComponent
import nl.tiebe.otarium.ui.home.grades.GradesScreen
import nl.tiebe.otarium.ui.home.messages.MessagesComponent
import nl.tiebe.otarium.ui.home.messages.MessagesScreen
import nl.tiebe.otarium.ui.home.settings.SettingsComponent
import nl.tiebe.otarium.ui.home.settings.SettingsScreen
import nl.tiebe.otarium.ui.home.timetable.TimetableRootComponent
import nl.tiebe.otarium.ui.home.timetable.TimetableRootScreen
import nl.tiebe.otarium.utils.ui.getLocalizedString

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun BottomBar(
    component: HomeComponent,
    modifier: Modifier,
) {
    val dialog = component.dialog.subscribeAsState()
    val overlay = dialog.value.child ?: return

    Scaffold(
        bottomBar = {
            NavigationBar(modifier = modifier, contentColor = MaterialTheme.colorScheme.onPrimary, containerColor = MaterialTheme.colorScheme.primary) {
                component.visibleItems.forEach { screen ->
                    NavigationBarItem(
                        icon = if (overlay.configuration == screen) screen.iconSelected else screen.icon,
                        label = { Text(getLocalizedString(screen.resourceId), modifier = Modifier.wrapContentWidth(unbounded = true), color = MaterialTheme.colorScheme.onPrimary) },
                        selected = overlay.configuration == screen,
                        onClick = {
                            component.navigate(screen)
                        },
                        colors = NavigationBarItemDefaults.colors(
                            indicatorColor = MaterialTheme.colorScheme.secondaryContainer,
                        )
                    )
                }
            }
        }
    ) { innerPadding ->
        Box(Modifier.fillMaxSize().padding(innerPadding)) {
            when (val dialogComponent = overlay.instance) {
                is TimetableRootComponent -> TimetableRootScreen(dialogComponent)
                is GradesComponent -> GradesScreen(dialogComponent)
                is MessagesComponent -> MessagesScreen(dialogComponent)
                is ELOComponent -> ELOScreen(dialogComponent)
                is SettingsComponent -> SettingsScreen(dialogComponent)
                is DebugComponent -> DebugScreen(dialogComponent)
            }
        }
    }
}