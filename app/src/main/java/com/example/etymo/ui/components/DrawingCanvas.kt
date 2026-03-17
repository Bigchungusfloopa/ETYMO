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

import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.clickable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.PanTool
import androidx.compose.material3.Icon
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.graphicsLayer
import com.example.etymo.ui.theme.EtymoDark

enum class CanvasMode { DRAW, MOVE }

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

    var scale by remember { mutableStateOf(1f) }
    var pan by remember { mutableStateOf(Offset.Zero) }
    var mode by remember { mutableStateOf(CanvasMode.DRAW) }

    Box(
        modifier = modifier
            .clip(RoundedCornerShape(24.dp))
            .background(Color.White)
            .border(
                width = 2.dp,
                color = GlassBorder,
                shape = RoundedCornerShape(24.dp)
            ),
    ) {
        // The actual drawing area with transformation applied
        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .graphicsLayer(
                    scaleX = scale,
                    scaleY = scale,
                    translationX = pan.x,
                    translationY = pan.y
                )
                .pointerInput(mode) {
                    if (mode == CanvasMode.DRAW) {
                        detectDragGestures(
                            onDragStart = { offset ->
                                // Unproject the raw touch offset based on scale & pan
                                val unprojectedX = (offset.x - pan.x) / scale
                                val unprojectedY = (offset.y - pan.y) / scale
                                onStrokeStart(Offset(unprojectedX, unprojectedY))
                            },
                            onDrag = { change, _ ->
                                change.consume()
                                val unprojectedX = (change.position.x - pan.x) / scale
                                val unprojectedY = (change.position.y - pan.y) / scale
                                onStrokeDrag(Offset(unprojectedX, unprojectedY))
                            },
                            onDragEnd = { onStrokeEnd() }
                        )
                    } else {
                        detectTransformGestures { _, panChange, zoomChange, _ ->
                            scale = (scale * zoomChange).coerceIn(1f, 5f)
                            pan += panChange
                        }
                    }
                }
        ) {
            val canvasWidth = size.width
            val canvasHeight = size.height

            // Draw subtle grid lines
            val gridColor = Color(0xFFE8E8E8)
            val gridSpacing = 40.dp.toPx()

            var x = gridSpacing
            while (x < canvasWidth) {
                drawLine(
                    color = gridColor,
                    start = Offset(x, 0f),
                    end = Offset(x, canvasHeight),
                    strokeWidth = 1f / scale // keep visually 1px
                )
                x += gridSpacing
            }

            var y = gridSpacing
            while (y < canvasHeight) {
                drawLine(
                    color = gridColor,
                    start = Offset(0f, y),
                    end = Offset(canvasWidth, y),
                    strokeWidth = 1f / scale
                )
                y += gridSpacing
            }

            // Draw center crosshair guides
            val guideColor = Color(0xFFD0D0D0)
            drawLine(
                color = guideColor,
                start = Offset(canvasWidth / 2, 0f),
                end = Offset(canvasWidth / 2, canvasHeight),
                strokeWidth = 1.5f / scale
            )
            drawLine(
                color = guideColor,
                start = Offset(0f, canvasHeight / 2),
                end = Offset(canvasWidth, canvasHeight / 2),
                strokeWidth = 1.5f / scale
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
                            width = strokeWidth / scale, // keep visual width
                            cap = StrokeCap.Round,
                            join = StrokeJoin.Round
                        )
                    )
                } else if (points.size == 1) {
                    drawCircle(
                        color = strokeColor,
                        radius = (strokeWidth / 2f) / scale,
                        center = points.first()
                    )
                }
            }
        }

        // Overlay controls for mode switching
        Row(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(12.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(Color.White.copy(alpha = 0.9f))
                .border(1.dp, GlassBorder, RoundedCornerShape(12.dp))
                .padding(4.dp),
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .background(if (mode == CanvasMode.DRAW) EtymoYellow else Color.Transparent)
                    .clickable { mode = CanvasMode.DRAW }
                    .padding(8.dp),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Create,
                    contentDescription = "Draw",
                    tint = EtymoDark,
                    modifier = Modifier.size(20.dp)
                )
            }
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .background(if (mode == CanvasMode.MOVE) EtymoYellow else Color.Transparent)
                    .clickable { mode = CanvasMode.MOVE }
                    .padding(8.dp),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.PanTool,
                    contentDescription = "Move/Zoom",
                    tint = EtymoDark,
                    modifier = Modifier.size(20.dp)
                )
            }
        }
    }
}
