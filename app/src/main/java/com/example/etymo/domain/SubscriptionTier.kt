package com.example.etymo.domain

enum class SubscriptionTier(
    val title: String,
    val priceMonthly: Int,
    val features: List<String>,
    val description: String
) {
    FREE(
        title = "Etymo Free",
        priceMonthly = 0,
        features = listOf(
            "Full course access",
            "Ad-supported",
            "Strictly limited to 5 Hearts/day"
        ),
        description = "Learn the basics at no cost."
    ),
    PLUS(
        title = "Etymo Plus",
        priceMonthly = 199,
        features = listOf(
            "Ad-free experience",
            "Unlimited Hearts",
            "Offline Access (download units for travel)"
        ),
        description = "Accelerate your learning without interruptions."
    ),
    PARIVAR(
        title = "Etymo Parivar",
        priceMonthly = 299,
        features = listOf(
            "All Plus features",
            "Shared among up to 4 user accounts"
        ),
        description = "Language learning for the whole family."
    ),
    GURU(
        title = "Etymo Guru",
        priceMonthly = 599,
        features = listOf(
            "All Plus features",
            "AI Roleplay (chat with AI)",
            "Explain My Answer (AI grammar explanations)"
        ),
        description = "The ultimate AI-powered mastery plan."
    )
}
