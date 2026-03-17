package com.example.etymo.data.local

object BengaliLessons {

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
                            options = listOf("নমস্কার", "বিদায়", "ধন্যবাদ"),
                            correctAnswer = "নমস্কার"
                        ),

                        Question(
                            type = QuestionType.WORD_JUMBLE,
                            question = "Arrange the words to say 'My name is Joy.'",
                            options = listOf("নাম", "আমার", "জয়", "।"),
                            correctAnswer = "আমার নাম জয়।"
                        ),

                        Question(
                            type = QuestionType.PRONUNCIATION,
                            question = "The word 'আমি' (I) sounds like:",
                            options = listOf("Ah-mee", "Ay-my", "Am-i"),
                            correctAnswer = "Ah-mee"
                        ),

                        Question(
                            type = QuestionType.MCQ,
                            question = "Translate 'How are you?'",
                            options = listOf(
                                "আপনি কেমন আছেন?",
                                "আপনার নাম কি?",
                                "আপনি কোথায় যান?"
                            ),
                            correctAnswer = "আপনি কেমন আছেন?"
                        ),

                        Question(
                            type = QuestionType.MATCH,
                            question = "Match the pairs",
                            matchPairs = listOf(
                                "হ্যাঁ" to "Yes",
                                "না" to "No"
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
                            options = listOf("খাবার", "জল", "দুধ"),
                            correctAnswer = "জল"
                        ),

                        Question(
                            type = QuestionType.WORD_JUMBLE,
                            question = "Arrange the words to ask 'Where is the room?'",
                            options = listOf("ঘরটি", "কোথায়", "?"),
                            correctAnswer = "ঘরটি কোথায়?"
                        ),

                        Question(
                            type = QuestionType.MCQ,
                            question = "Fill in the blank: 'আমার ______ পেয়েছে।'",
                            options = listOf("তৃষ্ণা", "খিদে", "ঘুম"),
                            correctAnswer = "খিদে"
                        ),

                        Question(
                            type = QuestionType.PRONUNCIATION,
                            question = "'জল' (Water) starts with:",
                            options = listOf(
                                "A soft 'J' (as in 'Jug')",
                                "A 'Z' sound",
                                "A 'D' sound"
                            ),
                            correctAnswer = "A soft 'J' (as in 'Jug')"
                        ),

                        Question(
                            type = QuestionType.MCQ,
                            question = "Which word means 'Sleep'?",
                            options = listOf("ঘুমানো", "হাঁটা", "দেখা"),
                            correctAnswer = "ঘুমানো"
                        )
                    )
                ),

                LessonUnit(
                    title = "Numbers & Time",
                    questions = listOf(

                        Question(
                            type = QuestionType.MCQ,
                            question = "What is the number '5'?",
                            options = listOf("তিন", "পাঁচ", "সাত"),
                            correctAnswer = "পাঁচ"
                        ),

                        Question(
                            type = QuestionType.WORD_JUMBLE,
                            question = "Form the sentence 'It is 4 o'clock.'",
                            options = listOf("চারটে", "বেজেছে", "এখন", "।"),
                            correctAnswer = "এখন চারটে বেজেছে।"
                        ),

                        Question(
                            type = QuestionType.MCQ,
                            question = "How do you say 'Today'?",
                            options = listOf("গতকাল", "আজ", "আগামীকাল"),
                            correctAnswer = "আজ"
                        ),

                        Question(
                            type = QuestionType.PRONUNCIATION,
                            question = "The number '10' (দশ) sounds like:",
                            options = listOf(
                                "Dos",
                                "Dosh (with 'sh')",
                                "Dash"
                            ),
                            correctAnswer = "Dosh (with 'sh')"
                        ),

                        Question(
                            type = QuestionType.MCQ,
                            question = "Translate 'What time is it?'",
                            options = listOf("কটা বাজে?", "আপনি কখন আসবেন?"),
                            correctAnswer = "কটা বাজে?"
                        )
                    )
                ),

                LessonUnit(
                    title = "People",
                    questions = listOf(

                        Question(
                            type = QuestionType.MCQ,
                            question = "Identify the word for 'Mother'",
                            options = listOf("বাবা", "মা", "ভাই"),
                            correctAnswer = "মা"
                        ),

                        Question(
                            type = QuestionType.WORD_JUMBLE,
                            question = "Arrange: 'He is my brother.'",
                            options = listOf("আমার", "সে", "ভাই", "।"),
                            correctAnswer = "সে আমার ভাই।"
                        ),

                        Question(
                            type = QuestionType.MCQ,
                            question = "Translate 'Teacher'",
                            options = listOf("ডাক্তার", "শিক্ষক", "চাষী"),
                            correctAnswer = "শিক্ষক"
                        ),

                        Question(
                            type = QuestionType.PRONUNCIATION,
                            question = "'মেয়ে' sounds like:",
                            options = listOf("May-ay", "Mee-ee", "My-ay"),
                            correctAnswer = "May-ay"
                        ),

                        Question(
                            type = QuestionType.MCQ,
                            question = "Which word means 'Friend'?",
                            options = listOf("বন্ধু", "শত্রু", "প্রতিবেশী"),
                            correctAnswer = "বন্ধু"
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
                            options = listOf("এটা কি?", "এটার দাম কত?"),
                            correctAnswer = "এটার দাম কত?"
                        ),

                        Question(
                            type = QuestionType.WORD_JUMBLE,
                            question = "Arrange: 'The price is very high.'",
                            options = listOf("খুব", "দাম", "বেশি", "।"),
                            correctAnswer = "দাম খুব বেশি।"
                        ),

                        Question(
                            type = QuestionType.MCQ,
                            question = "Select the color 'Blue'",
                            options = listOf("লাল", "নীল", "সবুজ"),
                            correctAnswer = "নীল"
                        ),

                        Question(
                            type = QuestionType.PRONUNCIATION,
                            question = "'দামি' starts with:",
                            options = listOf(
                                "Soft dental D",
                                "Hard retroflex D",
                                "T sound"
                            ),
                            correctAnswer = "Soft dental D"
                        ),

                        Question(
                            type = QuestionType.MCQ,
                            question = "Opposite of 'ভারী'",
                            options = listOf("হালকা", "বড়", "ছোট"),
                            correctAnswer = "হালকা"
                        )
                    )
                ),

                LessonUnit(
                    title = "Feelings & Health",
                    questions = listOf(

                        Question(
                            type = QuestionType.MCQ,
                            question = "How do you say 'I am happy'?",
                            options = listOf("আমি খুব দুঃখিত", "আমি খুব খুশি"),
                            correctAnswer = "আমি খুব খুশি"
                        ),

                        Question(
                            type = QuestionType.WORD_JUMBLE,
                            question = "Arrange: 'I have a fever.'",
                            options = listOf("হয়েছে", "আমার", "জ্বর", "।"),
                            correctAnswer = "আমার জ্বর হয়েছে।"
                        ),

                        Question(
                            type = QuestionType.MCQ,
                            question = "Identify 'Medicine'",
                            options = listOf("মিষ্টি", "ওষুধ", "বই"),
                            correctAnswer = "ওষুধ"
                        ),

                        Question(
                            type = QuestionType.PRONUNCIATION,
                            question = "'ব্যথা' starts with:",
                            options = listOf("Ba-tha", "Be-tha", "Bye-tha"),
                            correctAnswer = "Be-tha"
                        ),

                        Question(
                            type = QuestionType.MCQ,
                            question = "Translate 'I am sick'",
                            options = listOf("আমি সুস্থ", "আমি অসুস্থ"),
                            correctAnswer = "আমি অসুস্থ"
                        )
                    )
                ),

                LessonUnit(
                    title = "Travel",
                    questions = listOf(

                        Question(
                            type = QuestionType.MCQ,
                            question = "Where is the Station?",
                            options = listOf("স্টেশন কোথায়?", "রাস্তা কোথায়?"),
                            correctAnswer = "স্টেশন কোথায়?"
                        ),

                        Question(
                            type = QuestionType.WORD_JUMBLE,
                            question = "Arrange: 'The train has arrived.'",
                            options = listOf("ট্রেন", "এসেছে", "।"),
                            correctAnswer = "ট্রেন এসেছে।"
                        ),

                        Question(
                            type = QuestionType.MCQ,
                            question = "Which way is 'Right'?",
                            options = listOf("বাঁদিকে", "ডানদিকে", "সোজা"),
                            correctAnswer = "ডানদিকে"
                        ),

                        Question(
                            type = QuestionType.PRONUNCIATION,
                            question = "'গাড়ি' has:",
                            options = listOf(
                                "Normal R",
                                "Flapped R",
                                "Silent"
                            ),
                            correctAnswer = "Flapped R"
                        ),

                        Question(
                            type = QuestionType.MCQ,
                            question = "Translate 'Stop here'",
                            options = listOf("এখানে থামুন", "ওখানে যান"),
                            correctAnswer = "এখানে থামুন"
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
                            options = listOf("আমি গিয়েছিলাম", "আমি যাব"),
                            correctAnswer = "আমি যাব"
                        ),

                        Question(
                            type = QuestionType.WORD_JUMBLE,
                            question = "Arrange: 'I will eat if you come.'",
                            options = listOf("তুমি", "আমি", "আসো", "খেলে", "খাব", "যদি", "।"),
                            correctAnswer = "তুমি আসো যদি আমি খাব।"
                        ),

                        Question(
                            type = QuestionType.MCQ,
                            question = "Word for 'But'",
                            options = listOf("এবং", "কিন্তু", "অথবা"),
                            correctAnswer = "কিন্তু"
                        ),

                        Question(
                            type = QuestionType.PRONUNCIATION,
                            question = "The letter 'ড়' is:",
                            options = listOf(
                                "Like D",
                                "Flapped R sound",
                                "Like V"
                            ),
                            correctAnswer = "Flapped R sound"
                        ),

                        Question(
                            type = QuestionType.MCQ,
                            question = "'I was doing' is:",
                            options = listOf("আমি করছিলাম", "আমি করেছি"),
                            correctAnswer = "আমি করছিলাম"
                        )
                    )
                ),

                LessonUnit(
                    title = "Culture & Slang",
                    questions = listOf(

                        Question(
                            type = QuestionType.MCQ,
                            question = "'মাছের মায়ের পুত্রশোক' means:",
                            options = listOf(
                                "Real sadness",
                                "Fake sympathy",
                                "Love for fish"
                            ),
                            correctAnswer = "Fake sympathy"
                        ),

                        Question(
                            type = QuestionType.WORD_JUMBLE,
                            question = "Arrange: 'Knowledge is power'",
                            options = listOf("জ্ঞানই", "শক্তি", "।"),
                            correctAnswer = "জ্ঞানই শক্তি।"
                        ),

                        Question(
                            type = QuestionType.MCQ,
                            question = "Respectful elder brother term",
                            options = listOf("দাদা", "ভাই", "কাকা"),
                            correctAnswer = "দাদা"
                        ),

                        Question(
                            type = QuestionType.MCQ,
                            question = "'ফাটায় দিস' means:",
                            options = listOf("Break it", "Rock it", "Cry"),
                            correctAnswer = "Rock it"
                        ),

                        Question(
                            type = QuestionType.MCQ,
                            question = "Common sweet",
                            options = listOf("রসগোল্লা", "সিঙাড়া", "লুচি"),
                            correctAnswer = "রসগোল্লা"
                        )
                    )
                )
            )
        )
    )
}