package com.example.etymo.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.etymo.domain.NodeOrigin
import com.example.etymo.domain.WordNode
import com.example.etymo.ui.theme.*

// ─── Root Node Card (claymorphism) ──────────────────────────────────────────

@Composable
fun RootNodeCard(
    node: WordNode.RootNode,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .width(180.dp)
            .clickable { onClick() },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Origin badge
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(12.dp))
                .background(node.origin.color.copy(alpha = 0.18f))
                .border(1.5.dp, node.origin.color, RoundedCornerShape(12.dp))
                .padding(horizontal = 10.dp, vertical = 3.dp)
        ) {
            Text(
                text = node.origin.name,
                color = node.origin.color,
                fontSize = 11.sp,
                fontWeight = FontWeight.ExtraBold,
                letterSpacing = 1.sp
            )
        }

        Spacer(modifier = Modifier.height(6.dp))

        // Clay card body
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .shadow(
                    elevation = 8.dp,
                    shape = RoundedCornerShape(20.dp),
                    ambientColor = node.origin.color.copy(alpha = 0.4f),
                    spotColor = node.origin.color.copy(alpha = 0.4f)
                )
                .clip(RoundedCornerShape(20.dp))
                .background(EtymoWhite)
                .border(
                    width = 3.dp,
                    color = node.origin.color.copy(alpha = 0.6f),
                    shape = RoundedCornerShape(20.dp)
                )
                .padding(horizontal = 14.dp, vertical = 12.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = node.text,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = EtymoDark,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = node.translation,
                    fontSize = 13.sp,
                    color = EtymoDarkCard.copy(alpha = 0.7f),
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

// ─── Leaf Node Card (claymorphism) ──────────────────────────────────────────

@Composable
fun LeafNodeCard(
    node: WordNode.LeafNode,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .width(150.dp)
            .shadow(
                elevation = 6.dp,
                shape = RoundedCornerShape(16.dp),
                ambientColor = EtymoDark.copy(alpha = 0.12f),
                spotColor = EtymoDark.copy(alpha = 0.12f)
            )
            .clip(RoundedCornerShape(16.dp))
            .background(EtymoYellow.copy(alpha = 0.85f))
            .border(2.dp, EtymoYellowDeep, RoundedCornerShape(16.dp))
            .clickable { onClick() }
            .padding(horizontal = 10.dp, vertical = 10.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = node.text,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = EtymoDark,
                textAlign = TextAlign.Center,
                lineHeight = 20.sp
            )
            Spacer(modifier = Modifier.height(3.dp))
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .background(EtymoDark.copy(alpha = 0.08f))
                    .padding(horizontal = 6.dp, vertical = 2.dp)
            ) {
                Text(
                    text = node.language,
                    fontSize = 10.sp,
                    color = EtymoDark.copy(alpha = 0.8f),
                    fontWeight = FontWeight.SemiBold,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

// ─── Info Dialog ─────────────────────────────────────────────────────────────

@Composable
fun NodeInfoDialog(
    node: WordNode,
    onDismiss: () -> Unit
) {
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(28.dp)
                .clickable { onDismiss() },
            contentAlignment = Alignment.Center
        ) {
            // Clay dialog card
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(
                        elevation = 16.dp,
                        shape = RoundedCornerShape(28.dp),
                        ambientColor = Color.Black.copy(alpha = 0.2f),
                        spotColor = Color.Black.copy(alpha = 0.2f)
                    )
                    .clip(RoundedCornerShape(28.dp))
                    .background(EtymoWhite)
                    .border(3.dp, EtymoOffWhite, RoundedCornerShape(28.dp))
                    .clickable(enabled = false) {}
            ) {
                Column(modifier = Modifier.padding(24.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = node.text,
                            fontSize = 26.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = EtymoDark,
                            modifier = Modifier.weight(1f)
                        )
                        if (node is WordNode.LeafNode) {
                            Spacer(modifier = Modifier.width(8.dp))
                            IconButton(
                                onClick = { /* TODO: TTS */ },
                                modifier = Modifier
                                    .size(40.dp)
                                    .clip(CircleShape)
                                    .background(EtymoPurple.copy(alpha = 0.1f))
                            ) {
                                Icon(Icons.Default.VolumeUp, contentDescription = "Play", tint = EtymoPurple)
                            }
                        }
                    }

                    Text(
                        text = node.translation,
                        fontSize = 15.sp,
                        color = EtymoPurple,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.padding(top = 2.dp, bottom = 14.dp)
                    )

                    HorizontalDivider(color = EtymoOffWhite, thickness = 2.dp)
                    Spacer(modifier = Modifier.height(14.dp))

                    val (accentColor, label, body) = when (node) {
                        is WordNode.RootNode  -> Triple(node.origin.color, "Historical Origin", node.historyBox)
                        is WordNode.LeafNode  -> Triple(EtymoGreen, "Did you know?", node.triviaPop)
                    }

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Info, contentDescription = null, tint = accentColor, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(label, fontSize = 13.sp, fontWeight = FontWeight.Bold, color = accentColor)
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(body, fontSize = 15.sp, color = EtymoDarkCard, lineHeight = 22.sp)

                    Spacer(modifier = Modifier.height(22.dp))

                    Button(
                        onClick = onDismiss,
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(containerColor = EtymoDark),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Text("Got it!", fontWeight = FontWeight.ExtraBold, color = EtymoYellow)
                    }
                }
            }
        }
    }
}
