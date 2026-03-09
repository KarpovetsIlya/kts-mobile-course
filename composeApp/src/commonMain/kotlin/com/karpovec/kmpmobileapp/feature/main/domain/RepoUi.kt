package com.karpovec.kmpmobileapp.feature.main.domain

data class RepoUi(
    val id: String,
    val name: String,
    val owner: String,
    val description: String?,
    val language: String?,
    val stars: Int,
    val ownerAvatarUrl: String
)