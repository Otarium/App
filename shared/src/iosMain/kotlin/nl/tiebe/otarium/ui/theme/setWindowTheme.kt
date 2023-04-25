package nl.tiebe.otarium.ui.theme

import androidx.compose.ui.graphics.Color
import nl.tiebe.otarium.darkModeState
import platform.UIKit.UIApplication
import platform.UIKit.UIStatusBarStyleDarkContent
import platform.UIKit.UIStatusBarStyleLightContent
import platform.UIKit.setStatusBarStyle

actual fun setWindowTheme(color: Color) {
    UIApplication.sharedApplication.setStatusBarStyle(if(darkModeState.value) UIStatusBarStyleLightContent else UIStatusBarStyleDarkContent)
}