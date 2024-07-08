package com.denihilhamsyah.totphub.totp.presentation.component.theme_switch

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue

@Composable
fun rememberThemeSwitchState(
    initial: Boolean = false)
        : ThemeSwitchState {
    return rememberSaveable(saver = ThemeSwitchState.Saver) {
        ThemeSwitchState(initial)
    }
}

class ThemeSwitchState(initial: Boolean) {
    var isDarkMode: Boolean by mutableStateOf(initial)
        private set

    fun switchMode(isDarkMode: Boolean) {
        this.isDarkMode = isDarkMode
    }

    companion object {
        val Saver: Saver<ThemeSwitchState, *> = Saver(
            save = { it.isDarkMode },
            restore = { ThemeSwitchState(it) }
        )
    }
}