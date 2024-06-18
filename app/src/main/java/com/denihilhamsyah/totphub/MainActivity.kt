package com.denihilhamsyah.totphub

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.denihilhamsyah.totphub.totp.presentation.CodeScreen
import com.denihilhamsyah.totphub.ui.theme.TOTPHubTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TOTPHubTheme {
                CodeScreen()
            }
        }
    }
}