package com.example.etymo.screens.learn

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
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
import com.example.etymo.data.local.LanguageInfo
import com.example.etymo.data.local.LessonRepository
import com.example.etymo.ui.components.PeacockMascot
import com.example.etymo.ui.theme.*

@Composable
fun LanguageSelectionScreen(
    onLanguageSelected: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(EtymoOffWhite)
            .padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(48.dp))

        // Mascot - Prominent and animating
        PeacockMascot(
            size = 200.dp
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Choose a Language",
            fontSize = 28.sp,
            fontWeight = FontWeight.ExtraBold,
            color = EtymoDark
        )

        Text(
            text = "Start your journey today!",
            fontSize = 15.sp,
            color = EtymoDarkCard.copy(alpha = 0.7f),
            modifier = Modifier.padding(top = 4.dp, bottom = 24.dp)
        )

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(bottom = 120.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(LessonRepository.supportedLanguages) { lang ->
                LanguageCard(
                    language = lang,
                    onClick = { onLanguageSelected(lang.name) }
                )
            }
        }
    }
}

@Composable
private fun LanguageCard(
    language: LanguageInfo,
    onClick: () -> Unit
) {
    var pressed by remember { mutableStateOf(false) }
    val scale by animateFloatAsState(
        targetValue = if (pressed) 0.95f else 1f,
        animationSpec = tween(100),
        label = "scale"
    )

    Box(
        modifier = Modifier
            .scale(scale)
            .shadow(
                elevation = 8.dp,
                shape = RoundedCornerShape(24.dp),
                spotColor = EtymoDark.copy(alpha = 0.1f)
            )
            .clip(RoundedCornerShape(24.dp))
            .background(EtymoWhite)
            .clickable {
                pressed = true
                onClick()
            }
            .padding(20.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = language.flag,
                fontSize = 36.sp
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = language.name,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = EtymoDark
            )
            Text(
                text = language.nativeName,
                fontSize = 14.sp,
                color = EtymoDarkCard.copy(alpha = 0.7f),
                fontWeight = FontWeight.Medium
            )
            Spacer(modifier = Modifier.height(8.dp))
            val sectionCount = LessonRepository.getSections(language.name).size
            val unitCount = LessonRepository.getSections(language.name).sumOf { it.units.size }
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(12.dp))
                    .background(EtymoYellow.copy(alpha = 0.2f))
                    .padding(horizontal = 10.dp, vertical = 4.dp)
            ) {
                Text(
                    text = "$unitCount lessons",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = EtymoYellowDark,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}
