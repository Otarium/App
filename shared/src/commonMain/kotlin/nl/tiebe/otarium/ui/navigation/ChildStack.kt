package nl.tiebe.otarium.ui.navigation

import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.Children
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.StackAnimation
import com.arkivanov.decompose.router.stack.StackNavigationSource
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.essenty.parcelable.Parcelable

@OptIn(ExperimentalDecomposeApi::class)
@Composable
internal inline fun <reified C : Parcelable> ChildStack(
    source: StackNavigationSource<C>,
    noinline initialStack: () -> List<C>,
    modifier: Modifier = Modifier,
    handleBackButton: Boolean = false,
    animation: StackAnimation<C, ComponentContext>? = null,
    noinline content: @Composable (C) -> Unit,
) {
    val componentContext = LocalComponentContext.current

    val childStack = remember {
        componentContext.childStack(
            source = source,
            initialStack = initialStack,
            handleBackButton = handleBackButton,
            childFactory = { _, childComponentContext -> childComponentContext },
        )
    }

    Children(
        stack = childStack,
        modifier = modifier,
        animation = animation,
    ) { child ->
        ProvideComponentContext(child.instance) {
            content(child.configuration)
        }
    }
}

val LocalComponentContext: ProvidableCompositionLocal<ComponentContext> =
    staticCompositionLocalOf { error("Root component context was not provided") }

@Composable
internal fun ProvideComponentContext(componentContext: ComponentContext, content: @Composable () -> Unit) {
    CompositionLocalProvider(LocalComponentContext provides componentContext, content = content)
}