package dev.tiebe.otarium.logic.default.home.children.elo

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import dev.tiebe.otarium.logic.default.home.children.elo.children.assignments.DefaultAssignmentsChildComponent
import dev.tiebe.otarium.logic.default.home.children.elo.children.learningresources.DefaultLearningResourcesChildComponent
import dev.tiebe.otarium.logic.default.home.children.elo.children.studyguides.DefaultStudyGuidesChildComponent
import dev.tiebe.otarium.logic.root.home.children.elo.ELOComponent
import dev.tiebe.otarium.logic.root.home.children.elo.children.assignments.AssignmentsChildComponent
import dev.tiebe.otarium.logic.root.home.children.elo.children.learningresources.LearningResourcesChildComponent
import dev.tiebe.otarium.logic.root.home.children.elo.children.studyguides.StudyGuidesChildComponent

class DefaultELOComponent(
    componentContext: ComponentContext
): ELOComponent, ComponentContext by componentContext {
    private fun studyGuidesComponent(componentContext: ComponentContext) =
        DefaultStudyGuidesChildComponent(
            componentContext = componentContext
        )

    private fun assignmentsComponent(componentContext: ComponentContext) =
        DefaultAssignmentsChildComponent(
            componentContext = componentContext
        )

    private fun learningResourcesComponent(componentContext: ComponentContext) =
        DefaultLearningResourcesChildComponent(
            componentContext = componentContext
        )

    override val studyGuidesComponent: StudyGuidesChildComponent = studyGuidesComponent(componentContext)
    override val assignmentsComponent: AssignmentsChildComponent = assignmentsComponent(componentContext)
    override val learningResourcesComponent: LearningResourcesChildComponent = learningResourcesComponent(componentContext)

    override val currentPage: MutableValue<Int> = MutableValue(0)


    override fun changeChild(eloChild: ELOComponent.ELOChild) {
        currentPage.value = eloChild.id
    }

}

interface ELOChildComponent