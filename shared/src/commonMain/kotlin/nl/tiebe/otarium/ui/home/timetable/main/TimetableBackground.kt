package nl.tiebe.otarium.ui.home.timetable.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
internal fun TimetableBackground() {
    Box(Modifier.fillMaxSize()) {
        Column {
            for (i in timesShown) {
                Text(
                    text = i.toString(),
                    Modifier
                        .size(40.dp, dpPerHour)
                        .padding(8.dp),
                    MaterialTheme.colorScheme.outline
                )
            }
        }

        for (i in timesShown) {
            HorizontalDivider(Modifier.fillMaxWidth().padding(top = dpPerHour * (i - timesShown.first)))
        }

        Box(
            modifier = Modifier
                .padding(start = 39.dp)
                .height(dpPerHour * (timesShown.last - timesShown.first + 1))
                .width(1.dp)
                .background(MaterialTheme.colorScheme.outlineVariant)
        )
    }
}