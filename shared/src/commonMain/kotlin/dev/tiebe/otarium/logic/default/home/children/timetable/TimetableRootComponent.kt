package dev.tiebe.otarium.logic.default.home.children.timetable

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.backhandler.BackCallback
import dev.tiebe.otarium.logic.default.home.children.timetable.children.timetable.DefaultTimetableComponent
import dev.tiebe.otarium.logic.root.home.children.timetable.TimetableRootComponent

class DefaultTimetableRootComponent(componentContext: ComponentContext): TimetableRootComponent, ComponentContext by componentContext {
    override val navigation = StackNavigation<TimetableRootComponent.Config>()
    override val onBack: MutableValue<BackCallback> = MutableValue(BackCallback { back() })

    override val timetableComponent = DefaultTimetableComponent(componentContext, ::navigate, onBack)

    override val childStack: Value<ChildStack<TimetableRootComponent.Config, TimetableRootComponent.Child>> =
        childStack(
            source = navigation,
            initialConfiguration = TimetableRootComponent.Config.Main,
            handleBackButton = false, // Pop the back stack on back button press
            childFactory = ::createChild,
        )

    override fun registerBackHandler() {
        backHandler.register(onBack.value)
    }

    override fun unregisterBackHandler() {
        backHandler.unregister(onBack.value)
    }

    private fun createChild(config: TimetableRootComponent.Config, @Suppress("UNUSED_PARAMETER") componentContext: ComponentContext): TimetableRootComponent.Child =
        when (config) {
            is TimetableRootComponent.Config.Main -> TimetableRootComponent.Child.TimetableChild(timetableComponent)
            is TimetableRootComponent.Config.TimetablePopup -> TimetableRootComponent.Child.TimetablePopupChild(
                timetableComponent,
                config.id
            )
        }

    init {
        backHandler.register(onBack.value)
    }
}