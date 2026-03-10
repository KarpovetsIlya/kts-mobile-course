package com.karpovec.kmpmobileapp.feature.onBoarding.presentation

import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.karpovec.kmpmobileapp.core.theme.LocalDimens
import com.karpovec.kmpmobileapp.feature.onBoarding.presentation.components.OnboardingPageContent
import com.karpovec.kmpmobileapp.feature.onBoarding.presentation.model.onboardingPages
import kotlinx.coroutines.launch
import kotlin.math.absoluteValue

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OnBoardingScreen(
    onFinish: () -> Unit
) {
    val cs = MaterialTheme.colorScheme
    val d = LocalDimens.current
    val scope = rememberCoroutineScope()

    val pages = remember { onboardingPages() }
    val pagerState = rememberPagerState(pageCount = { pages.size })
    val isLastPage = pagerState.currentPage == pages.lastIndex

    Scaffold(
        containerColor = cs.background
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = d.screenPadding)
        ) {

            Spacer(Modifier.height(d.space2xl))

            HorizontalPager(
                state = pagerState,
                userScrollEnabled = false,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) { page ->

                val pageOffset =
                    ((pagerState.currentPage - page) + pagerState.currentPageOffsetFraction)
                        .absoluteValue

                OnboardingPageContent(
                    page = pages[page],
                    selectedPage = pagerState.currentPage,
                    totalPages = pages.size,
                    pageOffset = pageOffset,
                    modifier = Modifier.fillMaxSize()
                )
            }

            Spacer(Modifier.height(d.spaceL))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = d.space3xl),
                horizontalArrangement = Arrangement.spacedBy(d.spaceM),
                verticalAlignment = Alignment.CenterVertically
            ) {

                TextButton(
                    onClick = { onFinish() },
                    modifier = Modifier.height(48.dp)
                ) {
                    Text(
                        text = "Skip",
                        style = MaterialTheme.typography.titleMedium
                    )
                }

                Button(
                    onClick = {
                        if (isLastPage) {
                            onFinish()
                        } else {
                            scope.launch {
                                pagerState.animateScrollToPage(
                                    pagerState.currentPage + 1
                                )
                            }
                        }
                    },
                    modifier = Modifier
                        .weight(1f)
                        .height(56.dp),
                    shape = RoundedCornerShape(d.buttonCorner),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = cs.primary,
                        contentColor = cs.onPrimary
                    )
                ) {

                    Crossfade(
                        targetState = isLastPage,
                        animationSpec = tween(200),
                        label = "buttonText"
                    ) { lastPage ->

                        Text(
                            text = if (lastPage) "Start" else "Next",
                            style = MaterialTheme.typography.titleMedium
                        )
                    }
                }
            }
        }
    }
}