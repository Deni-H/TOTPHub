package com.denihilhamsyah.totphub.totp.presentation.component

import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import com.denihilhamsyah.totphub.R
import com.denihilhamsyah.totphub.ui.theme.TOTPHubTheme

@Composable
fun ThemeSwitch(
    modifier: Modifier = Modifier,
    themeSwitchState: ThemeSwitchState,
    onThemeSwitch: (isDarkMode: Boolean) -> Unit
) {
    Switch(
        modifier = modifier,
        checked = themeSwitchState.isDarkMode,
        onCheckedChange = onThemeSwitch,
        thumbContent = {
            Icon(
                imageVector = if (themeSwitchState.isDarkMode) ImageVector.vectorResource(R.drawable.ic_dark_mode)
                              else ImageVector.vectorResource(R.drawable.ic_light_mode),
                contentDescription = stringResource(R.string.theme_mode_icon),
                tint = MaterialTheme.colorScheme.secondary
            )
        },
        colors = SwitchDefaults.colors(
            checkedThumbColor = MaterialTheme.colorScheme.primary,
            checkedTrackColor = MaterialTheme.colorScheme.secondary,
            uncheckedThumbColor = MaterialTheme.colorScheme.primary,
            uncheckedTrackColor = MaterialTheme.colorScheme.secondary,
            uncheckedBorderColor = MaterialTheme.colorScheme.secondary
        )
    )
}

@Preview(showBackground = true)
@Composable
fun ThemeSwitchPreview() {
    val themeSwitchState = rememberThemeSwitchState()

    TOTPHubTheme(themeSwitchState.isDarkMode) {
        ThemeSwitch(
            themeSwitchState = themeSwitchState,
            onThemeSwitch = {
                themeSwitchState.switchMode(it)
            }
        )
    }
}

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