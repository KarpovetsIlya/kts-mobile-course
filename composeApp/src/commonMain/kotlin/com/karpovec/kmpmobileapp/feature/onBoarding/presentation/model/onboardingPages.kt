package com.karpovec.kmpmobileapp.feature.onBoarding.presentation.model

fun onboardingPages(): List<OnboardingPage> = listOf(
    OnboardingPage(
        title = "GitHub Explorer",
        description = "Ищи репозитории, смотри описание, язык и звезды — быстро и удобно.",
        emoji = "🔎"
    ),
    OnboardingPage(
        title = "Избранное офлайн",
        description = "Сохраняй понравившиеся репозитории и открывай их без интернета.",
        emoji = "⭐"
    ),
    OnboardingPage(
        title = "Профиль и Issues",
        description = "Просматривай профиль и создавай issues прямо из приложения.",
        emoji = "📝"
    )
)