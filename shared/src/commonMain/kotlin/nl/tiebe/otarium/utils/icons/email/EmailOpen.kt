package nl.tiebe.otarium.utils.icons.email

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType.Companion.NonZero
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap.Companion.Butt
import androidx.compose.ui.graphics.StrokeJoin.Companion.Miter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.ImageVector.Builder
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import nl.tiebe.otarium.utils.icons.EmailGroup

public val EmailGroup.EmailOpen: ImageVector
    get() {
        if (_emailOpen != null) {
            return _emailOpen!!
        }
        _emailOpen = Builder(name = "EmailOpen", defaultWidth = 24.0.dp, defaultHeight = 24.0.dp,
                viewportWidth = 24.0f, viewportHeight = 24.0f).apply {
            path(fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(4.0f, 8.0f)
                lineTo(12.0f, 13.0f)
                lineTo(20.0f, 8.0f)
                verticalLineTo(8.0f)
                lineTo(12.0f, 3.0f)
                lineTo(4.0f, 8.0f)
                verticalLineTo(8.0f)
                moveTo(22.0f, 8.0f)
                verticalLineTo(18.0f)
                arcTo(2.0f, 2.0f, 0.0f, false, true, 20.0f, 20.0f)
                horizontalLineTo(4.0f)
                arcTo(2.0f, 2.0f, 0.0f, false, true, 2.0f, 18.0f)
                verticalLineTo(8.0f)
                curveTo(2.0f, 7.27f, 2.39f, 6.64f, 2.97f, 6.29f)
                lineTo(12.0f, 0.64f)
                lineTo(21.03f, 6.29f)
                curveTo(21.61f, 6.64f, 22.0f, 7.27f, 22.0f, 8.0f)
                close()
            }
        }
        .build()
        return _emailOpen!!
    }

private var _emailOpen: ImageVector? = null
