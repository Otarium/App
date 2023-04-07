package nl.tiebe.otarium.ui.home.grades.calculation.screen

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.arkivanov.decompose.extensions.compose.jetbrains.subscribeAsState
import nl.tiebe.otarium.ui.home.grades.calculation.GradeCalculationChildComponent
import nl.tiebe.otarium.ui.home.grades.calculation.subject.GCSubjectList

@Composable
internal fun GradeCalculationChild(component: GradeCalculationChildComponent) {
    when (val state = component.state.subscribeAsState().value) {
        GradeCalculationChildComponent.State.Loading -> {
            Text("Loading")
        }
        is GradeCalculationChildComponent.State.Data -> {
            GCSubjectList(component, state.data.sortedBy { it.grade.dateEntered })
        }
        is GradeCalculationChildComponent.State.Failed -> {
            Text("Something went wrong while retrieving your grades")
        }
    }
}