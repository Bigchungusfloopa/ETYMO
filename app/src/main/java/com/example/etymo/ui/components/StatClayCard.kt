package com.example.etymo.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.animation.animateContentSize
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.etymo.ui.theme.EtymoDark
import com.example.etymo.ui.theme.EtymoDarkCard
import com.example.etymo.ui.theme.EtymoYellow
import com.example.etymo.ui.theme.EtymoYellowDark

@Composable
fun StatClayCard(
    modifier: Modifier = Modifier,
    title: String,
    value: String,
    icon: ImageVector,
    iconTint: Color,
    isLarge: Boolean = false,
    content: @Composable ColumnScope.() -> Unit = {}
) {
    ClayCard(
        modifier = modifier.animateContentSize(),
        backgroundColor = EtymoYellow,
        lightShadowColor = Color.White.copy(alpha = 0.8f),
        darkShadowColor = EtymoYellowDark.copy(alpha = 0.5f),
        outerShadowColor = EtymoYellowDark.copy(alpha = 0.3f),
        cornerRadius = 24.dp
    ) {
        Column(
            modifier = Modifier.padding(if (isLarge) 24.dp else 20.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(if (isLarge) 48.dp else 40.dp)
                    .clip(CircleShape)
                    .background(Color.White.copy(alpha = 0.9f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = iconTint,
                    modifier = Modifier.size(if (isLarge) 28.dp else 24.dp)
                )
            }
            Column {
                Text(
                    text = value,
                    fontSize = if (isLarge) 28.sp else 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = EtymoDark
                )
                Text(
                    text = title,
                    fontSize = if (isLarge) 16.sp else 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = EtymoDarkCard
                )
            }
            content()
        }
    }
}
