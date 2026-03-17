package com.example.etymo.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.etymo.ui.theme.EtymoDark
import com.example.etymo.ui.theme.EtymoDarkCard
import com.example.etymo.ui.theme.EtymoOffWhite
import com.example.etymo.ui.theme.EtymoYellow
import com.example.etymo.ui.theme.EtymoYellowDeep

@Composable
fun ScriptCharacterCard(
    char: String,
    romanized: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    onSpeakClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val bgColor = if (isSelected) EtymoYellow else EtymoOffWhite
    val borderColor = if (isSelected) EtymoYellow else EtymoOffWhite

    Box(
        modifier = modifier
            .widthIn(min = 72.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(bgColor)
            .border(
                width = 2.dp,
                color = borderColor,
                shape = RoundedCornerShape(16.dp)
            )
            .clickable(onClick = onClick)
            .padding(horizontal = 12.dp, vertical = 10.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = char,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Medium,
                    color = EtymoDark,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.width(4.dp))
                Box(
                    modifier = Modifier
                        .size(22.dp)
                        .clip(CircleShape)
                        .background(
                            if (isSelected) EtymoYellowDeep.copy(alpha = 0.3f)
                            else EtymoDarkCard.copy(alpha = 0.1f)
                        )
                        .clickable(onClick = onSpeakClick),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.VolumeUp,
                        contentDescription = "Pronounce",
                        tint = EtymoDark,
                        modifier = Modifier.size(14.dp)
                    )
                }
            }
            Text(
                text = romanized,
                fontSize = 11.sp,
                fontWeight = FontWeight.Normal,
                color = EtymoDarkCard,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = 2.dp)
            )
        }
    }
}
