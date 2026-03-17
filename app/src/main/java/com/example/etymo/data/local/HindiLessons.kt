package com.example.etymo.data.local

object HindiLessons {

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
                            options = listOf("नमस्ते", "अलविदा", "धन्यवाद"),
                            correctAnswer = "नमस्ते"
                        ),

                        Question(
                            type = QuestionType.WORD_JUMBLE,
                            question = "Arrange the words to say 'My name is Amit.'",
                            options = listOf("नाम", "मेरा", "अमित", "है"),
                            correctAnswer = "मेरा नाम अमित है"
                        ),

                        Question(
                            type = QuestionType.PRONUNCIATION,
                            question = "The word 'मैं' (I) sounds like:",
                            options = listOf("Men", "May (with a nasal sound)", "My"),
                            correctAnswer = "May (with a nasal sound)"
                        ),

                        Question(
                            type = QuestionType.MCQ,
                            question = "Translate 'Who are you?'",
                            options = listOf("आप कौन हैं?", "आप कहाँ हैं?", "आप कैसे हैं?"),
                            correctAnswer = "आप कौन हैं?"
                        ),

                        Question(
                            type = QuestionType.MATCH,
                            question = "Match the pairs",
                            matchPairs = listOf(
                                "हाँ" to "Yes",
                                "नहीं" to "No"
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
                            options = listOf("खाना", "पानी", "दूध"),
                            correctAnswer = "पानी"
                        ),

                        Question(
                            type = QuestionType.WORD_JUMBLE,
                            question = "Arrange the words to ask 'Where is the toilet?'",
                            options = listOf("कहाँ", "शौचालय", "है", "?"),
                            correctAnswer = "शौचालय कहाँ है?"
                        ),

                        Question(
                            type = QuestionType.MCQ,
                            question = "Fill in the blank: 'मुझे ______ लगी है।' (I am hungry.)",
                            options = listOf("प्यास", "भूख", "नींद"),
                            correctAnswer = "भूख"
                        ),

                        Question(
                            type = QuestionType.PRONUNCIATION,
                            question = "'खाना' (Food) starts with:",
                            options = listOf(
                                "A soft 'K'",
                                "A heavy, aspirated 'Kh'",
                                "A 'G' sound"
                            ),
                            correctAnswer = "A heavy, aspirated 'Kh'"
                        ),

                        Question(
                            type = QuestionType.MCQ,
                            question = "Which word means 'Sleep'?",
                            options = listOf("सोना", "जागना", "चलना"),
                            correctAnswer = "सोना"
                        )
                    )
                ),

                LessonUnit(
                    title = "Numbers & Time",
                    questions = listOf(

                        Question(
                            type = QuestionType.MCQ,
                            question = "What is the number '5'?",
                            options = listOf("तीन", "पाँच", "सात"),
                            correctAnswer = "पाँच"
                        ),

                        Question(
                            type = QuestionType.WORD_JUMBLE,
                            question = "Form the sentence 'It is 2 o'clock.'",
                            options = listOf("दो", "हैं", "बजे"),
                            correctAnswer = "दो बजे हैं"
                        ),

                        Question(
                            type = QuestionType.MCQ,
                            question = "How do you say 'Today'?",
                            options = listOf("कल", "आज", "अब"),
                            correctAnswer = "आज"
                        ),

                        Question(
                            type = QuestionType.PRONUNCIATION,
                            question = "The number '8' (आठ) ends with:",
                            options = listOf(
                                "A soft 't'",
                                "A hard 'Th' sound",
                                "A 'D' sound"
                            ),
                            correctAnswer = "A hard 'Th' sound"
                        ),

                        Question(
                            type = QuestionType.MCQ,
                            question = "Translate 'What time is it?'",
                            options = listOf("कितने बजे हैं?", "आप कब आएंगे?"),
                            correctAnswer = "कितने बजे हैं?"
                        )
                    )
                ),

                LessonUnit(
                    title = "People",
                    questions = listOf(

                        Question(
                            type = QuestionType.MCQ,
                            question = "Identify the word for 'Mother'",
                            options = listOf("पिता", "माँ", "भाई"),
                            correctAnswer = "माँ"
                        ),

                        Question(
                            type = QuestionType.WORD_JUMBLE,
                            question = "Arrange: 'He is my friend.'",
                            options = listOf("दोस्त", "मेरा", "वह", "है"),
                            correctAnswer = "वह मेरा दोस्त है"
                        ),

                        Question(
                            type = QuestionType.MCQ,
                            question = "Translate 'Teacher'",
                            options = listOf("डॉक्टर", "शिक्षक", "किसान"),
                            correctAnswer = "शिक्षक"
                        ),

                        Question(
                            type = QuestionType.PRONUNCIATION,
                            question = "'भाई' (Brother) starts with:",
                            options = listOf("B", "Bh (aspirated)", "P"),
                            correctAnswer = "Bh (aspirated)"
                        ),

                        Question(
                            type = QuestionType.MCQ,
                            question = "Which word means 'Daughter'?",
                            options = listOf("बेटा", "बेटी", "बहन"),
                            correctAnswer = "बेटी"
                        )
                    )
                )
            )
        ),

        // ================= SECTION 2: Interaction (A2) =================
        Section(
            title = "Interaction (A2)",
            units = listOf(

                LessonUnit(
                    title = "Shopping",
                    questions = listOf(

                        Question(
                            type = QuestionType.MCQ,
                            question = "How do you ask 'How much is this?'",
                            options = listOf("यह क्या है?", "यह कितने का है?"),
                            correctAnswer = "यह कितने का है?"
                        ),

                        Question(
                            type = QuestionType.WORD_JUMBLE,
                            question = "Arrange: 'This is very expensive.'",
                            options = listOf("बहुत", "यह", "है", "महंगा"),
                            correctAnswer = "यह बहुत महंगा है"
                        ),

                        Question(
                            type = QuestionType.MCQ,
                            question = "Select the color 'Red'",
                            options = listOf("नीला", "हरा", "लाल"),
                            correctAnswer = "लाल"
                        ),

                        Question(
                            type = QuestionType.PRONUNCIATION,
                            question = "'सस्ता' (Cheap) sounds like:",
                            options = listOf("Sas-ta", "Shas-ta", "Sas-tra"),
                            correctAnswer = "Sas-ta"
                        ),

                        Question(
                            type = QuestionType.MCQ,
                            question = "Opposite of 'भारी' (Heavy)",
                            options = listOf("बड़ा", "हल्का", "पतला"),
                            correctAnswer = "हल्का"
                        )
                    )
                ),

                LessonUnit(
                    title = "Feelings & Health",
                    questions = listOf(

                        Question(
                            type = QuestionType.MCQ,
                            question = "How do you say 'I am happy'?",
                            options = listOf("मैं दुखी हूँ", "मैं खुश हूँ"),
                            correctAnswer = "मैं खुश हूँ"
                        ),

                        Question(
                            type = QuestionType.WORD_JUMBLE,
                            question = "Arrange: 'My head hurts.'",
                            options = listOf("रहा", "है", "सिर", "दुख", "मेरा"),
                            correctAnswer = "मेरा सिर दुख रहा है"
                        ),

                        Question(
                            type = QuestionType.MCQ,
                            question = "Identify 'Medicine'",
                            options = listOf("मिठाई", "दवाई", "पढ़ाई"),
                            correctAnswer = "दवाई"
                        ),

                        Question(
                            type = QuestionType.PRONUNCIATION,
                            question = "The 'kh' in 'खुश' is:",
                            options = listOf(
                                "Like 'K' in King",
                                "Raspy 'KH' from throat",
                                "Like 'CH' in Church"
                            ),
                            correctAnswer = "Raspy 'KH' from throat"
                        ),

                        Question(
                            type = QuestionType.MCQ,
                            question = "Translate 'I am tired'",
                            options = listOf("मैं सो रहा हूँ", "मैं थक गया हूँ"),
                            correctAnswer = "मैं थक गया हूँ"
                        )
                    )
                ),

                LessonUnit(
                    title = "Travel",
                    questions = listOf(

                        Question(
                            type = QuestionType.MCQ,
                            question = "Where is the Bus Stand?",
                            options = listOf("बस अड्डा कहाँ है?", "स्टेशन कहाँ है?"),
                            correctAnswer = "बस अड्डा कहाँ है?"
                        ),

                        Question(
                            type = QuestionType.WORD_JUMBLE,
                            question = "Arrange: 'The train is late.'",
                            options = listOf("है", "देरी", "से", "गाड़ी"),
                            correctAnswer = "गाड़ी देरी से है"
                        ),

                        Question(
                            type = QuestionType.MCQ,
                            question = "Which way is 'Left'?",
                            options = listOf("दायाँ", "बायाँ", "सीधा"),
                            correctAnswer = "बायाँ"
                        ),

                        Question(
                            type = QuestionType.PRONUNCIATION,
                            question = "'टिकट' sounds like:",
                            options = listOf("Ti-kat", "Tee-kut", "Ti-ket"),
                            correctAnswer = "Ti-ket"
                        ),

                        Question(
                            type = QuestionType.MCQ,
                            question = "Translate 'Stop here'",
                            options = listOf("यहाँ रुकिए", "वहाँ जाइए"),
                            correctAnswer = "यहाँ रुकिए"
                        )
                    )
                )
            )
        ),

        // ================= SECTION 3: Mastery (B1+) =================
        Section(
            title = "Mastery (B1+)",
            units = listOf(

                LessonUnit(
                    title = "Grammar Polish",
                    questions = listOf(

                        Question(
                            type = QuestionType.MCQ,
                            question = "Future tense: 'I will go'",
                            options = listOf("मैं गया", "मैं जाऊँगा"),
                            correctAnswer = "मैं जाऊँगा"
                        ),

                        Question(
                            type = QuestionType.WORD_JUMBLE,
                            question = "Arrange: 'I am going because it is late.'",
                            options = listOf("रहा", "हूँ", "क्योंकि", "देर", "हो", "गई", "है", "मैं", "जा"),
                            correctAnswer = "मैं जा रहा हूँ क्योंकि देर हो गई है"
                        ),

                        Question(
                            type = QuestionType.MCQ,
                            question = "Choose the word for 'But'",
                            options = listOf("और", "लेकिन", "इसलिए"),
                            correctAnswer = "लेकिन"
                        ),

                        Question(
                            type = QuestionType.PRONUNCIATION,
                            question = "'हूँ' has:",
                            options = listOf(
                                "Silent H",
                                "Strong nasal 'OON'",
                                "Soft U"
                            ),
                            correctAnswer = "Strong nasal 'OON'"
                        ),

                        Question(
                            type = QuestionType.MCQ,
                            question = "'I was reading' is:",
                            options = listOf("मैं पढ़ रहा था", "मैंने पढ़ा"),
                            correctAnswer = "मैं पढ़ रहा था"
                        )
                    )
                ),

                LessonUnit(
                    title = "Culture & Slang",
                    questions = listOf(

                        Question(
                            type = QuestionType.MCQ,
                            question = "'आँखों का तारा' means:",
                            options = listOf("A star", "Very beloved", "Blind man"),
                            correctAnswer = "Very beloved"
                        ),

                        Question(
                            type = QuestionType.WORD_JUMBLE,
                            question = "Arrange proverb: 'Might is right'",
                            options = listOf("भैंस", "जिसकी", "उसकी", "लाठी"),
                            correctAnswer = "जिसकी लाठी उसकी भैंस"
                        ),

                        Question(
                            type = QuestionType.MCQ,
                            question = "Respectful suffix",
                            options = listOf("-को", "-जी", "-ने"),
                            correctAnswer = "-जी"
                        ),

                        Question(
                            type = QuestionType.MCQ,
                            question = "'बिंदास' means:",
                            options = listOf("Worried", "Cool/Carefree", "Angry"),
                            correctAnswer = "Cool/Carefree"
                        ),

                        Question(
                            type = QuestionType.MCQ,
                            question = "Informal greeting",
                            options = listOf("महोदय", "भाई / यार"),
                            correctAnswer = "भाई / यार"
                        )
                    )
                )
            )
        )
    )
}