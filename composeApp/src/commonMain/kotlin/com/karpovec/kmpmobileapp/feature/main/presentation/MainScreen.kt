package com.karpovec.kmpmobileapp.feature.main.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.karpovec.kmpmobileapp.core.theme.LocalDimens
import kotlinx.coroutines.flow.distinctUntilChanged

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    viewModel: MainViewModel
) {
    val state by viewModel.state.collectAsState()
    val d = LocalDimens.current
    val listState = rememberLazyListState()

    LaunchedEffect(listState, state.items.size, state.isLoadingMore, state.hasMore) {
        snapshotFlow {
            val lastVisibleItemIndex = listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: 0
            val totalItemsCount = listState.layoutInfo.totalItemsCount
            totalItemsCount > 0 && lastVisibleItemIndex >= totalItemsCount - 3
        }
            .distinctUntilChanged()
            .collect { shouldLoadNextPage ->
                if (shouldLoadNextPage) {
                    viewModel.loadNextPage()
                }
            }
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("GitHub Explorer") }
            )
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            OutlinedTextField(
                value = state.query,
                onValueChange = viewModel::onQueryChanged,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = d.screenPadding,
                        vertical = d.spaceS
                    ),
                label = { Text("Поиск репозиториев") },
                singleLine = true
            )

            when {
                state.isLoading && state.items.isEmpty() -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }

                state.errorMessage != null && state.items.isEmpty() -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(d.spaceM)
                        ) {
                            Text(
                                text = state.errorMessage ?: "Unknown error",
                                style = MaterialTheme.typography.bodyLarge
                            )
                            Button(
                                onClick = viewModel::retry
                            ) {
                                Text("Повторить")
                            }
                        }
                    }
                }

                state.items.isEmpty() -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(d.spaceM)
                        ) {
                            AsyncImage(
                                model = "https://cdn-icons-png.flaticon.com/512/7486/7486740.png",
                                contentDescription = "Пустой список",
                                modifier = Modifier.size(120.dp)
                            )

                            Text(
                                text = "Ничего не найдено",
                                style = MaterialTheme.typography.bodyLarge
                            )
                        }
                    }
                }

                else -> {
                    LazyColumn(
                        state = listState,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        itemsIndexed(
                            items = state.items,
                            key = { _, repo -> repo.id }
                        ) { index, repo ->

                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(Color.Transparent)
                                    .padding(
                                        horizontal = d.screenPadding,
                                        vertical = d.spaceS
                                    )
                            ) {
                                Row(
                                    horizontalArrangement = Arrangement.spacedBy(d.spaceS)
                                ) {
                                    AsyncImage(
                                        model = repo.owner.avatarUrl,
                                        contentDescription = "${repo.owner.login} avatar",
                                        modifier = Modifier.size(d.avatarSize)
                                    )

                                    Column(
                                        modifier = Modifier.weight(1f)
                                    ) {
                                        Text(
                                            text = "${repo.owner.login} / ${repo.name}",
                                            style = MaterialTheme.typography.titleMedium
                                        )

                                        if (!repo.description.isNullOrBlank()) {
                                            Spacer(Modifier.height(d.spaceXs))
                                            Text(
                                                text = repo.description,
                                                style = MaterialTheme.typography.bodyMedium
                                            )
                                        }

                                        Spacer(Modifier.height(d.spaceM))

                                        Row(
                                            horizontalArrangement = Arrangement.spacedBy(d.spaceS)
                                        ) {
                                            Text(
                                                text = repo.language ?: "Unknown",
                                                style = MaterialTheme.typography.labelMedium,
                                                color = MaterialTheme.colorScheme.onSurfaceVariant
                                            )

                                            Text(
                                                text = "★ ${repo.stargazersCount}",
                                                style = MaterialTheme.typography.labelMedium,
                                                color = MaterialTheme.colorScheme.onSurfaceVariant
                                            )
                                        }
                                    }
                                }
                            }

                            if (index < state.items.lastIndex) {
                                HorizontalDivider(
                                    modifier = Modifier.padding(horizontal = d.screenPadding),
                                    color = MaterialTheme.colorScheme.outlineVariant
                                )
                            }
                        }

                        if (state.isLoadingMore) {
                            item {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = d.spaceM),
                                    contentAlignment = Alignment.Center
                                ) {
                                    CircularProgressIndicator()
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}