package com.example.etymo.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.etymo.ui.theme.EtymoOffWhite
import com.example.etymo.ui.theme.EtymoYellow
import com.example.etymo.ui.theme.EtymoYellowDeep
import com.example.etymo.ui.theme.GlassBorder

@Composable
fun DrawingCanvas(
    strokes: List<List<Offset>>,
    referenceChar: String,
    onStrokeStart: (Offset) -> Unit,
    onStrokeDrag: (Offset) -> Unit,
    onStrokeEnd: () -> Unit,
    modifier: Modifier = Modifier,
    strokeColor: Color = EtymoYellowDeep,
    strokeWidth: Float = 8f,
) {
    val textMeasurer = rememberTextMeasurer()

    Box(
        modifier = modifier
            .clip(RoundedCornerShape(24.dp))
            .background(Color.White)
            .border(
                width = 2.dp,
                color = GlassBorder,
                shape = RoundedCornerShape(24.dp)
            ),
        contentAlignment = Alignment.Center
    ) {
        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .pointerInput(Unit) {
                    detectDragGestures(
                        onDragStart = { offset ->
                            onStrokeStart(offset)
                        },
                        onDrag = { change, _ ->
                            change.consume()
                            onStrokeDrag(change.position)
                        },
                        onDragEnd = {
                            onStrokeEnd()
                        }
                    )
                }
        ) {
            val canvasWidth = size.width
            val canvasHeight = size.height

            // Draw subtle grid lines
            val gridColor = Color(0xFFE8E8E8)
            val gridSpacing = 40.dp.toPx()

            // Vertical grid lines
            var x = gridSpacing
            while (x < canvasWidth) {
                drawLine(
                    color = gridColor,
                    start = Offset(x, 0f),
                    end = Offset(x, canvasHeight),
                    strokeWidth = 1f
                )
                x += gridSpacing
            }

            // Horizontal grid lines
            var y = gridSpacing
            while (y < canvasHeight) {
                drawLine(
                    color = gridColor,
                    start = Offset(0f, y),
                    end = Offset(canvasWidth, y),
                    strokeWidth = 1f
                )
                y += gridSpacing
            }

            // Draw center crosshair guides
            val guideColor = Color(0xFFD0D0D0)
            // Vertical center
            drawLine(
                color = guideColor,
                start = Offset(canvasWidth / 2, 0f),
                end = Offset(canvasWidth / 2, canvasHeight),
                strokeWidth = 1.5f
            )
            // Horizontal center
            drawLine(
                color = guideColor,
                start = Offset(0f, canvasHeight / 2),
                end = Offset(canvasWidth, canvasHeight / 2),
                strokeWidth = 1.5f
            )

            // Draw reference character as watermark
            if (referenceChar.isNotEmpty()) {
                val refStyle = TextStyle(
                    fontSize = 180.sp,
                    fontWeight = FontWeight.Light,
                    color = Color(0x18000000)
                )
                val measuredText = textMeasurer.measure(
                    text = referenceChar,
                    style = refStyle
                )
                drawText(
                    textLayoutResult = measuredText,
                    topLeft = Offset(
                        (canvasWidth - measuredText.size.width) / 2f,
                        (canvasHeight - measuredText.size.height) / 2f
                    )
                )
            }

            // Draw all strokes
            strokes.forEach { points ->
                if (points.size > 1) {
                    val path = Path().apply {
                        moveTo(points.first().x, points.first().y)
                        for (i in 1 until points.size) {
                            val prev = points[i - 1]
                            val curr = points[i]
                            // Use quadratic bezier for smoother curves
                            quadraticBezierTo(
                                prev.x, prev.y,
                                (prev.x + curr.x) / 2f, (prev.y + curr.y) / 2f
                            )
                        }
                    }
                    drawPath(
                        path = path,
                        color = strokeColor,
                        style = Stroke(
                            width = strokeWidth,
                            cap = StrokeCap.Round,
                            join = StrokeJoin.Round
                        )
                    )
                } else if (points.size == 1) {
                    // Single dot
                    drawCircle(
                        color = strokeColor,
                        radius = strokeWidth / 2f,
                        center = points.first()
                    )
                }
            }
        }
    }
}
