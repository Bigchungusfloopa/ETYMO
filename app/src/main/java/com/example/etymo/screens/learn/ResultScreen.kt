package com.example.etymo.screens.learn

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.etymo.ui.components.PeacockMascot
import com.example.etymo.ui.components.rememberFeedbackManager
import com.example.etymo.ui.theme.*

@Composable
fun ResultScreen(
    correctCount: Int,
    totalQuestions: Int,
    xpEarned: Int,
    onContinue: () -> Unit,
    onTryAgain: () -> Unit
) {
    val percentage = if (totalQuestions > 0) (correctCount * 100) / totalQuestions else 0
    val stars = when {
        percentage >= 90 -> 3
        percentage >= 60 -> 2
        percentage >= 30 -> 1
        else -> 0
    }

    // Entrance animation
    val scaleAnim by animateFloatAsState(
        targetValue = 1f,
        animationSpec = spring(dampingRatio = 0.5f, stiffness = 200f),
        label = "resultScale"
    )

    val feedback = rememberFeedbackManager()
    LaunchedEffect(Unit) {
        if (percentage >= 30) {
            feedback.playComplete()
        } else {
            feedback.playWrong() // Optional logic, or just playComplete normally. Wait, playComplete is celebratory. Just playComplete! Let's just do playComplete.
        }
    }

    // XP counter animation
    var displayedXp by remember { mutableIntStateOf(0) }
    LaunchedEffect(xpEarned) {
        val steps = 30
        val delay = 30L
        for (i in 1..steps) {
            displayedXp = (xpEarned * i) / steps
            kotlinx.coroutines.delay(delay)
        }
        displayedXp = xpEarned
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(EtymoOffWhite)
            .padding(horizontal = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Mascot
        PeacockMascot(
            size = 180.dp,
            modifier = Modifier.scale(scaleAnim)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Title
        Text(
            text = when {
                percentage >= 90 -> "Amazing! 🎉"
                percentage >= 60 -> "Well done! 👏"
                percentage >= 30 -> "Keep going! 💪"
                else -> "Try again! 📚"
            },
            fontSize = 28.sp,
            fontWeight = FontWeight.ExtraBold,
            color = EtymoDark
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Stars
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            repeat(3) { i ->
                val starScale by animateFloatAsState(
                    targetValue = if (i < stars) 1.2f else 0.9f,
                    animationSpec = spring(
                        dampingRatio = 0.4f,
                        stiffness = 300f
                    ),
                    label = "star$i"
                )
                Icon(
                    imageVector = if (i < stars) Icons.Default.Star else Icons.Default.StarBorder,
                    contentDescription = null,
                    tint = if (i < stars) EtymoYellow else EtymoLocked,
                    modifier = Modifier
                        .size(48.dp)
                        .scale(starScale)
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Stats card
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .shadow(8.dp, RoundedCornerShape(24.dp))
                .clip(RoundedCornerShape(24.dp))
                .background(EtymoWhite)
                .padding(24.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                StatItem(
                    value = "$correctCount/$totalQuestions",
                    label = "Score",
                    color = EtymoCorrect
                )
                StatItem(
                    value = "$percentage%",
                    label = "Accuracy",
                    color = EtymoBlue
                )
                StatItem(
                    value = "+$displayedXp",
                    label = "XP Earned",
                    color = EtymoYellowDark
                )
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Continue button
        Button(
            onClick = onContinue,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(containerColor = EtymoCorrect)
        ) {
            Text(
                "CONTINUE",
                fontWeight = FontWeight.ExtraBold,
                fontSize = 16.sp,
                color = EtymoWhite
            )
        }

        // Try Again button (only show if not perfect)
        if (percentage < 100) {
            Spacer(modifier = Modifier.height(12.dp))
            OutlinedButton(
                onClick = onTryAgain,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.outlinedButtonColors(contentColor = EtymoDark)
            ) {
                Text(
                    "TRY AGAIN",
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 16.sp
                )
            }
        }
    }
}

@Composable
private fun StatItem(
    value: String,
    label: String,
    color: androidx.compose.ui.graphics.Color
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box(
            modifier = Modifier
                .size(56.dp)
                .clip(CircleShape)
                .background(color.copy(alpha = 0.12f)),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = value,
                fontSize = 16.sp,
                fontWeight = FontWeight.ExtraBold,
                color = color,
                textAlign = TextAlign.Center
            )
        }
        Spacer(modifier = Modifier.height(6.dp))
        Text(
            text = label,
            fontSize = 12.sp,
            color = EtymoDarkCard.copy(alpha = 0.7f),
            fontWeight = FontWeight.Medium
        )
    }
}
