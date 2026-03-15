package com.example.etymo.ui.components

import android.graphics.BlurMaskFilter
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun ClayCard(
    modifier: Modifier = Modifier,
    backgroundColor: Color = Color.White,
    lightShadowColor: Color = Color.White.copy(alpha = 0.9f),
    darkShadowColor: Color = Color.Black.copy(alpha = 0.15f),
    outerShadowColor: Color = Color.Black.copy(alpha = 0.1f),
    cornerRadius: Dp = 24.dp,
    elevation: Dp = 8.dp,
    content: @Composable BoxScope.() -> Unit
) {
    Box(
        modifier = modifier
            .shadow(
                elevation = elevation,
                shape = RoundedCornerShape(cornerRadius),
                spotColor = outerShadowColor,
                ambientColor = outerShadowColor
            )
            .background(color = backgroundColor, shape = RoundedCornerShape(cornerRadius))
            .clip(RoundedCornerShape(cornerRadius))
            .innerShadow(
                color = lightShadowColor,
                offsetX = (-6).dp,
                offsetY = (-6).dp,
                blur = 12.dp,
                cornerRadius = cornerRadius
            )
            .innerShadow(
                color = darkShadowColor,
                offsetX = 6.dp,
                offsetY = 6.dp,
                blur = 12.dp,
                cornerRadius = cornerRadius
            )
    ) {
        content()
    }
}

fun Modifier.innerShadow(
    color: Color,
    offsetX: Dp = 0.dp,
    offsetY: Dp = 0.dp,
    blur: Dp = 4.dp,
    cornerRadius: Dp = 0.dp
): Modifier = this.drawWithContent {
    drawContent()
    drawIntoCanvas { canvas ->
        val paint = Paint().apply {
            this.color = color
            this.isAntiAlias = true
        }
        val frameworkPaint = paint.asFrameworkPaint()
        if (blur.toPx() > 0) {
            frameworkPaint.maskFilter = BlurMaskFilter(blur.toPx(), BlurMaskFilter.Blur.NORMAL)
        }
        
        // Setup shadow path
        val shadowPath = Path().apply {
            // A huge outer rect
            val outerRect = Rect(-size.width, -size.height, size.width * 2, size.height * 2)
            addRect(outerRect)
            
            // The inner hollow part matching our bounds + offset
            val innerRoundRect = RoundRect(
                rect = Rect(
                    offsetX.toPx(),
                    offsetY.toPx(),
                    size.width + offsetX.toPx(),
                    size.height + offsetY.toPx()
                ),
                cornerRadius = CornerRadius(cornerRadius.toPx(), cornerRadius.toPx())
            )
            addRoundRect(innerRoundRect)
            
            // FillType.EvenOdd ensures the inner rectangle is hollow
            fillType = PathFillType.EvenOdd
        }
        
        canvas.drawPath(shadowPath, paint)
    }
}
