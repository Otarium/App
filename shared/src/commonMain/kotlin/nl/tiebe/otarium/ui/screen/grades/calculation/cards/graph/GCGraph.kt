package nl.tiebe.otarium.ui.screen.grades.calculation.cards.graph

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import nl.tiebe.otarium.MR
import nl.tiebe.otarium.ui.screen.grades.calculation.cards.calculateAverage
import nl.tiebe.otarium.utils.getLocalizedString
import nl.tiebe.otarium.utils.server.ServerGrade

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GCGraph(grades: List<ServerGrade>) {
    ElevatedCard(
        modifier = Modifier.padding(10.dp),
    ) {
        Text(
            text = getLocalizedString(MR.strings.graph),
            style = MaterialTheme.typography.labelLarge,
            modifier = Modifier.padding(top = 8.dp).fillMaxWidth(),
            textAlign = TextAlign.Center
        )
        val textColor = MaterialTheme.colorScheme.onBackground
        val lineColor = MaterialTheme.colorScheme.primary
        val averageColor = MaterialTheme.colorScheme.secondary
        val axisLineColor = MaterialTheme.colorScheme.outline

        val lineBound = remember { mutableStateOf(1F) }

        val gradeText = getLocalizedString(MR.strings.grade)
        val averageText = getLocalizedString(MR.strings.average)

        Canvas(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
                .padding(top = 20.dp, start = 30.dp)
                .drawBehind { drawAxis(lineColor = axisLineColor, textColor = textColor) }
                .padding(horizontal = 4.dp)

        ) {
            lineBound.value = size.width / grades.count() * 0.8f

            val gradeBrush = Brush.linearGradient(listOf(lineColor, lineColor))
            val gradePath = Path().apply { moveTo(0f, size.height) }

            grades.forEachIndexed { index, gradeInfo ->
                val grade = gradeInfo.grade.grade?.replace(',', '.')?.toFloatOrNull() ?: 0f

                val offset = Offset((2*index+1) * lineBound.value * 0.6f, size.height - grade * (size.height / 10))
                if (grades.size > 1) {
                    when (index) {
                        0 -> gradePath.moveTo(offset.x, offset.y)
                        else -> gradePath.lineTo(offset.x, offset.y)
                    }
                }
                drawCircle(
                    center = offset,
                    radius = size.width / 70,
                    brush = gradeBrush
                )
            }
            if (grades.size > 1) {
                drawPath(
                    path = gradePath,
                    brush = gradeBrush,
                    style = Stroke(width = size.width / 100),
                )
            }

            val averageBrush = Brush.linearGradient(listOf(averageColor, averageColor))
            val averagePath = Path().apply { moveTo(0f, size.height) }

            grades.forEachIndexed { index, _ ->
                val average = calculateAverage(grades.subList(0, index+1))

                val offset = Offset((2*index+1) * lineBound.value * 0.6f, size.height - average * (size.height / 10))
                if (grades.size > 1) {
                    when (index) {
                        0 -> averagePath.moveTo(offset.x, offset.y)
                        else -> averagePath.lineTo(offset.x, offset.y)
                    }
                }
                drawCircle(
                    center = offset,
                    radius = size.width / 70,
                    brush = averageBrush
                )
            }

            if (grades.size > 1) {
                drawPath(
                    path = averagePath,
                    brush = averageBrush,
                    style = Stroke(width = size.width / 100),
                )
            }

            drawCircle(
                center = Offset(size.width - 200,size.height - 100),
                brush = gradeBrush,
                radius = size.width / 70
            )

            drawCircle(
                center = Offset(size.width - 200,size.height - 50),
                brush = averageBrush,
                radius = size.width / 70
            )

/*            drawIntoCanvas {
                it.nativeCanvas.apply {
                    drawTextLine(
                        TextLine.Companion.make(gradeText, Font()),
                        size.width - 135,
                        size.height - 89,
                        Paint().apply {
                            color = textColor.toArgb()
                        }
                    )

                    drawTextLine(
                        TextLine.Companion.make(averageText, Font()),
                        size.width - 118,
                        size.height - 39,
                        Paint().apply {
                            color = textColor.toArgb()
                        }
                    )
                }
            }*/
        }
    }
}