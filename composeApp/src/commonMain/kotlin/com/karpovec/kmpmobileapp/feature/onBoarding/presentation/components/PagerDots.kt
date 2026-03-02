package com.karpovec.kmpmobileapp.feature.onBoarding.presentation.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun PagerDots(
    total: Int,
    selected: Int,
    modifier: Modifier = Modifier
) {
    val cs = MaterialTheme.colorScheme

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        repeat(total) { index ->
            val isSelected = index == selected
            Surface(
                shape = RoundedCornerShape(100),
                color = if (isSelected) cs.primary else cs.onBackground.copy(alpha = 0.25f),
                modifier = Modifier
                    .height(8.dp)
                    .width(if (isSelected) 24.dp else 8.dp)
            ) {}
        }
    }
}