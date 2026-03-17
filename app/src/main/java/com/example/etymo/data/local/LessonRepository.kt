package com.example.etymo.data.local

object LessonRepository {

    val supportedLanguages = listOf(
        LanguageInfo("Tamil", "தமிழ்", "🇮🇳"),
        LanguageInfo("Hindi", "हिन्दी", "🇮🇳"),
        LanguageInfo("Kannada", "ಕನ್ನಡ", "🇮🇳"),
        LanguageInfo("Marathi", "मराठी", "🇮🇳"),
        LanguageInfo("Bengali", "বাংলা", "🇮🇳")
    )

    fun getSections(language: String): List<Section> = when (language) {
        "Tamil" -> TamilLessons.sections
        "Hindi" -> HindiLessons.sections
        "Kannada" -> KannadaLessons.sections
        "Marathi" -> MarathiLessons.sections
        "Bengali" -> BengaliLessons.sections
        else -> emptyList()
    }
}

data class LanguageInfo(
    val name: String,
    val nativeName: String,
    val flag: String
)
