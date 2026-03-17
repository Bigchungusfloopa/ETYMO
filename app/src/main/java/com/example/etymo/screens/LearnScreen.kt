package com.example.etymo.screens

import androidx.compose.animation.*
import androidx.compose.runtime.*
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.etymo.screens.learn.*
import com.example.etymo.viewmodels.LearnNavState
import com.example.etymo.viewmodels.LearnViewModel

@Composable
fun LearnScreen() {
    val viewModel: LearnViewModel = viewModel()
    val uiState by viewModel.uiState.collectAsState()

    AnimatedContent(
        targetState = uiState.navState,
        transitionSpec = {
            slideInHorizontally { it / 2 } + fadeIn() togetherWith
                    slideOutHorizontally { -it / 2 } + fadeOut()
        },
        label = "learnNav"
    ) { navState ->
        when (navState) {
            LearnNavState.LANGUAGE_SELECTION -> {
                LanguageSelectionScreen(
                    onLanguageSelected = { viewModel.selectLanguage(it) }
                )
            }

            LearnNavState.SECTION_LIST -> {
                SectionListScreen(
                    language = uiState.selectedLanguage,
                    sections = uiState.sections,
                    onSectionSelected = { viewModel.selectSection(it) },
                    onBack = { viewModel.navigateBack() }
                )
            }

            LearnNavState.UNIT_PATH -> {
                val section = uiState.sections.getOrNull(uiState.selectedSectionIndex)
                if (section != null) {
                    UnitPathScreen(
                        sectionTitle = section.title,
                        units = section.units,
                        isUnitUnlocked = { viewModel.isUnitUnlocked(uiState.selectedSectionIndex, it) },
                        isUnitCompleted = { viewModel.isUnitCompleted(uiState.selectedSectionIndex, it) },
                        onUnitSelected = { viewModel.startQuiz(it) },
                        onBack = { viewModel.navigateBack() }
                    )
                }
            }

            LearnNavState.QUIZ -> {
                val question = viewModel.getCurrentQuestion()
                val totalQuestions = viewModel.getCurrentQuestions().size
                if (question != null) {
                    QuizScreen(
                        question = question,
                        questionIndex = uiState.currentQuestionIndex,
                        totalQuestions = totalQuestions,
                        hearts = uiState.hearts,
                        streak = uiState.streak,
                        selectedAnswer = uiState.selectedAnswer,
                        jumbleSelectedWords = uiState.jumbleSelectedWords,
                        matchSelectedPairs = uiState.matchSelectedPairs,
                        answerChecked = uiState.answerChecked,
                        isCorrect = uiState.isCorrect,
                        onSelectAnswer = { viewModel.selectAnswer(it) },
                        onToggleJumbleWord = { viewModel.toggleJumbleWord(it) },
                        onSelectMatchPair = { l, r -> viewModel.selectMatchPair(l, r) },
                        onCheckAnswer = { viewModel.checkAnswer() },
                        onNextQuestion = { viewModel.nextQuestion() },
                        onClose = { viewModel.navigateBack() }
                    )
                }
            }

            LearnNavState.RESULT -> {
                val totalQuestions = viewModel.getCurrentQuestions().size
                ResultScreen(
                    correctCount = uiState.correctCount,
                    totalQuestions = totalQuestions,
                    xpEarned = uiState.xpEarned,
                    onContinue = { viewModel.navigateBack() },
                    onTryAgain = { viewModel.startQuiz(uiState.selectedUnitIndex) }
                )
            }
        }
    }
}
