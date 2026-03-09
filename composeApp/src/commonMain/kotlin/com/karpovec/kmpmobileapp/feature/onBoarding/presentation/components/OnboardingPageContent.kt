package com.karpovec.kmpmobileapp.feature.onBoarding.presentation.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.togetherWith
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.util.lerp
import com.karpovec.kmpmobileapp.core.theme.LocalDimens
import com.karpovec.kmpmobileapp.feature.onBoarding.presentation.model.OnboardingPage
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun OnboardingPageContent(
    page: OnboardingPage,
    selectedPage: Int,
    totalPages: Int,
    pageOffset: Float,
    modifier: Modifier = Modifier
) {
    val cs = MaterialTheme.colorScheme
    val d = LocalDimens.current

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.weight(1f))

        val clamped = pageOffset.coerceIn(0f, 1f)

        AnimatedContent(
            targetState = page.image,
            transitionSpec = {
                (fadeIn(spring(stiffness = Spring.StiffnessLow)) +
                        scaleIn(
                            initialScale = 0.92f,
                            animationSpec = spring(stiffness = Spring.StiffnessLow)
                        )) togetherWith
                        (fadeOut(spring(stiffness = Spring.StiffnessLow)) +
                                scaleOut(
                                    targetScale = 1.05f,
                                    animationSpec = spring(stiffness = Spring.StiffnessLow)
                                ))
            },
            label = "onboardingImage"
        ) { imageRes ->

            Image(
                painter = painterResource(imageRes),
                contentDescription = null,
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .fillMaxWidth(0.95f)
                    .heightIn(max = d.space3xl * 6f)
                    .graphicsLayer {
                        alpha = lerp(0.70f, 1f, 1f - clamped)
                        val s = lerp(0.96f, 1f, 1f - clamped)
                        scaleX = s
                        scaleY = s
                        translationY = lerp(18f, 0f, 1f - clamped)
                    }
            )
        }

        Spacer(Modifier.height(d.spaceXs))

        Text(
            text = stringResource(page.title),
            style = MaterialTheme.typography.headlineLarge,
            color = cs.onBackground,
            textAlign = TextAlign.Center
        )

        Spacer(Modifier.height(d.spaceXs))

        Text(
            text = stringResource(page.description),
            style = MaterialTheme.typography.bodyLarge.copy(
                lineHeight = MaterialTheme.typography.bodyLarge.fontSize * 1.4f
            ),
            color = cs.onBackground.copy(alpha = 0.8f),
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = d.spaceS)
        )

        Spacer(Modifier.height(d.spaceM))

        PagerDots(
            total = totalPages,
            selected = selectedPage
        )

        Spacer(Modifier.weight(1f))
    }
}