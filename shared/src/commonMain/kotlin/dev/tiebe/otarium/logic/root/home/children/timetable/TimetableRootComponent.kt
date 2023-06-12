package dev.tiebe.otarium.logic.root.home.children.timetable

import com.arkivanov.decompose.router.stack.*
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.backhandler.BackCallback
import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
import dev.tiebe.otarium.logic.root.home.children.timetable.children.timetable.TimetableComponent

interface TimetableRootComponent : _root_ide_package_.dev.tiebe.otarium.logic.default.home.MenuItemComponent {
    val navigation: StackNavigation<Config>
    val childStack: Value<ChildStack<Config, Child>>

    val onBack: MutableValue<BackCallback>

    fun registerBackHandler()
    fun unregisterBackHandler()

    fun navigate(child: Config) {
        navigation.push(child)
    }

    fun back() {
        navigation.pop()
    }

    sealed class Child {
        class TimetableChild(val component: TimetableComponent) : Child()
        class TimetablePopupChild(val component: TimetableComponent, val id: Int) : Child()
    }

    sealed class Config : Parcelable {
        @Parcelize
        object Main : Config()

        @Parcelize
        data class TimetablePopup(val id: Int) : Config()

    }

    val timetableComponent: TimetableComponent
}
