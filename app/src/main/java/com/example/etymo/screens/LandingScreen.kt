package com.example.etymo.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.etymo.ui.theme.EtymoDark
import com.example.etymo.ui.theme.EtymoYellow
import com.example.etymo.ui.theme.EtymoYellowDeep
import com.example.etymo.ui.theme.EtymoWhite

@Composable
fun LandingScreen(
    onNavigateToAuth: (isSignUp: Boolean) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        EtymoWhite,
                        EtymoYellow.copy(alpha = 0.3f),
                        EtymoYellowDeep.copy(alpha = 0.8f)
                    )
                )
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Logo / Branding Area
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "ETYMO",
                        fontSize = 48.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = EtymoDark,
                        letterSpacing = 4.sp
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "Master languages organically.",
                        fontSize = 18.sp,
                        color = EtymoDark.copy(alpha = 0.8f),
                        textAlign = TextAlign.Center
                    )
                }
            }

            // Action Buttons
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Button(
                    onClick = { onNavigateToAuth(true) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(64.dp)
                        .clip(RoundedCornerShape(32.dp)),
                    colors = ButtonDefaults.buttonColors(containerColor = EtymoDark)
                ) {
                    Text(
                        text = "Get Started",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = EtymoWhite
                    )
                }

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(64.dp)
                        .clip(RoundedCornerShape(32.dp))
                        .background(EtymoWhite.copy(alpha = 0.8f))
                        .clickable { onNavigateToAuth(false) },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "I already have an account",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = EtymoDark
                    )
                }
                
                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}
