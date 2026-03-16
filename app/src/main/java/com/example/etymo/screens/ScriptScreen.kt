package com.example.etymo.screens

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.NavigateNext
import androidx.compose.material.icons.filled.Undo
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.etymo.ui.components.ClayCard
import com.example.etymo.ui.components.DrawingCanvas
import com.example.etymo.ui.components.ScriptCharacterCard
import com.example.etymo.ui.theme.*

@Composable
fun ScriptScreen() {
    var selectedScriptIndex by remember { mutableIntStateOf(0) }
    var selectedCharIndex by remember { mutableIntStateOf(0) }
    var strokes by remember { mutableStateOf(listOf<List<Offset>>()) }
    var currentStroke by remember { mutableStateOf(listOf<Offset>()) }

    val scripts = availableScripts
    val currentScript = scripts[selectedScriptIndex]
    val currentChar = currentScript.characters.getOrNull(selectedCharIndex)

    // Reset character index when switching scripts
    LaunchedEffect(selectedScriptIndex) {
        selectedCharIndex = 0
        strokes = emptyList()
        currentStroke = emptyList()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(EtymoOffWhite)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp)
                .padding(top = 48.dp, bottom = 120.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            // Header
            Text(
                text = "Practice Scripts",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = EtymoDark
            )

            // Script selector chips
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                contentPadding = PaddingValues(end = 8.dp)
            ) {
                items(scripts.size) { index ->
                    val script = scripts[index]
                    val isSelected = index == selectedScriptIndex
                    val bgColor by animateColorAsState(
                        targetValue = if (isSelected) EtymoYellow else Color.White,
                        label = "chipBg"
                    )

                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(20.dp))
                            .background(bgColor)
                            .clickable {
                                selectedScriptIndex = index
                            }
                            .padding(horizontal = 16.dp, vertical = 10.dp)
                    ) {
                        Text(
                            text = "${script.emoji}  ${script.name}",
                            fontSize = 14.sp,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                            color = EtymoDark
                        )
                    }
                }
            }

            // Character grid as horizontal scroll
            Column {
                Text(
                    text = "Characters",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = EtymoDark,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    state = rememberLazyListState()
                ) {
                    items(currentScript.characters) { char ->
                        val charIndex = currentScript.characters.indexOf(char)
                        ScriptCharacterCard(
                            char = char.char,
                            romanized = char.romanized,
                            isSelected = charIndex == selectedCharIndex,
                            onClick = {
                                selectedCharIndex = charIndex
                                strokes = emptyList()
                                currentStroke = emptyList()
                            }
                        )
                    }
                }
            }

            // Drawing Canvas
            Column(modifier = Modifier.weight(1f)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Draw Here",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = EtymoDark
                    )
                    if (currentChar != null) {
                        Text(
                            text = "${currentChar.char}  (${currentChar.romanized})",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium,
                            color = EtymoDarkCard
                        )
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))

                // Combine completed strokes with the in-progress stroke
                val allStrokes = remember(strokes, currentStroke) {
                    if (currentStroke.isNotEmpty()) strokes + listOf(currentStroke) else strokes
                }

                DrawingCanvas(
                    strokes = allStrokes,
                    referenceChar = currentChar?.char ?: "",
                    onStrokeStart = { offset ->
                        currentStroke = listOf(offset)
                    },
                    onStrokeDrag = { offset ->
                        currentStroke = currentStroke + offset
                    },
                    onStrokeEnd = {
                        if (currentStroke.isNotEmpty()) {
                            strokes = strokes + listOf(currentStroke)
                            currentStroke = emptyList()
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                )
            }

            // Controls Row
            ClayCard(
                modifier = Modifier.fillMaxWidth(),
                backgroundColor = Color.White,
                cornerRadius = 20.dp
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp, vertical = 6.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Undo last stroke
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(14.dp))
                            .clickable {
                                if (strokes.isNotEmpty()) {
                                    strokes = strokes.dropLast(1)
                                }
                            }
                            .padding(horizontal = 16.dp, vertical = 10.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Undo,
                                contentDescription = "Undo",
                                tint = EtymoDark,
                                modifier = Modifier.size(20.dp)
                            )
                            Text(
                                text = "Undo",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Medium,
                                color = EtymoDark
                            )
                        }
                    }

                    // Clear canvas
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(14.dp))
                            .background(EtymoRed.copy(alpha = 0.1f))
                            .clickable {
                                strokes = emptyList()
                                currentStroke = emptyList()
                            }
                            .padding(horizontal = 16.dp, vertical = 10.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = "Clear",
                                tint = EtymoRed,
                                modifier = Modifier.size(20.dp)
                            )
                            Text(
                                text = "Clear",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Medium,
                                color = EtymoRed
                            )
                        }
                    }

                    // Next character
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(14.dp))
                            .background(EtymoYellow)
                            .clickable {
                                val nextIndex = selectedCharIndex + 1
                                if (nextIndex < currentScript.characters.size) {
                                    selectedCharIndex = nextIndex
                                } else {
                                    selectedCharIndex = 0
                                }
                                strokes = emptyList()
                                currentStroke = emptyList()
                            }
                            .padding(horizontal = 16.dp, vertical = 10.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Text(
                                text = "Next",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = EtymoDark
                            )
                            Icon(
                                imageVector = Icons.Default.NavigateNext,
                                contentDescription = "Next",
                                tint = EtymoDark,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}
