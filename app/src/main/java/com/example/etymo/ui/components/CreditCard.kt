package com.example.etymo.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Stars
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.etymo.ui.theme.EtymoDark
import com.example.etymo.ui.theme.EtymoDarkCard
import com.example.etymo.ui.theme.EtymoWhite
import com.example.etymo.ui.theme.EtymoYellowGold

@Composable
fun CreditCard(
    modifier: Modifier = Modifier,
    xp: Int
) {
    var showDialog by remember { mutableStateOf(false) }
    val formattedXp = String.format("%,d", xp)

    StatClayCard(
        modifier = modifier
            .clip(RoundedCornerShape(24.dp))
            .clickable { showDialog = true },
        title = "Local Credit",
        value = "$formattedXp XP",
        icon = Icons.Default.Stars,
        iconTint = EtymoYellowGold
    )

    if (showDialog) {
        Dialog(
            onDismissRequest = { showDialog = false },
            properties = DialogProperties(usePlatformDefaultWidth = false)
        ) {
            var visible by remember { mutableStateOf(false) }

            LaunchedEffect(Unit) { visible = true }

            // Use screen height to cap dialog at 85% so it never overflows
            val screenHeight = LocalConfiguration.current.screenHeightDp.dp

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 20.dp, vertical = 32.dp),
                contentAlignment = Alignment.Center
            ) {
                AnimatedVisibility(
                    visible = visible,
                    enter = fadeIn(animationSpec = tween(300)) + scaleIn(
                        initialScale = 0.85f,
                        animationSpec = tween(300)
                    ),
                    exit = fadeOut(animationSpec = tween(200)) + scaleOut(
                        targetScale = 0.85f,
                        animationSpec = tween(200)
                    )
                ) {
                    ClayCard(
                        modifier = Modifier
                            .fillMaxWidth()
                            // Caps at 85% of screen height; shrinks to content on small content
                            .heightIn(max = screenHeight * 0.85f),
                        backgroundColor = EtymoWhite,
                        cornerRadius = 32.dp
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(24.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            // ── Header ────────────────────────────────────────
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Stars,
                                        contentDescription = null,
                                        tint = EtymoYellowGold,
                                        modifier = Modifier.size(28.dp)
                                    )
                                    Text(
                                        text = "$formattedXp XP Earned",
                                        fontSize = 22.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = EtymoDark
                                    )
                                }
                                IconButton(onClick = { showDialog = false }) {
                                    Icon(
                                        imageVector = Icons.Default.Close,
                                        contentDescription = "Close",
                                        tint = EtymoDark
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(16.dp))

                            // ── Scrollable body ───────────────────────────────
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .weight(1f, fill = false) // lets column shrink; scroll handles overflow
                                    .verticalScroll(rememberScrollState())
                            ) {
                                var selectedPeriod by remember { mutableStateOf("Week") }

                                // Segmented toggle
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .background(
                                            color = EtymoDarkCard.copy(alpha = 0.05f),
                                            shape = RoundedCornerShape(16.dp)
                                        )
                                        .padding(4.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    listOf("Week", "Month").forEach { period ->
                                        val isSelected = selectedPeriod == period
                                        Box(
                                            modifier = Modifier
                                                .weight(1f)
                                                .clip(RoundedCornerShape(12.dp))
                                                .background(
                                                    if (isSelected) EtymoWhite else Color.Transparent
                                                )
                                                .clickable { selectedPeriod = period }
                                                .padding(vertical = 8.dp),
                                            contentAlignment = Alignment.Center
                                        ) {
                                            Text(
                                                text = period,
                                                fontSize = 14.sp,
                                                fontWeight = if (isSelected) FontWeight.Bold
                                                             else FontWeight.Medium,
                                                color = if (isSelected) EtymoDark
                                                        else EtymoDarkCard.copy(alpha = 0.6f)
                                            )
                                        }
                                    }
                                }

                                Spacer(modifier = Modifier.height(24.dp))

                                // Chart label
                                Text(
                                    text = if (selectedPeriod == "Week") "This Week's Credit"
                                           else "This Month's Credit",
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = EtymoDarkCard.copy(alpha = 0.7f),
                                    modifier = Modifier.padding(bottom = 12.dp)
                                )

                                if (selectedPeriod == "Week") {
                                    WeekBarChart()
                                } else {
                                    MonthGrid()
                                }

                                Spacer(modifier = Modifier.height(24.dp))

                                Text(
                                    text = "Keep learning to earn more local credit! 🌟",
                                    fontSize = 14.sp,
                                    color = EtymoDark.copy(alpha = 0.6f),
                                    fontWeight = FontWeight.Medium,
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier.fillMaxWidth()
                                )

                                Spacer(modifier = Modifier.height(8.dp))
                            }
                        }
                    }
                }
            }
        }
    }
}

// ── Week bar chart ────────────────────────────────────────────────────────────
// Bars grow/shrink with available width via weight(); height stays proportional.

@Composable
private fun WeekBarChart() {
    val xpData = listOf(
        "Mon" to 150, "Tue" to 320, "Wed" to 80,
        "Thu" to 410, "Fri" to 0,   "Sat" to 290, "Sun" to 175
    )
    val maxXp = xpData.maxOf { it.second }.takeIf { it > 0 } ?: 1
    val maxBarHeightDp = 100 // logical max height in dp — comfortable on all screens

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Bottom
    ) {
        xpData.forEach { (day, xp) ->
            val barHeightDp = ((xp.toFloat() / maxXp) * maxBarHeightDp)
                .coerceAtLeast(if (xp > 0) 8f else 4f)

            Column(
                modifier = Modifier.weight(1f), // equal widths, fully fluid
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Bottom
            ) {
                // XP label above bar
                Text(
                    text = if (xp > 0) "$xp" else "",
                    fontSize = 9.sp,
                    fontWeight = FontWeight.Bold,
                    color = EtymoDark.copy(alpha = 0.5f)
                )
                Spacer(modifier = Modifier.height(3.dp))
                // Bar — width is a fraction of its weight slot, not hardcoded
                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.65f) // 65% of the weight slot → scales with screen
                        .height(barHeightDp.dp)
                        .clip(RoundedCornerShape(topStart = 6.dp, topEnd = 6.dp))
                        .background(
                            if (xp > 0) EtymoYellowGold
                            else EtymoDarkCard.copy(alpha = 0.08f)
                        )
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = day,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Medium,
                    color = EtymoDarkCard.copy(alpha = 0.55f)
                )
            }
        }
    }
}

// ── Month grid ────────────────────────────────────────────────────────────────
// Cell size is derived from available width so it looks right on every screen.

@Composable
private fun MonthGrid() {
    val columns = 7
    val rows = 5
    val daysLabel = listOf("S", "M", "T", "W", "T", "F", "S")
    val random = java.util.Random(42)

    // BoxWithConstraints lets us measure available width at composition time
    BoxWithConstraints(modifier = Modifier.fillMaxWidth()) {
        // Leave a small gap between cells; divide the rest equally
        val gapDp = 6.dp
        val totalGap = gapDp * (columns - 1)
        val cellSize = (maxWidth - totalGap) / columns

        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(gapDp)
        ) {
            // Day-of-week header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(gapDp)
            ) {
                daysLabel.forEach { label ->
                    Box(
                        modifier = Modifier.size(cellSize),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = label,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = EtymoDarkCard.copy(alpha = 0.6f)
                        )
                    }
                }
            }

            // Calendar cells
            var currentDayNum = 1
            for (r in 0 until rows) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(gapDp)
                ) {
                    for (c in 0 until columns) {
                        val isCurrentMonth = currentDayNum <= 31
                        val hasCredit = random.nextFloat() > 0.3f

                        val bgColor = if (hasCredit && isCurrentMonth)
                            EtymoYellowGold.copy(alpha = 0.2f + random.nextFloat() * 0.8f)
                        else Color.Transparent

                        val textColor = when {
                            hasCredit && isCurrentMonth -> EtymoDark
                            isCurrentMonth -> EtymoDarkCard.copy(alpha = 0.6f)
                            else -> Color.Transparent
                        }

                        Box(
                            modifier = Modifier
                                .size(cellSize)
                                .clip(RoundedCornerShape(8.dp))
                                .background(bgColor),
                            contentAlignment = Alignment.Center
                        ) {
                            if (isCurrentMonth) {
                                // Font size scales relative to cell size
                                Text(
                                    text = "$currentDayNum",
                                    fontSize = (cellSize.value * 0.35f).sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = textColor
                                )
                            }
                        }
                        currentDayNum++
                    }
                }
            }
        }
    }
}