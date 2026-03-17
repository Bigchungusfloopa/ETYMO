package com.example.etymo.data.local

enum class QuestionType {
    MCQ,
    WORD_JUMBLE,
    PRONUNCIATION,
    MATCH
}

data class Question(
    val type: QuestionType,
    val question: String,
    val options: List<String>? = null,
    val correctAnswer: String? = null,
    val matchPairs: List<Pair<String, String>>? = null
)

data class LessonUnit(
    val title: String,
    val questions: List<Question>
)

data class Section(
    val title: String,
    val units: List<LessonUnit>
)
