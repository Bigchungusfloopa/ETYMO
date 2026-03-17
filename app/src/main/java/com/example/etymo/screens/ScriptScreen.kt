package com.example.etymo.screens

import android.speech.tts.TextToSpeech
import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.NavigateNext
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Undo
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.etymo.ui.components.ClayCard
import com.example.etymo.ui.components.DrawingCanvas
import com.example.etymo.ui.components.ScriptCharacterCard
import com.example.etymo.ui.components.TracingAccuracyChecker
import com.example.etymo.ui.theme.*
import kotlinx.coroutines.delay
import android.graphics.Bitmap
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.TextButton

// Result state after checking accuracy
enum class TracingResult { NONE, CORRECT, TRY_AGAIN }

enum class CameraFlowState { IDLE, INTRO, ANALYZING, RESULT }

@Composable
fun ScriptScreen() {
    val context = LocalContext.current

    // TTS engine
    var ttsReady by remember { mutableStateOf(false) }
    val tts = remember {
        var engine: TextToSpeech? = null
        engine = TextToSpeech(context) { status ->
            ttsReady = status == TextToSpeech.SUCCESS
        }
        engine
    }

    DisposableEffect(Unit) {
        onDispose { tts?.shutdown() }
    }

    // Camera launcher for dummy translation flow
    var cameraFlowState by remember { mutableStateOf(CameraFlowState.IDLE) }
    
    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicturePreview()
    ) { bitmap: Bitmap? ->
        if (bitmap != null) {
            cameraFlowState = CameraFlowState.ANALYZING
        } else {
            cameraFlowState = CameraFlowState.IDLE
        }
    }
    
    // Simulate AI processing
    LaunchedEffect(cameraFlowState) {
        if (cameraFlowState == CameraFlowState.ANALYZING) {
            delay(2000)
            cameraFlowState = CameraFlowState.RESULT
        }
    }

    var selectedScriptIndex by remember { mutableIntStateOf(0) }
    var selectedCharIndex by remember { mutableIntStateOf(0) }
    var strokes by remember { mutableStateOf(listOf<List<Offset>>()) }
    var currentStroke by remember { mutableStateOf(listOf<Offset>()) }
    var tracingResult by remember { mutableStateOf(TracingResult.NONE) }
    var canvasSize by remember { mutableStateOf(IntSize.Zero) }

    val scripts = availableScripts
    val currentScript = scripts[selectedScriptIndex]
    val currentChar = currentScript.characters.getOrNull(selectedCharIndex)

    // Set TTS locale when script changes
    LaunchedEffect(selectedScriptIndex, ttsReady) {
        if (ttsReady && tts != null) {
            tts.language = currentScript.locale
        }
    }

    // Reset state when switching scripts
    LaunchedEffect(selectedScriptIndex) {
        selectedCharIndex = 0
        strokes = emptyList()
        currentStroke = emptyList()
        tracingResult = TracingResult.NONE
    }

    // Auto-dismiss the result after 2 seconds
    LaunchedEffect(tracingResult) {
        if (tracingResult != TracingResult.NONE) {
            delay(2000)
            if (tracingResult == TracingResult.CORRECT) {
                // Auto advance on correct
                val nextIndex = selectedCharIndex + 1
                selectedCharIndex = if (nextIndex < currentScript.characters.size) nextIndex else 0
                strokes = emptyList()
                currentStroke = emptyList()
            }
            tracingResult = TracingResult.NONE
        }
    }

    fun speakChar(char: String) {
        if (ttsReady && tts != null) {
            tts.language = currentScript.locale
            tts.speak(char, TextToSpeech.QUEUE_FLUSH, null, "char_$char")
        }
    }

    fun checkAccuracy() {
        if (strokes.isEmpty() || currentChar == null) return
        if (canvasSize.width == 0 || canvasSize.height == 0) return

        val isCorrect = TracingAccuracyChecker.isAccurate(
            referenceChar = currentChar.char,
            strokes = strokes,
            canvasWidth = canvasSize.width.toFloat(),
            canvasHeight = canvasSize.height.toFloat(),
            threshold = 0.05f // Very relaxed strictness
        )
        tracingResult = if (isCorrect) TracingResult.CORRECT else TracingResult.TRY_AGAIN
    }

    // AI Translation Flow Dialogs
    when (cameraFlowState) {
        CameraFlowState.INTRO -> {
            AlertDialog(
                onDismissRequest = { cameraFlowState = CameraFlowState.IDLE },
                title = { Text("Translate (Demo)", fontWeight = FontWeight.Bold, color = EtymoDark) },
                text = { Text("Translate native language sign boards into English using the camera.", color = EtymoDarkCard) },
                confirmButton = {
                    TextButton(onClick = { cameraLauncher.launch(null) }) {
                        Text("Open Camera", color = EtymoYellowDeep, fontWeight = FontWeight.Bold)
                    }
                },
                dismissButton = {
                    TextButton(onClick = { cameraFlowState = CameraFlowState.IDLE }) {
                        Text("Cancel", color = EtymoDark)
                    }
                },
                containerColor = Color.White,
                shape = RoundedCornerShape(16.dp)
            )
        }
        CameraFlowState.ANALYZING -> {
            AlertDialog(
                onDismissRequest = { /* Cannot dismiss while analyzing */ },
                title = { Text("Translating...", fontWeight = FontWeight.Bold, color = EtymoDark) },
                text = { 
                    Row(
                        modifier = Modifier.fillMaxWidth(), 
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        CircularProgressIndicator(color = EtymoYellowDeep)
                    }
                },
                confirmButton = { },
                containerColor = Color.White,
                shape = RoundedCornerShape(16.dp)
            )
        }
        CameraFlowState.RESULT -> {
            AlertDialog(
                onDismissRequest = { cameraFlowState = CameraFlowState.IDLE },
                title = { Text("AI Translation (Demo)", fontWeight = FontWeight.Bold, color = EtymoDark) },
                text = { Text("Result: \"Welcome to our shop!\"", color = EtymoDarkCard) },
                confirmButton = {
                    TextButton(onClick = { cameraFlowState = CameraFlowState.IDLE }) {
                        Text("OK", color = EtymoYellowDeep, fontWeight = FontWeight.Bold)
                    }
                },
                containerColor = Color.White,
                shape = RoundedCornerShape(16.dp)
            )
        }
        CameraFlowState.IDLE -> {}
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
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Practice Scripts",
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    color = EtymoDark
                )
                
                // Camera Translate Button
                Box(
                    modifier = Modifier
                        .size(44.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(EtymoYellow.copy(alpha = 0.2f))
                        .clickable { cameraFlowState = CameraFlowState.INTRO },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.CameraAlt,
                        contentDescription = "Translate with AI",
                        tint = EtymoDark,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }

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

            // Character grid
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
                                tracingResult = TracingResult.NONE
                            },
                            onSpeakClick = {
                                speakChar(char.char)
                            }
                        )
                    }
                }
            }

            // Drawing Canvas header
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
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = "${currentChar.char}  (${currentChar.romanized})",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium,
                            color = EtymoDarkCard
                        )
                        // Inline speaker button for the currently selected character
                        Box(
                            modifier = Modifier
                                .size(28.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .background(EtymoYellow.copy(alpha = 0.3f))
                                .clickable { speakChar(currentChar.char) },
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.VolumeUp,
                                contentDescription = "Pronounce",
                                tint = EtymoDark,
                                modifier = Modifier.size(16.dp)
                            )
                        }
                    }
                }
            }

            // Combine completed strokes with the in-progress stroke
            val allStrokes = remember(strokes, currentStroke) {
                if (currentStroke.isNotEmpty()) strokes + listOf(currentStroke) else strokes
            }

            // Drawing canvas with overlay
            Box(modifier = Modifier.weight(1f)) {
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
                        .fillMaxSize()
                        .onSizeChanged { size -> canvasSize = size }
                )

                // Result overlay
                androidx.compose.animation.AnimatedVisibility(
                    visible = tracingResult != TracingResult.NONE,
                    enter = fadeIn(tween(300)) + scaleIn(tween(300)),
                    exit = fadeOut(tween(300)) + scaleOut(tween(300)),
                    modifier = Modifier.align(Alignment.Center)
                ) {
                    val isCorrect = tracingResult == TracingResult.CORRECT
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(20.dp))
                            .background(
                                if (isCorrect) EtymoGreen.copy(alpha = 0.9f)
                                else EtymoRed.copy(alpha = 0.9f)
                            )
                            .padding(horizontal = 32.dp, vertical = 20.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            Icon(
                                imageVector = if (isCorrect) Icons.Default.CheckCircle
                                else Icons.Default.Refresh,
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier.size(28.dp)
                            )
                            Text(
                                text = if (isCorrect) "Correct!" else "Try Again",
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                        }
                    }
                }
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
                        .padding(horizontal = 4.dp, vertical = 6.dp),
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
                                tracingResult = TracingResult.NONE
                            }
                            .padding(horizontal = 12.dp, vertical = 10.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Undo,
                                contentDescription = "Undo",
                                tint = EtymoDark,
                                modifier = Modifier.size(18.dp)
                            )
                            Text("Undo", fontSize = 13.sp, fontWeight = FontWeight.Medium, color = EtymoDark)
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
                                tracingResult = TracingResult.NONE
                            }
                            .padding(horizontal = 12.dp, vertical = 10.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = "Clear",
                                tint = EtymoRed,
                                modifier = Modifier.size(18.dp)
                            )
                            Text("Clear", fontSize = 13.sp, fontWeight = FontWeight.Medium, color = EtymoRed)
                        }
                    }

                    // Check accuracy
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(14.dp))
                            .background(EtymoGreen.copy(alpha = 0.15f))
                            .clickable { checkAccuracy() }
                            .padding(horizontal = 12.dp, vertical = 10.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.CheckCircle,
                                contentDescription = "Check",
                                tint = EtymoGreen,
                                modifier = Modifier.size(18.dp)
                            )
                            Text("Check", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = EtymoGreen)
                        }
                    }

                    // Next character
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(14.dp))
                            .background(EtymoYellow)
                            .clickable {
                                val nextIndex = selectedCharIndex + 1
                                selectedCharIndex = if (nextIndex < currentScript.characters.size) nextIndex else 0
                                strokes = emptyList()
                                currentStroke = emptyList()
                                tracingResult = TracingResult.NONE
                            }
                            .padding(horizontal = 12.dp, vertical = 10.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Text("Next", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = EtymoDark)
                            Icon(
                                imageVector = Icons.Default.NavigateNext,
                                contentDescription = "Next",
                                tint = EtymoDark,
                                modifier = Modifier.size(18.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}
