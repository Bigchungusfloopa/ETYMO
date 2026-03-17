package com.example.etymo.screens.learn

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.etymo.data.local.Question
import com.example.etymo.data.local.QuestionType
import com.example.etymo.ui.components.PeacockMascot
import com.example.etymo.ui.components.rememberFeedbackManager
import com.example.etymo.ui.theme.*

@Composable
fun QuizScreen(
    question: Question,
    questionIndex: Int,
    totalQuestions: Int,
    hearts: Int,
    streak: Int,
    selectedAnswer: String?,
    jumbleSelectedWords: List<String>,
    matchSelectedPairs: Map<String, String>,
    answerChecked: Boolean,
    isCorrect: Boolean,
    onSelectAnswer: (String) -> Unit,
    onToggleJumbleWord: (String) -> Unit,
    onSelectMatchPair: (String, String) -> Unit,
    onCheckAnswer: () -> Unit,
    onNextQuestion: () -> Unit,
    onClose: () -> Unit
) {
    val progress = (questionIndex + 1).toFloat() / totalQuestions.toFloat()
    val animatedProgress by animateFloatAsState(
        targetValue = progress,
        animationSpec = tween(500, easing = EaseOutCubic),
        label = "progress"
    )

    // Feedback background flash
    val bgColor by animateColorAsState(
        targetValue = when {
            answerChecked && isCorrect -> EtymoCorrect.copy(alpha = 0.08f)
            answerChecked && !isCorrect -> EtymoWrong.copy(alpha = 0.08f)
            else -> EtymoOffWhite
        },
        animationSpec = tween(300),
        label = "bg"
    )

    val feedback = rememberFeedbackManager()

    LaunchedEffect(answerChecked) {
        if (answerChecked) {
            if (isCorrect) feedback.playCorrect()
            else feedback.playWrong()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(bgColor)
            .padding(horizontal = 24.dp)
    ) {
        Spacer(modifier = Modifier.height(48.dp))

        // Top bar: close, progress, hearts
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onClose) {
                Icon(Icons.Default.Close, contentDescription = "Close", tint = EtymoDark)
            }

            // Progress bar
            Box(
                modifier = Modifier
                    .weight(1f)
                    .height(12.dp)
                    .clip(RoundedCornerShape(6.dp))
                    .background(EtymoProgressBg)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .fillMaxWidth(animatedProgress)
                        .clip(RoundedCornerShape(6.dp))
                        .background(EtymoCorrect)
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            // Hearts
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    Icons.Default.Favorite,
                    contentDescription = "Hearts",
                    tint = EtymoWrong,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "$hearts",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = EtymoWrong
                )
            }
        }

        // Streak indicator
        if (streak > 1) {
            Spacer(modifier = Modifier.height(8.dp))
            Box(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .clip(RoundedCornerShape(12.dp))
                    .background(EtymoOrange.copy(alpha = 0.15f))
                    .padding(horizontal = 12.dp, vertical = 4.dp)
            ) {
                Text(
                    text = "🔥 $streak streak!",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    color = EtymoOrange
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Question
        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
        ) {
            // Mascot reflecting current state
            PeacockMascot(
                isAnimating = false,
                frozenProgress = when {
                    answerChecked && isCorrect -> 0.75f // celebrating
                    answerChecked && !isCorrect -> 0.0f // sad/neutral
                    else -> 0.25f // thinking/perked up
                },
                size = 120.dp,
                modifier = Modifier.align(Alignment.CenterHorizontally).padding(bottom = 16.dp)
            )

            Text(
                text = question.question,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = EtymoDark,
                lineHeight = 30.sp
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Dynamic question type UI
            when (question.type) {
                QuestionType.MCQ, QuestionType.PRONUNCIATION -> {
                    McqOptions(
                        options = question.options ?: emptyList(),
                        selectedAnswer = selectedAnswer,
                        correctAnswer = question.correctAnswer,
                        answerChecked = answerChecked,
                        onSelect = { 
                            feedback.playTap()
                            onSelectAnswer(it) 
                        }
                    )
                }
                QuestionType.WORD_JUMBLE -> {
                    JumbleUI(
                        words = question.options ?: emptyList(),
                        selectedWords = jumbleSelectedWords,
                        correctAnswer = question.correctAnswer,
                        answerChecked = answerChecked,
                        onToggleWord = { 
                            feedback.playTap()
                            onToggleJumbleWord(it) 
                        }
                    )
                }
                QuestionType.MATCH -> {
                    MatchUI(
                        pairs = question.matchPairs ?: emptyList(),
                        selectedPairs = matchSelectedPairs,
                        answerChecked = answerChecked,
                        onSelectPair = { l, r -> 
                            feedback.playTap()
                            onSelectMatchPair(l, r) 
                        }
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Feedback message
            if (answerChecked) {
                FeedbackBanner(isCorrect = isCorrect, correctAnswer = question.correctAnswer)
            }
        }

        // Bottom buttons
        Spacer(modifier = Modifier.height(8.dp))

        if (!answerChecked) {
            val canCheck = when (question.type) {
                QuestionType.MCQ, QuestionType.PRONUNCIATION -> selectedAnswer != null
                QuestionType.WORD_JUMBLE -> jumbleSelectedWords.isNotEmpty()
                QuestionType.MATCH -> {
                    val expected = question.matchPairs?.size ?: 0
                    matchSelectedPairs.size >= expected
                }
            }

            Button(
                onClick = onCheckAnswer,
                enabled = canCheck,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = EtymoCorrect,
                    disabledContainerColor = EtymoProgressBg
                )
            ) {
                Text(
                    "CHECK",
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 16.sp,
                    color = if (canCheck) EtymoWhite else EtymoLocked
                )
            }
        } else {
            Button(
                onClick = onNextQuestion,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (isCorrect) EtymoCorrect else EtymoWrong
                )
            ) {
                Text(
                    "CONTINUE",
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 16.sp,
                    color = EtymoWhite
                )
            }
        }

        Spacer(modifier = Modifier.height(100.dp))
    }
}

// ─── MCQ Options ────────────────────────────────────────────────────────────

@Composable
private fun McqOptions(
    options: List<String>,
    selectedAnswer: String?,
    correctAnswer: String?,
    answerChecked: Boolean,
    onSelect: (String) -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        options.forEach { option ->
            val isSelected = option == selectedAnswer
            val isCorrectOption = option == correctAnswer

            val borderColor = when {
                answerChecked && isCorrectOption -> EtymoCorrect
                answerChecked && isSelected && !isCorrectOption -> EtymoWrong
                isSelected -> EtymoYellow
                else -> EtymoProgressBg
            }

            val bgColor = when {
                answerChecked && isCorrectOption -> EtymoCorrect.copy(alpha = 0.1f)
                answerChecked && isSelected && !isCorrectOption -> EtymoWrong.copy(alpha = 0.1f)
                isSelected -> EtymoYellow.copy(alpha = 0.1f)
                else -> EtymoWhite
            }

            // Scale animation on selection
            val scale by animateFloatAsState(
                targetValue = if (isSelected && !answerChecked) 1.02f else 1f,
                animationSpec = spring(dampingRatio = 0.6f),
                label = "optionScale"
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .scale(scale)
                    .shadow(
                        elevation = if (isSelected) 6.dp else 2.dp,
                        shape = RoundedCornerShape(16.dp),
                        spotColor = borderColor.copy(alpha = 0.2f)
                    )
                    .clip(RoundedCornerShape(16.dp))
                    .background(bgColor)
                    .border(2.dp, borderColor, RoundedCornerShape(16.dp))
                    .clickable(enabled = !answerChecked) { onSelect(option) }
                    .padding(horizontal = 20.dp, vertical = 16.dp)
            ) {
                Text(
                    text = option,
                    fontSize = 17.sp,
                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                    color = EtymoDark
                )
            }
        }
    }
}

// ─── Jumble UI ──────────────────────────────────────────────────────────────

@Composable
private fun JumbleUI(
    words: List<String>,
    selectedWords: List<String>,
    correctAnswer: String?,
    answerChecked: Boolean,
    onToggleWord: (String) -> Unit
) {
    Column {
        // Assembled sentence area
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(64.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(EtymoWhite)
                .border(
                    2.dp,
                    when {
                        answerChecked && selectedWords.joinToString(" ") == correctAnswer?.trim() -> EtymoCorrect
                        answerChecked -> EtymoWrong
                        else -> EtymoProgressBg
                    },
                    RoundedCornerShape(16.dp)
                )
                .padding(horizontal = 16.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            if (selectedWords.isEmpty()) {
                Text(
                    text = "Tap words to arrange...",
                    color = EtymoLocked,
                    fontSize = 15.sp
                )
            } else {
                Text(
                    text = selectedWords.joinToString(" "),
                    fontSize = 17.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = EtymoDark
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Word chips
        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            words.forEach { word ->
                val isSelected = selectedWords.contains(word)
                val chipScale by animateFloatAsState(
                    targetValue = if (isSelected) 0.9f else 1f,
                    animationSpec = spring(dampingRatio = 0.7f),
                    label = "chipScale"
                )

                Box(
                    modifier = Modifier
                        .scale(chipScale)
                        .clip(RoundedCornerShape(14.dp))
                        .background(
                            if (isSelected) EtymoYellow.copy(alpha = 0.3f)
                            else EtymoWhite
                        )
                        .border(
                            1.5.dp,
                            if (isSelected) EtymoYellow else EtymoProgressBg,
                            RoundedCornerShape(14.dp)
                        )
                        .clickable(enabled = !answerChecked) { onToggleWord(word) }
                        .padding(horizontal = 16.dp, vertical = 10.dp)
                ) {
                    Text(
                        text = word,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = if (isSelected) EtymoYellowDark else EtymoDark
                    )
                }
            }
        }
    }
}

// ─── Match UI ───────────────────────────────────────────────────────────────

@Composable
private fun MatchUI(
    pairs: List<Pair<String, String>>,
    selectedPairs: Map<String, String>,
    answerChecked: Boolean,
    onSelectPair: (String, String) -> Unit
) {
    var selectedLeft by remember { mutableStateOf<String?>(null) }

    Column {
        Text(
            text = "Tap to match pairs",
            fontSize = 14.sp,
            color = EtymoDarkCard.copy(alpha = 0.6f),
            modifier = Modifier.padding(bottom = 12.dp)
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            // Left column
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                pairs.forEach { (left, _) ->
                    val isMatched = selectedPairs.containsKey(left)
                    val isActive = selectedLeft == left

                    Box(
                        modifier = Modifier
                            .width(140.dp)
                            .clip(RoundedCornerShape(14.dp))
                            .background(
                                when {
                                    isMatched -> EtymoCorrect.copy(alpha = 0.15f)
                                    isActive -> EtymoYellow.copy(alpha = 0.2f)
                                    else -> EtymoWhite
                                }
                            )
                            .border(
                                2.dp,
                                when {
                                    isMatched -> EtymoCorrect
                                    isActive -> EtymoYellow
                                    else -> EtymoProgressBg
                                },
                                RoundedCornerShape(14.dp)
                            )
                            .clickable(enabled = !answerChecked && !isMatched) {
                                selectedLeft = left
                            }
                            .padding(horizontal = 16.dp, vertical = 14.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = left,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = EtymoDark,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }

            // Right column
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                pairs.map { it.second }.shuffled().forEach { right ->
                    val isMatched = selectedPairs.containsValue(right)

                    Box(
                        modifier = Modifier
                            .width(140.dp)
                            .clip(RoundedCornerShape(14.dp))
                            .background(
                                when {
                                    isMatched -> EtymoCorrect.copy(alpha = 0.15f)
                                    else -> EtymoWhite
                                }
                            )
                            .border(
                                2.dp,
                                if (isMatched) EtymoCorrect else EtymoProgressBg,
                                RoundedCornerShape(14.dp)
                            )
                            .clickable(enabled = !answerChecked && !isMatched && selectedLeft != null) {
                                selectedLeft?.let { left ->
                                    onSelectPair(left, right)
                                    selectedLeft = null
                                }
                            }
                            .padding(horizontal = 16.dp, vertical = 14.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = right,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = EtymoDark,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }
    }
}

// ─── Feedback Banner ────────────────────────────────────────────────────────

@Composable
private fun FeedbackBanner(isCorrect: Boolean, correctAnswer: String?) {
    val scale by animateFloatAsState(
        targetValue = 1f,
        animationSpec = spring(dampingRatio = 0.5f, stiffness = 300f),
        label = "feedbackScale"
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .scale(scale)
            .clip(RoundedCornerShape(16.dp))
            .background(
                if (isCorrect) EtymoCorrect.copy(alpha = 0.12f)
                else EtymoWrong.copy(alpha = 0.12f)
            )
            .padding(16.dp)
    ) {
        Column {
            Text(
                text = if (isCorrect) "✅ Excellent!" else "❌ Not quite right",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = if (isCorrect) EtymoCorrectDark else EtymoWrongDark
            )
            if (!isCorrect && correctAnswer != null) {
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Correct: $correctAnswer",
                    fontSize = 15.sp,
                    color = EtymoCorrectDark,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}

// ─── FlowRow fallback (Compose Foundation provides this) ────────────────────
// Using compose foundation's FlowRow - already available via libs.androidx.compose.foundation
