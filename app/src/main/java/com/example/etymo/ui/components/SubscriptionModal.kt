package com.example.etymo.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.WorkspacePremium
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.etymo.domain.SubscriptionTier
import com.example.etymo.ui.theme.*

@Composable
fun SubscriptionModal(
    showDialog: Boolean,
    currentTier: SubscriptionTier,
    onDismiss: () -> Unit,
    onPlanSelected: (SubscriptionTier) -> Unit
) {
    if (showDialog) {
        Dialog(
            onDismissRequest = onDismiss,
            properties = DialogProperties(
                usePlatformDefaultWidth = false,
                dismissOnBackPress = true,
                dismissOnClickOutside = true
            )
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.5f))
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                GlassCard(
                    modifier = Modifier.fillMaxWidth().fillMaxHeight(0.9f),
                    blurRadius = 40.dp,
                    cornerRadius = 32.dp,
                    backgroundAlpha = 0.98f,
                    borderAlpha = 0.6f
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(24.dp)
                    ) {
                        // Header
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Default.WorkspacePremium,
                                    contentDescription = "Premium",
                                    tint = EtymoPurple,
                                    modifier = Modifier.size(28.dp)
                                )
                                Spacer(modifier = Modifier.width(12.dp))
                                Text(
                                    text = "Choose Your Path",
                                    fontSize = 24.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = EtymoDark
                                )
                            }
                            IconButton(
                                onClick = onDismiss,
                                modifier = Modifier
                                    .size(36.dp)
                                    .clip(CircleShape)
                                    .background(EtymoOffWhite)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Close,
                                    contentDescription = "Close",
                                    tint = EtymoDark,
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(24.dp))

                        // Plans List
                        LazyColumn(
                            verticalArrangement = Arrangement.spacedBy(16.dp),
                            modifier = Modifier.weight(1f)
                        ) {
                            items(SubscriptionTier.values()) { plan ->
                                PlanCard(
                                    plan = plan,
                                    isCurrent = plan == currentTier,
                                    onClick = { 
                                        if (plan != currentTier) onPlanSelected(plan) 
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun PlanCard(
    plan: SubscriptionTier,
    isCurrent: Boolean,
    onClick: () -> Unit
) {
    val borderColor = if (plan == SubscriptionTier.GURU) EtymoPurple else Color.Transparent
    val backgroundColor = EtymoWhite

    ClayCard(
        modifier = Modifier
            .fillMaxWidth()
            .pointerInput(isCurrent) {
                detectTapGestures(
                    onDoubleTap = { if (!isCurrent) onClick() }
                )
            }
            .border(
                width = if (borderColor != Color.Transparent) 2.dp else 0.dp,
                color = borderColor,
                shape = RoundedCornerShape(24.dp)
            ),
        backgroundColor = backgroundColor,
        cornerRadius = 24.dp
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = plan.title,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = EtymoDark
                    )
                    Text(
                        text = plan.description,
                        fontSize = 14.sp,
                        color = EtymoDarkCard,
                        modifier = Modifier.padding(top = 4.dp, end = 8.dp)
                    )
                }
                Column(horizontalAlignment = Alignment.End) {
                    Text(
                        text = if (plan.priceMonthly == 0) "Free" else "₹${plan.priceMonthly}",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = if (plan == SubscriptionTier.GURU) EtymoPurple else EtymoDark
                    )
                    if (plan.priceMonthly > 0) {
                        Text(
                            text = "/ month",
                            fontSize = 12.sp,
                            color = EtymoDarkCard
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
            HorizontalDivider(color = EtymoOffWhite, thickness = 1.dp)
            Spacer(modifier = Modifier.height(16.dp))

            plan.features.forEach { feature ->
                Row(
                    verticalAlignment = Alignment.Top,
                    modifier = Modifier.padding(bottom = 8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = "Included",
                        tint = if (plan == SubscriptionTier.GURU) EtymoPurple else EtymoYellow,
                        modifier = Modifier.size(18.dp).padding(top = 2.dp)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        text = feature,
                        fontSize = 14.sp,
                        color = EtymoDark
                    )
                }
            }

            if (isCurrent) {
                Spacer(modifier = Modifier.height(16.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                        .background(EtymoSoftGreen.copy(alpha = 0.2f))
                        .padding(vertical = 8.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Current Plan",
                        color = EtymoSoftGreen,
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp
                    )
                }
            }
        }
    }
}
