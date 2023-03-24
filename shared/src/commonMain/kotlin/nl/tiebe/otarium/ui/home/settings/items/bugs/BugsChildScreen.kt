package nl.tiebe.otarium.ui.home.settings.items.bugs

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import nl.tiebe.otarium.Data
import nl.tiebe.otarium.MR
import nl.tiebe.otarium.utils.ui.getLocalizedString
import kotlin.random.Random

@Composable
internal fun BugsChildScreen(component: BugsChildComponent) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp))
    {
        Text(
            text = getLocalizedString(MR.strings.bug_text_1),
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Divider()

        Row(modifier = Modifier
            .fillMaxWidth(0.95f)
            .height(70.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = "Remove random grade",
                textAlign = TextAlign.Center)
            Button(
                onClick = {
                    val account = Data.selectedAccount
                    val savedGrades = account.fullGradeList.toMutableList()

                    savedGrades.removeAt(Random.nextInt(savedGrades.size))
                    account.fullGradeList = savedGrades

                }) {
                Text("remove")
            }
        }

        Divider()

        Text(
            text = getLocalizedString(MR.strings.bug_text_2),
            modifier = Modifier.padding(vertical = 16.dp))
    }
}