package com.example.etymo.data.local

object KannadaLessons {

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
                            options = listOf("ನಮಸ್ಕಾರ", "ಹೋಗಿ ಬರುತ್ತೇನೆ", "ಧನ್ಯವಾದ"),
                            correctAnswer = "ನಮಸ್ಕಾರ"
                        ),

                        Question(
                            type = QuestionType.WORD_JUMBLE,
                            question = "Arrange: ಹೆಸರು / ನನ್ನ / ಆಕಾಶ್ / .",
                            options = listOf("ಹೆಸರು", "ನನ್ನ", "ಆಕಾಶ್", "."),
                            correctAnswer = "ನನ್ನ ಹೆಸರು ಆಕಾಶ್."
                        ),

                        Question(
                            type = QuestionType.PRONUNCIATION,
                            question = "The word 'ನಾನು' sounds like:",
                            options = listOf("Na-nu", "Naa-nu", "Now-nu"),
                            correctAnswer = "Naa-nu"
                        ),

                        Question(
                            type = QuestionType.MCQ,
                            question = "Translate 'How are you?'",
                            options = listOf(
                                "ನೀವು ಹೇಗಿದ್ದೀರಿ?",
                                "ನೀವು ಎಲ್ಲಿಗೆ ಹೋಗುತ್ತಿದ್ದೀರಿ?",
                                "ನಿಮ್ಮ ಹೆಸರೇನು?"
                            ),
                            correctAnswer = "ನೀವು ಹೇಗಿದ್ದೀರಿ?"
                        )
                    )
                ),

                LessonUnit(
                    title = "Daily Needs",
                    questions = listOf(

                        Question(
                            type = QuestionType.MCQ,
                            question = "Select the word for 'Water'",
                            options = listOf("ಊಟ", "ನೀರು", "ಹಾಲು"),
                            correctAnswer = "ನೀರು"
                        ),

                        Question(
                            type = QuestionType.WORD_JUMBLE,
                            question = "Arrange: ರಸ್ತೆ / ಎಲ್ಲಿದೆ / ?",
                            options = listOf("ರಸ್ತೆ", "ಎಲ್ಲಿದೆ", "?"),
                            correctAnswer = "ರಸ್ತೆ ಎಲ್ಲಿದೆ?"
                        ),

                        Question(
                            type = QuestionType.MCQ,
                            question = "Fill: ನನಗೆ ______ ಹಸಿವೆಯಾಗುತ್ತಿದೆ.",
                            options = listOf("ಬಾಯಾರಿಕೆ", "ಊಟದ", "ತುಂಬಾ"),
                            correctAnswer = "ಊಟದ"
                        ),

                        Question(
                            type = QuestionType.PRONUNCIATION,
                            question = "'ಊಟ' starts with:",
                            options = listOf("Short u", "Long oo", "O sound"),
                            correctAnswer = "Long oo"
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
                            options = listOf("ಇದು ಏನು?", "ಇದು ಎಷ್ಟು?"),
                            correctAnswer = "ಇದು ಎಷ್ಟು?"
                        ),

                        Question(
                            type = QuestionType.WORD_JUMBLE,
                            question = "Arrange: ಜಾಸ್ತಿ / ಬೆಲೆ / ಇದು / .",
                            options = listOf("ಜಾಸ್ತಿ", "ಬೆಲೆ", "ಇದು", "."),
                            correctAnswer = "ಇದು ಬೆಲೆ ಜಾಸ್ತಿ."
                        ),

                        Question(
                            type = QuestionType.MCQ,
                            question = "Select the color 'White'",
                            options = listOf("ಕಪ್ಪು", "ಬಿಳಿ", "ಕೆಂಪು"),
                            correctAnswer = "ಬಿಳಿ"
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
                            question = "Future Tense: 'I will go.'",
                            options = listOf("ನಾನು ಹೋದೆ", "ನಾನು ಹೋಗುತ್ತೇನೆ"),
                            correctAnswer = "ನಾನು ಹೋಗುತ್ತೇನೆ"
                        ),

                        Question(
                            type = QuestionType.WORD_JUMBLE,
                            question = "Arrange: ಮಾಡುತ್ತಿದ್ದೇನೆ / ಅಗತ್ಯ / ಕಾರಣ / ನಾನು / .",
                            options = listOf("ಮಾಡುತ್ತಿದ್ದೇನೆ", "ಅಗತ್ಯ", "ಕಾರಣ", "ನಾನು", "."),
                            correctAnswer = "ಅಗತ್ಯ ಕಾರಣ ನಾನು ಮಾಡುತ್ತಿದ್ದೇನೆ."
                        ),

                        Question(
                            type = QuestionType.MCQ,
                            question = "Choose the word for 'But'",
                            options = listOf("ಮತ್ತು", "ಆದರೆ", "ಆದ್ದರಿಂದ"),
                            correctAnswer = "ಆದರೆ"
                        )
                    )
                )
            )
        )
    )
}