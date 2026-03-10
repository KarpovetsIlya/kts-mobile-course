package com.karpovec.kmpmobileapp.feature.onBoarding.presentation.components

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.karpovec.kmpmobileapp.core.theme.LocalDimens


@Composable
fun PagerDots(
    total: Int,
    selected: Int,
    modifier: Modifier = Modifier
) {
    val cs = MaterialTheme.colorScheme
    val d = LocalDimens.current

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(d.spaceXs),
        verticalAlignment = Alignment.CenterVertically
    ) {
        repeat(total) { index ->
            val isSelected = index == selected

            val w by animateDpAsState(
                targetValue = if (isSelected) d.spaceL else d.spaceXs,
                animationSpec = tween(180),
                label = "dotWidth"
            )
            val a by androidx.compose.runtime.remember(isSelected) {
                mutableStateOf(if (isSelected) 1f else 0.25f)
            }

            Surface(
                shape = RoundedCornerShape(percent = 50),
                color = if (isSelected) cs.primary else cs.onBackground.copy(alpha = 0.25f),
                modifier = Modifier
                    .height(d.spaceXs)
                    .width(w)
            ) {}
        }
    }
}