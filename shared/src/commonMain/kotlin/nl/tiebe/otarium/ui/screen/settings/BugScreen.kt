package nl.tiebe.otarium.ui.screen.settings

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import moe.tlaster.precompose.navigation.BackHandler
import nl.tiebe.otarium.MR
import nl.tiebe.otarium.utils.getLocalizedString

@Composable
internal fun BugScreen(onExit: () -> Unit) {
    BackHandler(onBack = onExit)

    Surface(Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
        Column(
            Modifier
                .fillMaxSize()
                .padding(16.dp))
        {
            Text(text = getLocalizedString(MR.strings.bug_text_1), Modifier.padding(bottom = 16.dp))
            Divider()
            Row(modifier = Modifier
                .fillMaxWidth(0.95f)
                .height(70.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically) {
                Text(text = "Clear cache", textAlign = TextAlign.Center)
                Button(onClick = { deleteCache() }) {
                    Text("Clear")
                }
            }
            Divider()
            Text(text = getLocalizedString(MR.strings.bug_text_2), Modifier.padding(vertical = 16.dp))
        }
    }
}

expect fun deleteCache()