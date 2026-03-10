package com.karpovec.kmpmobileapp.feature.onBoarding.presentation.model

import com.karpovec.kmpmobileapp.Res
import com.karpovec.kmpmobileapp.offline_favorites
import com.karpovec.kmpmobileapp.onboarding_desc_1
import com.karpovec.kmpmobileapp.onboarding_desc_2
import com.karpovec.kmpmobileapp.onboarding_desc_3
import com.karpovec.kmpmobileapp.onboarding_title_1
import com.karpovec.kmpmobileapp.onboarding_title_2
import com.karpovec.kmpmobileapp.onboarding_title_3
import com.karpovec.kmpmobileapp.profile

import com.karpovec.kmpmobileapp.search_repositories

fun onboardingPages(): List<OnboardingPage> = listOf(
    OnboardingPage(
        title = Res.string.onboarding_title_1,
        description = Res.string.onboarding_desc_1,
        image = Res.drawable.search_repositories
    ),
    OnboardingPage(
        title = Res.string.onboarding_title_2,
        description = Res.string.onboarding_desc_2,
        image = Res.drawable.offline_favorites
    ),
    OnboardingPage(
        title = Res.string.onboarding_title_3,
        description = Res.string.onboarding_desc_3,
        image = Res.drawable.profile
    )
)