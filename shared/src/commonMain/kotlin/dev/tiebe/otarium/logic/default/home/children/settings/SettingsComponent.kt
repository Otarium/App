package dev.tiebe.otarium.logic.default.home.children.settings

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.push
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.backhandler.BackCallback
import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
import dev.tiebe.otarium.MR
import dev.tiebe.otarium.logic.home.children.settings.children.main.DefaultMainChildComponent
import dev.tiebe.otarium.logic.home.children.settings.children.main.MainChildComponent
import dev.tiebe.otarium.logic.home.children.settings.children.ui.DefaultUIChildComponent
import dev.tiebe.otarium.logic.home.children.settings.children.ui.UIChildComponent
import dev.tiebe.otarium.logic.home.children.settings.children.ui.children.colors.ColorChildComponent
import dev.tiebe.otarium.logic.home.children.settings.children.ui.children.colors.DefaultColorChildComponent
import dev.tiebe.otarium.logic.home.children.settings.children.users.DefaultUserChildComponent
import dev.tiebe.otarium.logic.home.children.settings.children.users.UserChildComponent
import dev.tiebe.otarium.logic.default.RootComponent
import dev.tiebe.otarium.utils.ui.getLocalizedString

class DefaultSettingsComponent(
    componentContext: ComponentContext,
    override val navigateRootComponent: (RootComponent.ChildScreen) -> Unit,
): SettingsComponent, ComponentContext by componentContext {
    override val navigation = StackNavigation<SettingsComponent.Config>()

    override val childStack: Value<ChildStack<SettingsComponent.Config, SettingsComponent.Child>> =
        childStack(
            source = navigation,
            initialConfiguration = SettingsComponent.Config.Main,
            handleBackButton = true, // Pop the back stack on back button press
            childFactory = ::createChild,
        )

    private fun createChild(config: SettingsComponent.Config, componentContext: ComponentContext): SettingsComponent.Child =
        when (config) {
            is SettingsComponent.Config.Main -> SettingsComponent.Child.MainChild(mainChild(componentContext))
            is SettingsComponent.Config.Users -> SettingsComponent.Child.UsersChild(usersChild(componentContext))
            is SettingsComponent.Config.UI -> SettingsComponent.Child.UIChild(uiChild(componentContext))
            is SettingsComponent.Config.Colors -> SettingsComponent.Child.ColorChild(colorChild(componentContext))
        }

    private fun mainChild(componentContext: ComponentContext): MainChildComponent =
        DefaultMainChildComponent(
            componentContext = componentContext,
            _navigate = ::navigate
        )

    private fun usersChild(componentContext: ComponentContext): UserChildComponent =
        DefaultUserChildComponent(
            componentContext = componentContext,
            _navigate = ::navigate,
            navigateRootComponent = navigateRootComponent
        )

    private fun uiChild(componentContext: ComponentContext): UIChildComponent =
        DefaultUIChildComponent(
            componentContext = componentContext,
            _navigate = ::navigate
        )

    private fun colorChild(componentContext: ComponentContext): ColorChildComponent =
        DefaultColorChildComponent(
            componentContext = componentContext,
            _navigate = ::navigate
        )

    private val registered = MutableValue(false)
    private val backCallback = BackCallback { onBack.value() }
    override val onBack: MutableValue<() -> Unit> = MutableValue {}

    override fun registerBackHandler() {
        if (registered.value) return
        backHandler.register(backCallback)
        registered.value = true
    }

    override fun unregisterBackHandler() {
        if (!registered.value) return
        backHandler.unregister(backCallback)
        registered.value = false
    }


    init {
        childStack.subscribe { childStack ->
            if (childStack.backStack.isEmpty()) {
                unregisterBackHandler()
            } else {
                registerBackHandler()
            }
        }
    }
}