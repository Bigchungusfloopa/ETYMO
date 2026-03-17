package com.example.etymo.data.local

object TamilLessons {

    val sections = listOf(

        Section(
            title = "Survival (A1)",
            units = listOf(

                LessonUnit(
                    title = "The Basics",
                    questions = listOf(

                        Question(
                            type = QuestionType.MCQ,
                            question = "How do you say 'Hello'?",
                            options = listOf("வணக்கம்", "போயிட்டு வருகிறேன்", "நன்றி"),
                            correctAnswer = "வணக்கம்"
                        ),

                        Question(
                            type = QuestionType.WORD_JUMBLE,
                            question = "Arrange: பெயர் / என் / ரவி / .",
                            options = listOf("பெயர்", "என்", "ரவி", "."),
                            correctAnswer = "என் பெயர் ரவி."
                        ),

                        Question(
                            type = QuestionType.PRONUNCIATION,
                            question = "The word 'நான்' sounds like:",
                            options = listOf("Nan", "Naan", "None"),
                            correctAnswer = "Naan"
                        ),

                        Question(
                            type = QuestionType.MCQ,
                            question = "Translate 'How are you?'",
                            options = listOf(
                                "எப்படி இருக்கீங்க?",
                                "எங்கே போறீங்க?",
                                "என்ன பண்றீங்க?"
                            ),
                            correctAnswer = "எப்படி இருக்கீங்க?"
                        )
                    )
                ),

                LessonUnit(
                    title = "Daily Needs",
                    questions = listOf(

                        Question(
                            type = QuestionType.MCQ,
                            question = "Select the word for 'Water'",
                            options = listOf("சாப்பாடு", "தண்ணி", "பால்"),
                            correctAnswer = "தண்ணி"
                        ),

                        Question(
                            type = QuestionType.WORD_JUMBLE,
                            question = "Arrange: வீடு / எங்கே / இருக்கிறது / ?",
                            options = listOf("வீடு", "எங்கே", "இருக்கிறது", "?"),
                            correctAnswer = "வீடு எங்கே இருக்கிறது?"
                        ),

                        Question(
                            type = QuestionType.MCQ,
                            question = "Fill: எனக்கு ______ எடுக்கிறது.",
                            options = listOf("தாகம்", "பசி", "தூக்கம்"),
                            correctAnswer = "பசி"
                        ),

                        Question(
                            type = QuestionType.PRONUNCIATION,
                            question = "'சாப்பாடு' starts with:",
                            options = listOf("Soft S", "Ch sound", "Z sound"),
                            correctAnswer = "Ch sound"
                        )
                    )
                )
            )
        ),

        Section(
            title = "Interaction (A2)",
            units = listOf(

                LessonUnit(
                    title = "Shopping",
                    questions = listOf(

                        Question(
                            type = QuestionType.MCQ,
                            question = "How do you ask 'How much is this?'",
                            options = listOf("இது என்ன?", "இது எவ்வளவு?"),
                            correctAnswer = "இது எவ்வளவு?"
                        ),

                        Question(
                            type = QuestionType.WORD_JUMBLE,
                            question = "Arrange: அதிகம் / விலை / இது / .",
                            options = listOf("அதிகம்", "விலை", "இது", "."),
                            correctAnswer = "இது விலை அதிகம்."
                        ),

                        Question(
                            type = QuestionType.MCQ,
                            question = "Select the color 'Green'",
                            options = listOf("நீலம்", "பச்சை", "சிவப்பு"),
                            correctAnswer = "பச்சை"
                        )
                    )
                )
            )
        ),

        Section(
            title = "Mastery (B1+)",
            units = listOf(

                LessonUnit(
                    title = "Grammar Polish",
                    questions = listOf(

                        Question(
                            type = QuestionType.MCQ,
                            question = "Future Tense: 'I will come.'",
                            options = listOf("நான் வந்தேன்", "நான் வருவேன்"),
                            correctAnswer = "நான் வருவேன்"
                        ),

                        Question(
                            type = QuestionType.WORD_JUMBLE,
                            question = "Arrange: கூப்பிட்டதால் / போனேன் / நான் / .",
                            options = listOf("கூப்பிட்டதால்", "போனேன்", "நான்", "."),
                            correctAnswer = "கூப்பிட்டதால் நான் போனேன்."
                        ),

                        Question(
                            type = QuestionType.MCQ,
                            question = "Choose the word for 'But'",
                            options = listOf("மற்றும்", "ஆனால்", "அதனால்"),
                            correctAnswer = "ஆனால்"
                        )
                    )
                )
            )
        )
    )
}