package com.karpovec.kmpmobileapp.feature.main.domain

class ReposRepository {
    fun getList(): List<RepoUi> = listOf(
        RepoUi(
            id = "1",
            name = "kotlin",
            owner = "JetBrains",
            description = "The Kotlin Programming Language.",
            language = "Kotlin",
            stars = 50000,
            ownerAvatarUrl = "https://avatars.githubusercontent.com/u/878437?v=4"
        ),

        RepoUi(
            id = "2",
            name = "compose-multiplatform",
            owner = "JetBrains",
            description = "Compose Multiplatform UI framework.",
            language = "Kotlin",
            stars = 15000,
            ownerAvatarUrl = "https://avatars.githubusercontent.com/u/878437?v=4"
        ),

        RepoUi(
            id = "3",
            name = "ktor",
            owner = "ktorio",
            description = "Framework for quickly creating connected applications.",
            language = "Kotlin",
            stars = 13000,
            ownerAvatarUrl = "https://avatars.githubusercontent.com/u/28214161?v=4"
        ),

        RepoUi(
            id = "4",
            name = "retrofit",
            owner = "square",
            description = "A type-safe HTTP client for Android and Java.",
            language = "Java",
            stars = 42000,
            ownerAvatarUrl = "https://avatars.githubusercontent.com/u/82592?v=4"
        ),

        RepoUi(
            id = "5",
            name = "okhttp",
            owner = "square",
            description = "Square’s meticulous HTTP client for Java and Kotlin.",
            language = "Java",
            stars = 45000,
            ownerAvatarUrl = "https://avatars.githubusercontent.com/u/82592?v=4"
        ),

        RepoUi(
            id = "6",
            name = "coil",
            owner = "coil-kt",
            description = "Image loading for Android backed by Kotlin Coroutines.",
            language = "Kotlin",
            stars = 10000,
            ownerAvatarUrl = "https://avatars.githubusercontent.com/u/6187816?v=4"
        ),

        RepoUi(
            id = "7",
            name = "flutter",
            owner = "flutter",
            description = "Flutter makes it easy and fast to build beautiful apps.",
            language = "Dart",
            stars = 160000,
            ownerAvatarUrl = "https://avatars.githubusercontent.com/u/14101776?v=4"
        ),

        RepoUi(
            id = "8",
            name = "react",
            owner = "facebook",
            description = "A declarative, efficient, and flexible JavaScript library.",
            language = "JavaScript",
            stars = 210000,
            ownerAvatarUrl = "https://avatars.githubusercontent.com/u/69631?v=4"
        ),

        RepoUi(
            id = "9",
            name = "spring-boot",
            owner = "spring-projects",
            description = "Spring Boot makes it easy to create stand-alone apps.",
            language = "Java",
            stars = 73000,
            ownerAvatarUrl = "https://avatars.githubusercontent.com/u/317776?v=4"
        ),

        RepoUi(
            id = "10",
            name = "fastapi",
            owner = "tiangolo",
            description = "FastAPI framework, high performance, easy to learn.",
            language = "Python",
            stars = 72000,
            ownerAvatarUrl = "https://avatars.githubusercontent.com/u/670639?v=4"
        ),

        RepoUi(
            id = "11",
            name = "node",
            owner = "nodejs",
            description = "Node.js JavaScript runtime.",
            language = "JavaScript",
            stars = 98000,
            ownerAvatarUrl = "https://avatars.githubusercontent.com/u/9950313?v=4"
        ),

        RepoUi(
            id = "12",
            name = "swift",
            owner = "apple",
            description = "The Swift Programming Language.",
            language = "Swift",
            stars = 65000,
            ownerAvatarUrl = "https://avatars.githubusercontent.com/u/10639145?v=4"
        ),

        RepoUi(
            id = "13",
            name = "laravel",
            owner = "laravel",
            description = "The PHP Framework for Web Artisans.",
            language = "PHP",
            stars = 77000,
            ownerAvatarUrl = "https://avatars.githubusercontent.com/u/958072?v=4"
        ),

        RepoUi(
            id = "14",
            name = "redis",
            owner = "redis",
            description = "In-memory data structure store, used as a database.",
            language = "C",
            stars = 64000,
            ownerAvatarUrl = "https://avatars.githubusercontent.com/u/1529926?v=4"
        ),

        RepoUi(
            id = "15",
            name = "tensorflow",
            owner = "tensorflow",
            description = "An end-to-end open source machine learning platform.",
            language = "C++",
            stars = 180000,
            ownerAvatarUrl = "https://avatars.githubusercontent.com/u/15658638?v=4"
        )
    )
}