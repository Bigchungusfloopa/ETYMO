package com.example.etymo.screens.learn

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.etymo.data.local.Section
import com.example.etymo.ui.theme.*

import com.example.etymo.ui.components.PeacockMascot

@Composable
fun SectionListScreen(
    language: String,
    sections: List<Section>,
    onSectionSelected: (Int) -> Unit,
    onBack: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(EtymoOffWhite)
    ) {
        // Top bar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 24.dp, top = 48.dp, bottom = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBack) {
                Icon(
                    Icons.Default.ArrowBack,
                    contentDescription = "Back",
                    tint = EtymoDark
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = language,
                    fontSize = 26.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = EtymoDark
                )
                Text(
                    text = "${sections.size} sections available",
                    fontSize = 14.sp,
                    color = EtymoDarkCard.copy(alpha = 0.6f)
                )
            }
            PeacockMascot(
                isAnimating = false,
                frozenProgress = 0.25f, // slightly perked up
                size = 72.dp
            )
        }

        LazyColumn(
            contentPadding = PaddingValues(horizontal = 24.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.padding(bottom = 100.dp)
        ) {
            itemsIndexed(sections) { index, section ->
                val isLocked = index > 0 // basic: first is always unlocked
                SectionCard(
                    section = section,
                    index = index,
                    isLocked = isLocked,
                    onClick = { if (!isLocked) onSectionSelected(index) }
                )
            }
        }
    }
}

@Composable
private fun SectionCard(
    section: Section,
    index: Int,
    isLocked: Boolean,
    onClick: () -> Unit
) {
    val sectionColors = listOf(
        EtymoCorrect,
        EtymoYellow,
        EtymoBlue,
        EtymoPurple,
        EtymoOrange
    )
    val accentColor = sectionColors[index % sectionColors.size]

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .alpha(if (isLocked) 0.5f else 1f)
            .shadow(
                elevation = 10.dp,
                shape = RoundedCornerShape(24.dp),
                spotColor = accentColor.copy(alpha = 0.2f)
            )
            .clip(RoundedCornerShape(24.dp))
            .background(EtymoWhite)
            .clickable(enabled = !isLocked) { onClick() }
            .padding(24.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Section number badge
            Box(
                modifier = Modifier
                    .size(56.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(accentColor.copy(alpha = 0.15f)),
                contentAlignment = Alignment.Center
            ) {
                if (isLocked) {
                    Icon(
                        Icons.Default.Lock,
                        contentDescription = "Locked",
                        tint = EtymoLocked,
                        modifier = Modifier.size(28.dp)
                    )
                } else {
                    Text(
                        text = "${index + 1}",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = accentColor
                    )
                }
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = section.title,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = EtymoDark
                )
                Text(
                    text = "${section.units.size} units",
                    fontSize = 14.sp,
                    color = EtymoDarkCard.copy(alpha = 0.6f),
                    modifier = Modifier.padding(top = 2.dp)
                )
            }

            if (!isLocked) {
                Box(
                    modifier = Modifier
                        .size(44.dp)
                        .clip(RoundedCornerShape(14.dp))
                        .background(accentColor),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        Icons.Default.PlayArrow,
                        contentDescription = "Start",
                        tint = EtymoWhite,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
        }
    }
}
