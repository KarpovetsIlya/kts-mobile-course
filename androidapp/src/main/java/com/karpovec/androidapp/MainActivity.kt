package com.karpovec.androidapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.karpovec.kmpmobileapp.app.App
import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        Napier.base(DebugAntilog())
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContent {
            App()
        }
    }
}

