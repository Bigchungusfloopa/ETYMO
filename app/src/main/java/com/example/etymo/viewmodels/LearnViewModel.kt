package com.example.etymo.viewmodels

import androidx.lifecycle.ViewModel
import com.example.etymo.data.local.LessonRepository
import com.example.etymo.data.local.LessonUnit
import com.example.etymo.data.local.Question
import com.example.etymo.data.local.QuestionType
import com.example.etymo.data.local.Section
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

enum class LearnNavState {
    LANGUAGE_SELECTION,
    SECTION_LIST,
    UNIT_PATH,
    QUIZ,
    RESULT
}

data class LearnUiState(
    val navState: LearnNavState = LearnNavState.LANGUAGE_SELECTION,
    val selectedLanguage: String = "",
    val sections: List<Section> = emptyList(),
    val selectedSectionIndex: Int = 0,
    val selectedUnitIndex: Int = 0,

    // Quiz state
    val currentQuestionIndex: Int = 0,
    val selectedAnswer: String? = null,
    val jumbleSelectedWords: List<String> = emptyList(),
    val matchSelectedPairs: Map<String, String> = emptyMap(),
    val answerChecked: Boolean = false,
    val isCorrect: Boolean = false,
    val correctCount: Int = 0,
    val wrongCount: Int = 0,
    val xpEarned: Int = 0,
    val hearts: Int = 5,
    val streak: Int = 0,
    val quizFinished: Boolean = false,

    // Unit completion tracking (sectionIdx_unitIdx -> completed)
    val completedUnits: Set<String> = emptySet()
)

class LearnViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(LearnUiState())
    val uiState: StateFlow<LearnUiState> = _uiState.asStateFlow()

    fun selectLanguage(language: String) {
        val sections = LessonRepository.getSections(language)
        _uiState.update {
            it.copy(
                selectedLanguage = language,
                sections = sections,
                navState = LearnNavState.SECTION_LIST
            )
        }
    }

    fun selectSection(sectionIndex: Int) {
        _uiState.update {
            it.copy(
                selectedSectionIndex = sectionIndex,
                navState = LearnNavState.UNIT_PATH
            )
        }
    }

    fun startQuiz(unitIndex: Int) {
        _uiState.update {
            it.copy(
                selectedUnitIndex = unitIndex,
                currentQuestionIndex = 0,
                selectedAnswer = null,
                jumbleSelectedWords = emptyList(),
                matchSelectedPairs = emptyMap(),
                answerChecked = false,
                isCorrect = false,
                correctCount = 0,
                wrongCount = 0,
                xpEarned = 0,
                hearts = 5,
                streak = 0,
                quizFinished = false,
                navState = LearnNavState.QUIZ
            )
        }
    }

    fun getCurrentQuestions(): List<Question> {
        val state = _uiState.value
        return state.sections
            .getOrNull(state.selectedSectionIndex)
            ?.units
            ?.getOrNull(state.selectedUnitIndex)
            ?.questions
            ?: emptyList()
    }

    fun getCurrentQuestion(): Question? {
        val questions = getCurrentQuestions()
        return questions.getOrNull(_uiState.value.currentQuestionIndex)
    }

    fun selectAnswer(answer: String) {
        if (_uiState.value.answerChecked) return
        _uiState.update { it.copy(selectedAnswer = answer) }
    }

    fun toggleJumbleWord(word: String) {
        if (_uiState.value.answerChecked) return
        _uiState.update { state ->
            val current = state.jumbleSelectedWords.toMutableList()
            if (current.contains(word)) {
                current.remove(word)
            } else {
                current.add(word)
            }
            state.copy(
                jumbleSelectedWords = current,
                selectedAnswer = current.joinToString(" ")
            )
        }
    }

    fun selectMatchPair(left: String, right: String) {
        if (_uiState.value.answerChecked) return
        _uiState.update { state ->
            val pairs = state.matchSelectedPairs.toMutableMap()
            pairs[left] = right
            state.copy(matchSelectedPairs = pairs)
        }
    }

    fun checkAnswer() {
        val question = getCurrentQuestion() ?: return
        val state = _uiState.value

        val isCorrect = when (question.type) {
            QuestionType.MATCH -> {
                val expected = question.matchPairs ?: emptyList()
                expected.all { (l, r) -> state.matchSelectedPairs[l] == r }
            }
            else -> {
                state.selectedAnswer?.trim() == question.correctAnswer?.trim()
            }
        }

        _uiState.update {
            it.copy(
                answerChecked = true,
                isCorrect = isCorrect,
                correctCount = if (isCorrect) it.correctCount + 1 else it.correctCount,
                wrongCount = if (!isCorrect) it.wrongCount + 1 else it.wrongCount,
                xpEarned = if (isCorrect) it.xpEarned + 10 else it.xpEarned,
                hearts = if (!isCorrect) (it.hearts - 1).coerceAtLeast(0) else it.hearts,
                streak = if (isCorrect) it.streak + 1 else 0
            )
        }
    }

    fun nextQuestion() {
        val questions = getCurrentQuestions()
        val nextIndex = _uiState.value.currentQuestionIndex + 1

        if (nextIndex >= questions.size || _uiState.value.hearts <= 0) {
            // Mark unit as completed
            val state = _uiState.value
            val unitKey = "${state.selectedSectionIndex}_${state.selectedUnitIndex}"
            _uiState.update {
                it.copy(
                    quizFinished = true,
                    navState = LearnNavState.RESULT,
                    completedUnits = it.completedUnits + unitKey
                )
            }
        } else {
            _uiState.update {
                it.copy(
                    currentQuestionIndex = nextIndex,
                    selectedAnswer = null,
                    jumbleSelectedWords = emptyList(),
                    matchSelectedPairs = emptyMap(),
                    answerChecked = false,
                    isCorrect = false
                )
            }
        }
    }

    fun isUnitUnlocked(sectionIndex: Int, unitIndex: Int): Boolean {
        if (unitIndex == 0) return true
        val prevKey = "${sectionIndex}_${unitIndex - 1}"
        return _uiState.value.completedUnits.contains(prevKey)
    }

    fun isUnitCompleted(sectionIndex: Int, unitIndex: Int): Boolean {
        val key = "${sectionIndex}_${unitIndex}"
        return _uiState.value.completedUnits.contains(key)
    }

    fun navigateBack() {
        _uiState.update { state ->
            when (state.navState) {
                LearnNavState.SECTION_LIST -> state.copy(navState = LearnNavState.LANGUAGE_SELECTION)
                LearnNavState.UNIT_PATH -> state.copy(navState = LearnNavState.SECTION_LIST)
                LearnNavState.QUIZ -> state.copy(navState = LearnNavState.UNIT_PATH)
                LearnNavState.RESULT -> state.copy(navState = LearnNavState.UNIT_PATH)
                else -> state
            }
        }
    }

    fun goToLanguageSelection() {
        _uiState.update { it.copy(navState = LearnNavState.LANGUAGE_SELECTION) }
    }
}
