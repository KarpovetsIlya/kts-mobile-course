    package com.karpovec.kmpmobileapp.feature.main.presentation

    import androidx.compose.foundation.background
    import androidx.compose.foundation.layout.*
    import androidx.compose.foundation.lazy.LazyColumn
    import androidx.compose.foundation.lazy.itemsIndexed
    import androidx.compose.material3.*
    import androidx.compose.runtime.*
    import androidx.compose.ui.Modifier
    import androidx.compose.ui.graphics.Color
    import androidx.compose.ui.unit.dp
    import androidx.lifecycle.viewmodel.compose.viewModel
    import coil3.compose.AsyncImage

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun MainScreen(
        viewModel: MainViewModel = viewModel()
    ) {
        val state by viewModel.state.collectAsState()

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
                            .padding(horizontal = 16.dp, vertical = 12.dp)
                    ) {

                        Row(
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {

                            AsyncImage(
                                model = repo.ownerAvatarUrl,
                                contentDescription = "${repo.owner} avatar",
                                modifier = Modifier.size(44.dp)
                            )

                            Column(
                                modifier = Modifier.weight(1f)
                            ) {

                                Text(
                                    text = "${repo.owner} / ${repo.name}",
                                    style = MaterialTheme.typography.titleMedium
                                )

                                if (!repo.description.isNullOrBlank()) {
                                    Spacer(Modifier.height(4.dp))
                                    Text(
                                        text = repo.description!!,
                                        style = MaterialTheme.typography.bodyMedium
                                    )
                                }

                                Spacer(Modifier.height(8.dp))

                                Row(
                                    horizontalArrangement = Arrangement.spacedBy(12.dp)
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
                            modifier = Modifier.padding(horizontal = 16.dp),
                            color = MaterialTheme.colorScheme.outlineVariant
                        )
                    }
                }
            }
        }
    }