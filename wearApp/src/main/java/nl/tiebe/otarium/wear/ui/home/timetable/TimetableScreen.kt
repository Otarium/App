package nl.tiebe.otarium.wear.ui.home.timetable

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.wear.compose.foundation.lazy.ScalingLazyColumn
import androidx.wear.compose.foundation.lazy.rememberScalingLazyListState
import androidx.wear.compose.material3.ListHeader
import androidx.wear.compose.material3.Text
import androidx.wear.compose.material3.TitleCard
import com.arkivanov.decompose.extensions.compose.jetbrains.subscribeAsState
import com.google.android.horologist.annotations.ExperimentalHorologistApi
import com.google.android.horologist.compose.rotaryinput.rotaryWithScroll
import com.mohamedrejeb.richeditor.model.rememberRichTextState
import com.mohamedrejeb.richeditor.ui.material3.RichText
import kotlinx.datetime.*
import kotlinx.datetime.TimeZone
import nl.tiebe.otarium.logic.root.home.children.timetable.children.timetable.TimetableComponent
import nl.tiebe.otarium.magister.AgendaItemWithAbsence
import nl.tiebe.otarium.utils.toFormattedStringTime
import nl.tiebe.otarium.wear.ui.utils.conditional
import java.time.format.TextStyle
import java.util.*

@OptIn(ExperimentalHorologistApi::class)
@Composable
fun TimetableScreen(component: TimetableComponent, scrollEnabled: State<Boolean>) {
    val today = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date

    component.refreshTimetable(today, today + DatePeriod(days = 31))

    val listState = rememberScalingLazyListState()

    val items = component.timetable.subscribeAsState().value.groupBy { it.start.toLocalDateTime(TimeZone.currentSystemDefault()).date }

    ScalingLazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .conditional(scrollEnabled.value) { rotaryWithScroll(scrollableState = listState) },
        state = listState
    ) {
        for ((day, timetableItems) in items) {
            item { ListHeader {
                Text(day.dayOfWeek.getDisplayName(TextStyle.FULL, Locale.getDefault()) + " ${day.dayOfMonth}-${day.monthNumber}")
            } }
            for (item in timetableItems) item { TimetableItem(item = item) { /*component.parentComponent.navigate(TimetableRootComponent.Config.TimetablePopup(item))*/ } }
        }
    }
}

@Composable
fun TimetableItem(item: AgendaItemWithAbsence, onClick: () -> Unit) {
    TitleCard(onClick = onClick, title = {
        Text(text = item.agendaItem.subjects.firstOrNull()?.name?.replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() } ?: item.agendaItem.description ?: "No description")
    }, subtitle = {
        if (item.agendaItem.content != null) {
            val state = rememberRichTextState()

            LaunchedEffect(item.agendaItem) {
                state.setHtml(item.agendaItem.content ?: "")
            }

            RichText(
                state,
                maxLines = 1
            )
        }
    }, time = { Text("${item.start.toFormattedStringTime(false)} - ${item.end.toFormattedStringTime(false)} in ${item.agendaItem.location}") })
}