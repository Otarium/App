package nl.tiebe.otarium.utils.icons.bottombar

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType.Companion.NonZero
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap.Companion.Butt
import androidx.compose.ui.graphics.StrokeJoin.Companion.Miter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.ImageVector.Builder
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import nl.tiebe.otarium.utils.icons.BottombarGroup

public val BottombarGroup.BookOpenOutline: ImageVector
    get() {
        if (_bookOpenOutline != null) {
            return _bookOpenOutline!!
        }
        _bookOpenOutline = Builder(name = "BookOpenOutline", defaultWidth = 24.0.dp, defaultHeight =
                24.0.dp, viewportWidth = 24.0f, viewportHeight = 24.0f).apply {
            path(fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(21.0f, 4.0f)
                horizontalLineTo(3.0f)
                arcTo(2.0f, 2.0f, 0.0f, false, false, 1.0f, 6.0f)
                verticalLineTo(19.0f)
                arcTo(2.0f, 2.0f, 0.0f, false, false, 3.0f, 21.0f)
                horizontalLineTo(21.0f)
                arcTo(2.0f, 2.0f, 0.0f, false, false, 23.0f, 19.0f)
                verticalLineTo(6.0f)
                arcTo(2.0f, 2.0f, 0.0f, false, false, 21.0f, 4.0f)
                moveTo(3.0f, 19.0f)
                verticalLineTo(6.0f)
                horizontalLineTo(11.0f)
                verticalLineTo(19.0f)
                horizontalLineTo(3.0f)
                moveTo(21.0f, 19.0f)
                horizontalLineTo(13.0f)
                verticalLineTo(6.0f)
                horizontalLineTo(21.0f)
                verticalLineTo(19.0f)
                moveTo(14.0f, 9.5f)
                horizontalLineTo(20.0f)
                verticalLineTo(11.0f)
                horizontalLineTo(14.0f)
                verticalLineTo(9.5f)
                moveTo(14.0f, 12.0f)
                horizontalLineTo(20.0f)
                verticalLineTo(13.5f)
                horizontalLineTo(14.0f)
                verticalLineTo(12.0f)
                moveTo(14.0f, 14.5f)
                horizontalLineTo(20.0f)
                verticalLineTo(16.0f)
                horizontalLineTo(14.0f)
                verticalLineTo(14.5f)
                close()
            }
        }
        .build()
        return _bookOpenOutline!!
    }

private var _bookOpenOutline: ImageVector? = null
