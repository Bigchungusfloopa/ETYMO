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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.etymo.ui.theme.EtymoAmber
import com.example.etymo.ui.theme.EtymoDark
import com.example.etymo.ui.theme.EtymoDarkCard
import com.example.etymo.ui.theme.EtymoGreen
import com.example.etymo.ui.theme.EtymoWhite
import com.example.etymo.ui.theme.EtymoYellow
import kotlin.random.Random

@Composable
fun StreakCard(
    modifier: Modifier = Modifier,
    streakDays: Int
) {
    var showDialog by remember { mutableStateOf(false) }

    StatClayCard(
        modifier = modifier
            .clip(RoundedCornerShape(24.dp))
            .clickable { showDialog = true },
        title = "Streak",
        value = "$streakDays Days",
        icon = Icons.Default.LocalFireDepartment,
        iconTint = EtymoAmber
    )

    if (showDialog) {
        Dialog(
            onDismissRequest = { showDialog = false },
            properties = DialogProperties(usePlatformDefaultWidth = false)
        ) {
            // Internal state to trigger animations once the Dialog is attached
            var visible by remember { mutableStateOf(false) }

            LaunchedEffect(Unit) {
                visible = true
            }

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                contentAlignment = Alignment.Center
            ) {
                AnimatedVisibility(
                    visible = visible,
                    enter = fadeIn(animationSpec = tween(300)) + scaleIn(
                        initialScale = 0.8f,
                        animationSpec = tween(300)
                    ),
                    exit = fadeOut(animationSpec = tween(200)) + scaleOut(
                        targetScale = 0.8f,
                        animationSpec = tween(200)
                    )
                ) {
                    ClayCard(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight(),
                        backgroundColor = EtymoWhite,
                        cornerRadius = 32.dp
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(24.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            // Header
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
                                        imageVector = Icons.Default.LocalFireDepartment,
                                        contentDescription = null,
                                        tint = EtymoAmber,
                                        modifier = Modifier.size(28.dp)
                                    )
                                    Text(
                                        text = "$streakDays Day Streak",
                                        fontSize = 22.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = EtymoDark
                                    )
                                }
                                IconButton(onClick = {
                                    showDialog = false
                                }) {
                                    Icon(
                                        imageVector = Icons.Default.Close,
                                        contentDescription = "Close",
                                        tint = EtymoDark
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(16.dp))

                            // Scrollable area for content
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .verticalScroll(rememberScrollState())
                            ) {
                                var selectedPeriod by remember { mutableStateOf("Month") }

                                // Segmented Toggle
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
                                                .background(if (isSelected) EtymoWhite else Color.Transparent)
                                                .clickable { selectedPeriod = period }
                                                .padding(vertical = 8.dp),
                                            contentAlignment = Alignment.Center
                                        ) {
                                            Text(
                                                text = period,
                                                fontSize = 14.sp,
                                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                                color = if (isSelected) EtymoDark else EtymoDarkCard.copy(
                                                    alpha = 0.6f
                                                )
                                            )
                                        }
                                    }
                                }

                                Spacer(modifier = Modifier.height(24.dp))

                                // Grid or Chart
                                Column(
                                    modifier = Modifier.fillMaxWidth(),
                                    verticalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    if (selectedPeriod == "Week") {
                                        Text(
                                            text = "This Week's Streak",
                                            fontSize = 14.sp,
                                            fontWeight = FontWeight.SemiBold,
                                            color = EtymoDarkCard.copy(alpha = 0.7f),
                                            modifier = Modifier.padding(bottom = 12.dp)
                                        )

                                        val daysData = listOf(
                                            "Mon" to 12, "Tue" to 9, "Wed" to 15,
                                            "Thu" to 0, "Fri" to 20, "Sat" to 7, "Sun" to 11
                                        )
                                        Row(
                                            modifier = Modifier.fillMaxWidth(),
                                            horizontalArrangement = Arrangement.SpaceBetween
                                        ) {
                                            daysData.forEach { (day, value) ->
                                                val isActive = value > 0
                                                Column(
                                                    horizontalAlignment = Alignment.CenterHorizontally,
                                                    verticalArrangement = Arrangement.spacedBy(6.dp)
                                                ) {
                                                    Box(
                                                        modifier = Modifier
                                                            .size(40.dp)
                                                            .clip(RoundedCornerShape(12.dp))
                                                            .background(
                                                                if (isActive) EtymoGreen
                                                                else EtymoDarkCard.copy(alpha = 0.08f)
                                                            ),
                                                        contentAlignment = Alignment.Center
                                                    ) {
                                                        Text(
                                                            text = if (isActive) "$value" else "–",
                                                            fontSize = 13.sp,
                                                            fontWeight = FontWeight.Bold,
                                                            color = if (isActive) EtymoWhite
                                                            else EtymoDarkCard.copy(alpha = 0.3f)
                                                        )
                                                    }
                                                    Text(
                                                        text = day,
                                                        fontSize = 11.sp,
                                                        fontWeight = FontWeight.Medium,
                                                        color = EtymoDarkCard.copy(alpha = 0.5f)
                                                    )
                                                }
                                            }
                                        }
                                    } else {
                                        Text(
                                            text = "This Month's Streak",
                                            fontSize = 14.sp,
                                            fontWeight = FontWeight.SemiBold,
                                            color = EtymoDarkCard.copy(alpha = 0.7f),
                                            modifier = Modifier.padding(bottom = 12.dp)
                                        )

                                        val columns = 7
                                        val rows = 5 // 35 days view

                                        val random = java.util.Random(42)

                                        // Days of week header
                                        Row(
                                            modifier = Modifier.fillMaxWidth(),
                                            horizontalArrangement = Arrangement.SpaceBetween
                                        ) {
                                            val daysLabel =
                                                listOf("S", "M", "T", "W", "T", "F", "S")
                                            daysLabel.forEach { day ->
                                                Box(
                                                    modifier = Modifier.width(32.dp),
                                                    contentAlignment = Alignment.Center
                                                ) {
                                                    Text(
                                                        text = day,
                                                        fontSize = 12.sp,
                                                        fontWeight = FontWeight.Bold,
                                                        color = EtymoDarkCard.copy(alpha = 0.6f)
                                                    )
                                                }
                                            }
                                        }

                                        var currentDayNum = 1
                                        for (r in 0 until rows) {
                                            Row(
                                                modifier = Modifier.fillMaxWidth(),
                                                horizontalArrangement = Arrangement.SpaceBetween
                                            ) {
                                                for (c in 0 until columns) {
                                                    val isActiveDay = random.nextFloat() > 0.4f
                                                    val isCurrentMonth = currentDayNum <= 31

                                                    val color = if (isActiveDay && isCurrentMonth) {
                                                        EtymoGreen
                                                    } else {
                                                        Color.Transparent
                                                    }

                                                    val textColor =
                                                        if (isActiveDay && isCurrentMonth) {
                                                            EtymoWhite
                                                        } else if (isCurrentMonth) {
                                                            EtymoDarkCard.copy(alpha = 0.6f)
                                                        } else {
                                                            Color.Transparent
                                                        }

                                                    Box(
                                                        modifier = Modifier
                                                            .size(32.dp)
                                                            .clip(CircleShape)
                                                            .background(color),
                                                        contentAlignment = Alignment.Center
                                                    ) {
                                                        if (isCurrentMonth) {
                                                            Text(
                                                                text = "$currentDayNum",
                                                                fontSize = 14.sp,
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

                                Spacer(modifier = Modifier.height(16.dp))
                                Text(
                                    text = "Keep it up! Your streak is on fire! 🔥",
                                    fontSize = 14.sp,
                                    color = EtymoDark.copy(alpha = 0.6f),
                                    fontWeight = FontWeight.Medium
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
