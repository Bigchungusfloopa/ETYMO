package com.example.etymo.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.*
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.rounded.WorkspacePremium
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.etymo.ui.theme.*

data class BadgeData(
    val name: String,
    val icon: ImageVector,
    val isUnlocked: Boolean,
    val unlockedDate: String? = null,
    val description: String
)

val allBadges = listOf(
    BadgeData("The Haggler", Icons.Default.MonetizationOn, true, "Oct 12, 2025", "You negotiated 50 vocabulary word hint prices down!"),
    BadgeData("Ancient Scholar", Icons.Default.AutoStories, true, "Nov 05, 2025", "You successfully completed all classic literature etymology lessons."),
    BadgeData("Early Bird", Icons.Default.WbTwilight, true, "Dec 01, 2025", "You completed a lesson before 6:00 AM on 5 different days."),
    BadgeData("Night Owl", Icons.Default.Bedtime, false, null, "Complete 10 lessons after midnight to unlock this badge."),
    BadgeData("Polyglot", Icons.Default.Translate, false, null, "Master words derived from 5 different foreign language origins."),
    BadgeData("Wordsmith", Icons.Default.Create, false, null, "Write 20 custom mnemonic sentences for difficult words."),
    BadgeData("Grammar Guru", Icons.Default.Spellcheck, false, null, "Achieve a perfect score on 5 consecutive grammar quizzes."),
    BadgeData("Social Butterfly", Icons.Default.People, false, null, "Share your learning progress with friends 10 times.")
)

@Composable
fun BadgesSection(modifier: Modifier = Modifier) {
    var showDialog by remember { mutableStateOf(false) }
    
    val unlockedBadges = allBadges.filter { it.isUnlocked }

    Column(modifier = modifier) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Unlocked Badges",
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold,
                color = EtymoDark
            )
            Text(
                text = "Show All",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = EtymoPurple,
                modifier = Modifier
                    .clip(RoundedCornerShape(12.dp))
                    .clickable { showDialog = true }
                    .padding(horizontal = 8.dp, vertical = 4.dp)
            )
        }
        
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            items(unlockedBadges.take(3)) { badge ->
                BadgeClayCard(badge = badge, modifier = Modifier.width(125.dp).height(160.dp))
            }
        }
    }

    if (showDialog) {
        Dialog(
            onDismissRequest = { showDialog = false },
            properties = DialogProperties(usePlatformDefaultWidth = false)
        ) {
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
                            .fillMaxHeight(0.85f),
                        backgroundColor = EtymoWhite,
                        cornerRadius = 32.dp
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
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Rounded.WorkspacePremium,
                                        contentDescription = null,
                                        tint = EtymoPurple,
                                        modifier = Modifier.size(28.dp)
                                    )
                                    Text(
                                        text = "All Badges",
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
                            
                            Spacer(modifier = Modifier.height(8.dp))
                            
                            Text(
                                text = "You have unlocked ${unlockedBadges.size} of ${allBadges.size} badges.",
                                fontSize = 14.sp,
                                color = EtymoDarkCard.copy(alpha = 0.7f),
                                fontWeight = FontWeight.Medium,
                                modifier = Modifier.padding(bottom = 16.dp)
                            )

                            // Grid
                            LazyVerticalGrid(
                                columns = GridCells.Fixed(2),
                                contentPadding = PaddingValues(vertical = 8.dp),
                                horizontalArrangement = Arrangement.spacedBy(16.dp),
                                verticalArrangement = Arrangement.spacedBy(16.dp),
                                modifier = Modifier.weight(1f)
                            ) {
                                items(allBadges) { badge ->
                                    BadgeClayCard(badge = badge, modifier = Modifier.fillMaxWidth().height(160.dp))
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun BadgeClayCard(badge: BadgeData, modifier: Modifier = Modifier) {
    val alpha = if (badge.isUnlocked) 1f else 0.5f
    var showDetailsDialog by remember { mutableStateOf(false) }

    Box(modifier = modifier.clickable { showDetailsDialog = true }) {
        ClayCard(
            modifier = Modifier.fillMaxSize(),
            backgroundColor = EtymoWhite.copy(alpha = alpha),
            cornerRadius = 20.dp
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(vertical = 16.dp, horizontal = 8.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Box(
                    modifier = Modifier
                        .size(56.dp)
                        .clip(CircleShape)
                        .background(if (badge.isUnlocked) EtymoOffWhite else Color.LightGray.copy(alpha = 0.3f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = badge.icon,
                        contentDescription = null,
                        tint = if (badge.isUnlocked) EtymoYellowDark else Color.Gray,
                        modifier = Modifier.size(32.dp)
                    )
                }
                Spacer(modifier = Modifier.height(12.dp))
                
                // Content wrapper to ensure bottom text aligns correctly
                Column(
                    modifier = Modifier.weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = badge.name,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = if (badge.isUnlocked) EtymoDark else Color.Gray,
                        textAlign = TextAlign.Center,
                        lineHeight = 16.sp
                    )
                    
                    if (badge.isUnlocked && badge.unlockedDate != null) {
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = badge.unlockedDate,
                            fontSize = 10.sp,
                            color = EtymoDarkCard.copy(alpha = 0.6f),
                            fontWeight = FontWeight.Medium,
                            textAlign = TextAlign.Center
                        )
                    } else if (!badge.isUnlocked) {
                        Spacer(modifier = Modifier.height(4.dp))
                        Icon(
                            imageVector = Icons.Default.Lock,
                            contentDescription = "Locked",
                            modifier = Modifier.size(12.dp),
                            tint = Color.Gray.copy(alpha = 0.6f)
                        )
                    }
                }
            }
        }
        
        // Shimmer overlay for unlocked badges
        if (badge.isUnlocked) {
            val transition = rememberInfiniteTransition(label = "shimmer")
            val translateAnim by transition.animateFloat(
                initialValue = -500f,
                targetValue = 1000f,
                animationSpec = infiniteRepeatable(
                    animation = tween(1500, easing = LinearEasing),
                    repeatMode = RepeatMode.Restart
                ),
                label = "shimmer_anim"
            )
            
            val brush = Brush.linearGradient(
                colors = listOf(
                    Color.Transparent,
                    Color.White.copy(alpha = 0.5f),
                    Color.Transparent
                ),
                start = Offset(translateAnim, translateAnim),
                end = Offset(translateAnim + 150f, translateAnim + 150f)
            )
            
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .clip(RoundedCornerShape(20.dp))
                    .background(brush)
            )
        }
    }
    
    if (showDetailsDialog) {
        Dialog(
            onDismissRequest = { showDetailsDialog = false },
            properties = DialogProperties(usePlatformDefaultWidth = false)
        ) {
            var visible by remember { mutableStateOf(false) }

            LaunchedEffect(Unit) {
                visible = true
            }

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(32.dp),
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
                        cornerRadius = 24.dp
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(24.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            // Close Button
                            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                                IconButton(onClick = { showDetailsDialog = false }, modifier = Modifier.size(24.dp)) {
                                    Icon(
                                        imageVector = Icons.Default.Close,
                                        contentDescription = "Close",
                                        tint = EtymoDark
                                    )
                                }
                            }
                            
                            Box(
                                modifier = Modifier
                                    .size(80.dp)
                                    .clip(CircleShape)
                                    .background(if (badge.isUnlocked) EtymoOffWhite else Color.LightGray.copy(alpha = 0.3f)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = badge.icon,
                                    contentDescription = null,
                                    tint = if (badge.isUnlocked) EtymoYellowDark else Color.Gray,
                                    modifier = Modifier.size(48.dp)
                                )
                            }
                            Spacer(modifier = Modifier.height(16.dp))
                            
                            Text(
                                text = badge.name,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                color = if (badge.isUnlocked) EtymoDark else Color.Gray,
                                textAlign = TextAlign.Center
                            )
                            
                            if (!badge.isUnlocked) {
                                Spacer(modifier = Modifier.height(8.dp))
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.Center,
                                    modifier = Modifier
                                        .background(Color.Gray.copy(alpha = 0.1f), RoundedCornerShape(8.dp))
                                        .padding(horizontal = 12.dp, vertical = 6.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Lock,
                                        contentDescription = "Locked",
                                        modifier = Modifier.size(16.dp),
                                        tint = Color.Gray
                                    )
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text(
                                        text = "Locked Badge",
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.SemiBold,
                                        color = Color.Gray
                                    )
                                }
                            } else if (badge.unlockedDate != null) {
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = "Unlocked on ${badge.unlockedDate}",
                                    fontSize = 12.sp,
                                    color = EtymoPurple,
                                    fontWeight = FontWeight.SemiBold
                                )
                            }
                            
                            Spacer(modifier = Modifier.height(16.dp))
                            
                            Text(
                                text = badge.description,
                                fontSize = 15.sp,
                                color = EtymoDarkCard.copy(alpha = 0.8f),
                                textAlign = TextAlign.Center,
                                lineHeight = 20.sp
                            )
                            
                            Spacer(modifier = Modifier.height(24.dp))
                        }
                    }
                }
            }
        }
    }
}
