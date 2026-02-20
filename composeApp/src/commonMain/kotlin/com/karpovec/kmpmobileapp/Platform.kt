package com.karpovec.kmpmobileapp

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform