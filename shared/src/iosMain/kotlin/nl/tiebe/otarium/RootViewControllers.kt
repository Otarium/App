package nl.tiebe.otarium

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.Text
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Application
import org.jetbrains.skiko.SystemTheme
import org.jetbrains.skiko.currentSystemTheme
import platform.CoreGraphics.CGFloat
import platform.UIKit.UIViewController

fun RootViewController(): UIViewController = Application {
    Text("test")
}

fun setSafeArea(start: CGFloat, top: CGFloat, end: CGFloat, bottom: CGFloat) {
    safeAreaState.value = PaddingValues(start.dp, top.dp, end.dp, bottom.dp)
}

fun setDarkMode() {
    darkmodeState.value = currentSystemTheme == SystemTheme.DARK
}
// TODO: between 28f7ef3c802ef4b85530ea42fcff61a7510a9f7e and HEAD