package com.example.etymo.data.local

object MarathiLessons {

    val sections = listOf(

        // ================= SECTION 1: Survival (A1) =================
        Section(
            title = "Survival (A1)",
            units = listOf(

                LessonUnit(
                    title = "The Basics",
                    questions = listOf(

                        Question(
                            type = QuestionType.MCQ,
                            question = "How do you say 'Hello'?",
                            options = listOf("नमस्कार", "निरोप", "धन्यवाद"),
                            correctAnswer = "नमस्कार"
                        ),

                        Question(
                            type = QuestionType.WORD_JUMBLE,
                            question = "Arrange the words to say 'My name is Rahul.'",
                            options = listOf("नाव", "माझे", "राहुल", "आहे"),
                            correctAnswer = "माझे नाव राहुल आहे"
                        ),

                        Question(
                            type = QuestionType.PRONUNCIATION,
                            question = "The word 'मी' (I) sounds like:",
                            options = listOf("My", "Mee (like 'me' in English)", "May"),
                            correctAnswer = "Mee (like 'me' in English)"
                        ),

                        Question(
                            type = QuestionType.MCQ,
                            question = "Translate 'Who are you?'",
                            options = listOf("तू कोण आहेस?", "तू कुठे आहेस?", "तू कसा आहेस?"),
                            correctAnswer = "तू कोण आहेस?"
                        ),

                        Question(
                            type = QuestionType.MATCH,
                            question = "Match the pairs",
                            matchPairs = listOf(
                                "हो" to "Yes",
                                "नाही" to "No"
                            )
                        )
                    )
                ),

                LessonUnit(
                    title = "Daily Needs",
                    questions = listOf(

                        Question(
                            type = QuestionType.MCQ,
                            question = "Select the word for 'Water'",
                            options = listOf("जेवण", "पाणी", "दूध"),
                            correctAnswer = "पाणी"
                        ),

                        Question(
                            type = QuestionType.WORD_JUMBLE,
                            question = "Arrange the words to ask 'Where is the road?'",
                            options = listOf("कुठे", "रस्ता", "आहे", "?"),
                            correctAnswer = "रस्ता कुठे आहे?"
                        ),

                        Question(
                            type = QuestionType.MCQ,
                            question = "Fill in the blank: 'मला ______ लागली आहे।' (I am hungry.)",
                            options = listOf("तहान", "भूक", "झोप"),
                            correctAnswer = "भूक"
                        ),

                        Question(
                            type = QuestionType.PRONUNCIATION,
                            question = "'जेवण' (Food) starts with:",
                            options = listOf(
                                "A soft 'J' (as in Jam)",
                                "A 'Z' sound",
                                "A 'D' sound"
                            ),
                            correctAnswer = "A soft 'J' (as in Jam)"
                        ),

                        Question(
                            type = QuestionType.MCQ,
                            question = "Which word means 'Sleep'?",
                            options = listOf("झोप", "जागृत", "धावणे"),
                            correctAnswer = "झोप"
                        )
                    )
                ),

                LessonUnit(
                    title = "Numbers & Time",
                    questions = listOf(

                        Question(
                            type = QuestionType.MCQ,
                            question = "What is the number '5'?",
                            options = listOf("तीन", "पाच", "सात"),
                            correctAnswer = "पाच"
                        ),

                        Question(
                            type = QuestionType.WORD_JUMBLE,
                            question = "Form the sentence 'It is 10 o'clock.'",
                            options = listOf("दहा", "आहेत", "वाजले"),
                            correctAnswer = "दहा वाजले आहेत"
                        ),

                        Question(
                            type = QuestionType.MCQ,
                            question = "How do you say 'Tomorrow'?",
                            options = listOf("आज", "उद्या", "काल"),
                            correctAnswer = "उद्या"
                        ),

                        Question(
                            type = QuestionType.PRONUNCIATION,
                            question = "The number '8' (आठ) sounds like:",
                            options = listOf("At", "Aath (with a hard 'th')", "Aut"),
                            correctAnswer = "Aath (with a hard 'th')"
                        ),

                        Question(
                            type = QuestionType.MCQ,
                            question = "Translate 'What time is it?'",
                            options = listOf("किती वाजले?", "तू कधी येशील?"),
                            correctAnswer = "किती वाजले?"
                        )
                    )
                ),

                LessonUnit(
                    title = "People",
                    questions = listOf(

                        Question(
                            type = QuestionType.MCQ,
                            question = "Identify the word for 'Father'",
                            options = listOf("आई", "बाबा", "भाऊ"),
                            correctAnswer = "बाबा"
                        ),

                        Question(
                            type = QuestionType.WORD_JUMBLE,
                            question = "Arrange: 'He is my brother.'",
                            options = listOf("भाऊ", "माझा", "तो", "आहे"),
                            correctAnswer = "तो माझा भाऊ आहे"
                        ),

                        Question(
                            type = QuestionType.MCQ,
                            question = "Translate 'Farmer'",
                            options = listOf("पोलीस", "शेतकरी", "शिक्षक"),
                            correctAnswer = "शेतकरी"
                        ),

                        Question(
                            type = QuestionType.PRONUNCIATION,
                            question = "'बहीण' (Sister) ends with:",
                            options = listOf(
                                "N (as in Net)",
                                "Na (retroflex 'N')",
                                "M"
                            ),
                            correctAnswer = "Na (retroflex 'N')"
                        ),

                        Question(
                            type = QuestionType.MCQ,
                            question = "Which word means 'Friend'?",
                            options = listOf("मित्र", "शत्रू", "शेजारी"),
                            correctAnswer = "मित्र"
                        )
                    )
                )
            )
        )
    )
}