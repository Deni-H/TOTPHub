package com.denihilhamsyah.totphub

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import com.denihilhamsyah.totphub.totp.presentation.TOTPScreen
import com.denihilhamsyah.totphub.totp.presentation.component.theme_switch.rememberThemeSwitchState
import com.denihilhamsyah.totphub.ui.theme.TOTPHubTheme
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private lateinit var analytics: FirebaseAnalytics

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        analytics = Firebase.analytics

        setContent {
            val themeSwitchState = rememberThemeSwitchState(isSystemInDarkTheme())

            TOTPHubTheme(darkTheme = themeSwitchState.isDarkMode) {
                TOTPScreen(
                    modifier = Modifier.fillMaxSize(),
                    themeSwitchState = themeSwitchState
                )
            }
        }
    }
}