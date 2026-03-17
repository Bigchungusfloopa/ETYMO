package com.example.etymo.screens.learn

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.etymo.data.local.LessonUnit
import com.example.etymo.ui.components.PeacockMascot
import com.example.etymo.ui.theme.*

enum class UnitNodeState {
    COMPLETED, CURRENT, LOCKED
}

@Composable
fun UnitPathScreen(
    sectionTitle: String,
    units: List<LessonUnit>,
    isUnitUnlocked: (Int) -> Boolean,
    isUnitCompleted: (Int) -> Boolean,
    onUnitSelected: (Int) -> Unit,
    onBack: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(EtymoOffWhite)
    ) {
        // Top bar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 24.dp, top = 48.dp, bottom = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBack) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = EtymoDark)
            }
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = sectionTitle,
                fontSize = 24.sp,
                fontWeight = FontWeight.ExtraBold,
                color = EtymoDark,
                modifier = Modifier.weight(1f)
            )
            PeacockMascot(
                isAnimating = false,
                frozenProgress = 0.5f, // confident look
                size = 72.dp
            )
        }

        // Path
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 32.dp, vertical = 16.dp)
                .padding(bottom = 120.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            units.forEachIndexed { index, unit ->
                val state = when {
                    isUnitCompleted(index) -> UnitNodeState.COMPLETED
                    isUnitUnlocked(index) -> UnitNodeState.CURRENT
                    else -> UnitNodeState.LOCKED
                }

                // Zigzag offset
                val offsetX = if (index % 2 == 0) (-40).dp else 40.dp

                // Connector line (before each node except the first)
                if (index > 0) {
                    val prevState = when {
                        isUnitCompleted(index - 1) -> UnitNodeState.COMPLETED
                        isUnitUnlocked(index - 1) -> UnitNodeState.CURRENT
                        else -> UnitNodeState.LOCKED
                    }
                    val lineColor = if (prevState == UnitNodeState.COMPLETED) EtymoCorrect.copy(alpha = 0.4f)
                        else EtymoLocked.copy(alpha = 0.3f)

                    Canvas(
                        modifier = Modifier
                            .height(40.dp)
                            .width(120.dp)
                    ) {
                        val dashEffect = PathEffect.dashPathEffect(floatArrayOf(12f, 8f))
                        drawLine(
                            color = lineColor,
                            start = Offset(size.width / 2, 0f),
                            end = Offset(size.width / 2, size.height),
                            strokeWidth = 4f,
                            pathEffect = dashEffect
                        )
                    }
                }

                // Node
                Box(
                    modifier = Modifier.offset(x = offsetX)
                ) {
                    UnitNode(
                        unit = unit,
                        index = index,
                        state = state,
                        onClick = {
                            if (state != UnitNodeState.LOCKED) {
                                onUnitSelected(index)
                            }
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun UnitNode(
    unit: LessonUnit,
    index: Int,
    state: UnitNodeState,
    onClick: () -> Unit
) {
    val nodeColor = when (state) {
        UnitNodeState.COMPLETED -> EtymoCorrect
        UnitNodeState.CURRENT -> EtymoYellow
        UnitNodeState.LOCKED -> EtymoLocked
    }

    val bgColor = when (state) {
        UnitNodeState.COMPLETED -> EtymoCorrect.copy(alpha = 0.12f)
        UnitNodeState.CURRENT -> EtymoYellow.copy(alpha = 0.12f)
        UnitNodeState.LOCKED -> EtymoLocked.copy(alpha = 0.08f)
    }

    // Pulse animation for current unit
    val infiniteTransition = rememberInfiniteTransition(label = "pulse")
    val pulseScale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = if (state == UnitNodeState.CURRENT) 1.08f else 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(800, easing = EaseInOutCubic),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulseScale"
    )

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .scale(pulseScale)
            .clickable(enabled = state != UnitNodeState.LOCKED) { onClick() }
    ) {
        // Circle node
        Box(
            modifier = Modifier
                .size(72.dp)
                .shadow(
                    elevation = if (state == UnitNodeState.CURRENT) 12.dp else 6.dp,
                    shape = CircleShape,
                    spotColor = nodeColor.copy(alpha = 0.3f)
                )
                .clip(CircleShape)
                .background(EtymoWhite)
                .padding(4.dp),
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(CircleShape)
                    .background(bgColor),
                contentAlignment = Alignment.Center
            ) {
                when (state) {
                    UnitNodeState.COMPLETED -> {
                        Icon(
                            Icons.Default.Check,
                            contentDescription = "Completed",
                            tint = EtymoCorrect,
                            modifier = Modifier.size(32.dp)
                        )
                    }
                    UnitNodeState.CURRENT -> {
                        Icon(
                            Icons.Default.PlayArrow,
                            contentDescription = "Start",
                            tint = EtymoYellowDark,
                            modifier = Modifier.size(32.dp)
                        )
                    }
                    UnitNodeState.LOCKED -> {
                        Icon(
                            Icons.Default.Lock,
                            contentDescription = "Locked",
                            tint = EtymoLocked,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Unit title label
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(12.dp))
                .background(
                    if (state == UnitNodeState.LOCKED) EtymoLocked.copy(alpha = 0.1f)
                    else nodeColor.copy(alpha = 0.12f)
                )
                .padding(horizontal = 14.dp, vertical = 6.dp)
        ) {
            Text(
                text = unit.title,
                fontSize = 13.sp,
                fontWeight = FontWeight.SemiBold,
                color = if (state == UnitNodeState.LOCKED) EtymoLocked else EtymoDark,
                textAlign = TextAlign.Center
            )
        }
    }
}
