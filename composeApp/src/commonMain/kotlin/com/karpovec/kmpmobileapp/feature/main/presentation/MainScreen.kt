package com.karpovec.kmpmobileapp.feature.main.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.AsyncImage
import com.karpovec.kmpmobileapp.core.theme.LocalDimens

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    viewModel: MainViewModel = viewModel()
) {
    val state by viewModel.state.collectAsState()
    val d = LocalDimens.current

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("GitHub Explorer") }
            )
        }
    ) { padding ->

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
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
                            model = repo.ownerAvatarUrl,
                            contentDescription = "${repo.owner} avatar",
                            modifier = Modifier.size(d.avatarSize)
                        )

                        Column(
                            modifier = Modifier.weight(1f)
                        ) {

                            Text(
                                text = "${repo.owner} / ${repo.name}",
                                style = MaterialTheme.typography.titleMedium
                            )

                            if (!repo.description.isNullOrBlank()) {
                                Spacer(Modifier.height(d.spaceXs))
                                Text(
                                    text = repo.description!!,
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
                                    text = "★ ${repo.stars}",
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
        }
    }
}