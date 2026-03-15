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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.etymo.ui.theme.EtymoDark
import com.example.etymo.ui.theme.EtymoDarkCard
import com.example.etymo.ui.theme.EtymoGreen
import com.example.etymo.ui.theme.EtymoWhite

@Composable
fun WordsMasteredCard(
    modifier: Modifier = Modifier,
    words: Int,
    isLarge: Boolean = true
) {
    var showDialog by remember { mutableStateOf(false) }
    val formattedWords = String.format("%,d", words)
    
    StatClayCard(
        modifier = modifier
            .clip(RoundedCornerShape(24.dp))
            .clickable { showDialog = true },
        title = "Total Words Mastered",
        value = "$formattedWords Words",
        icon = Icons.Default.MenuBook,
        iconTint = EtymoGreen,
        isLarge = isLarge
    )

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
                                        imageVector = Icons.Default.MenuBook,
                                        contentDescription = null,
                                        tint = EtymoGreen,
                                        modifier = Modifier.size(28.dp)
                                    )
                                    Text(
                                        text = "$formattedWords Mastered",
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

                            Spacer(modifier = Modifier.height(24.dp))

                            // Recent Words List
                            Column(modifier = Modifier.fillMaxWidth()) {
                                Text(
                                    text = "Recently Mastered",
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = EtymoDarkCard.copy(alpha = 0.7f),
                                    modifier = Modifier.padding(bottom = 12.dp)
                                )

                                val recentWords = listOf(
                                    "Ephemeral" to "Lasting for a very short time",
                                    "Serendipity" to "The occurrence of events by chance",
                                    "Mellifluous" to "Sweet or musical; pleasant to hear",
                                    "Luminous" to "Full of or shedding light; bright"
                                )

                                recentWords.forEach { (word, definition) ->
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(vertical = 8.dp)
                                            .background(
                                                color = EtymoGreen.copy(alpha = 0.1f),
                                                shape = RoundedCornerShape(12.dp)
                                            )
                                            .padding(12.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Column {
                                            Text(
                                                text = word,
                                                fontSize = 16.sp,
                                                fontWeight = FontWeight.Bold,
                                                color = EtymoDark
                                            )
                                            Text(
                                                text = definition,
                                                fontSize = 13.sp,
                                                color = EtymoDarkCard.copy(alpha = 0.8f),
                                                maxLines = 1,
                                                modifier = Modifier.padding(top = 2.dp)
                                            )
                                        }
                                    }
                                }
                            }
                            
                            Spacer(modifier = Modifier.height(24.dp))
                            Text(
                                text = "Your vocabulary is expanding! 📚",
                                fontSize = 14.sp,
                                color = EtymoDark.copy(alpha = 0.6f),
                                fontWeight = FontWeight.Medium,
                                textAlign = androidx.compose.ui.text.style.TextAlign.Center
                            )
                        }
                    }
                }
            }
        }
    }
}
