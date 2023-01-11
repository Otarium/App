package nl.tiebe.otarium.ui.screen.grades.calculation.cards

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import nl.tiebe.otarium.MR
import nl.tiebe.otarium.utils.ui.format
import nl.tiebe.otarium.utils.ui.getLocalizedString
import nl.tiebe.otarium.utils.server.ServerGrade

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun GCAverageCalculator(grades: List<ServerGrade>) {
    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
    ) {
        Text(
            text = getLocalizedString(MR.strings.calculation),
            style = MaterialTheme.typography.labelLarge,
            modifier = Modifier.padding(top = 8.dp).fillMaxWidth(),
            textAlign = TextAlign.Center
        )

        var type by remember { mutableStateOf(1) }

        Row(modifier = Modifier.padding(end = 20.dp).fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
            Row(Modifier
                .selectable(
                    selected = (type == 1),
                    onClick = {
                        type = 1
                    }),
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(selected = type == 1, onClick = { type = 1 })
                Text(getLocalizedString(MR.strings.calculate_new_average))
            }

            Row(Modifier
                .selectable(
                    selected = (type == 2),
                    onClick = {
                        type = 2
                    }),
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(selected = type == 2, onClick = { type = 2 })
                Text(getLocalizedString(MR.strings.calculate_new_grade))

            }
        }
        Row(Modifier.padding(5.dp)) {
            var calculatedAverage by remember { mutableStateOf<Float?>(null)}

            var enteredGrade by remember { mutableStateOf("") }
            var enteredWeight by remember { mutableStateOf("") }

            Column(Modifier.padding(start = 5.dp)) {
                TextField(
                    value = enteredGrade,
                    onValueChange = { value ->
                        if ((value.replace(",", ".").replace(".", "").all { it.isDigit() } &&
                        value.replace(",", ".").toFloatOrNull() != null) || value.isBlank()) enteredGrade = value },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                    placeholder = { Text(
                        getLocalizedString(
                            if (type == 1) MR.strings.new_grade_calculation_placeholder else MR.strings.average_calculation_placeholder)
                    ) },
                    modifier = Modifier.width(200.dp)
                )

                Spacer(modifier = Modifier.padding(top = 6.dp))

                TextField(
                    value = enteredWeight,
                    onValueChange = { value ->
                        if ((value.replace(",", ".").replace(".", "").all { it.isDigit() } &&
                                value.replace(",", ".").toFloatOrNull() != null) || value.isBlank()) enteredWeight = value },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                    placeholder = { Text(getLocalizedString(MR.strings.weight_calculation_placeholder)) },
                    modifier = Modifier.width(200.dp)
                )
            }

            Column(Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
                Text(
                    text = if (calculatedAverage != null) calculatedAverage?.format(2) ?: "" else "",
                    style = MaterialTheme.typography.displayMedium
                )

                Button(modifier = Modifier.padding(top = 5.dp), onClick = {
                    calculatedAverage = if (type == 1)
                        calculateAverage(grades,
                            enteredGrade.replace(",", ".").toFloatOrNull() ?: 0f,
                            enteredWeight.replace(",", ".").toFloatOrNull() ?: 0f)
                    else {
                        calculateNewGrade(grades,
                            enteredGrade.replace(",", ".").toFloatOrNull() ?: 0f,
                            enteredWeight.replace(",", ".").toFloatOrNull() ?: 0f)
                    }
                }) {
                    Text(getLocalizedString(MR.strings.calculate))
                }
            }
        }
    }
}

fun calculateAverage(grades: List<ServerGrade>, addedGrade: Float = 0f, addedGradeWeight: Float = 0f): Float {
    var sum = addedGrade * addedGradeWeight
    var weight = addedGradeWeight

    grades.forEach {
        sum += (it.grade.grade?.replace(',', '.')?.toFloatOrNull() ?: 0f) * it.gradeInfo.weight.toFloat()
        weight += it.gradeInfo.weight.toFloat()
    }

    if (weight == 0f) return 0f

    return sum/weight
}

fun calculateNewGrade(grades: List<ServerGrade>, newAverage: Float = 10f, newGradeWeight: Float = 1f): Float {
    var sum = 0f
    var weight = newGradeWeight

    grades.forEach {
        sum += (it.grade.grade?.replace(',', '.')?.toFloatOrNull() ?: 0f) * it.gradeInfo.weight.toFloat()
        weight += it.gradeInfo.weight.toFloat()
    }

    return ((newAverage * weight) - sum) / newGradeWeight
}