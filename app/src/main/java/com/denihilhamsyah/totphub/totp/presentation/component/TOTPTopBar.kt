package com.denihilhamsyah.totphub.totp.presentation.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.denihilhamsyah.totphub.ui.theme.TOTPHubTheme

@Composable
fun TOTPTopBar(
    modifier: Modifier = Modifier,
    title: String,
    themeSwitchState: ThemeSwitchState,
    onThemeSwitch: (isDarkMode: Boolean) -> Unit
) {
    Row(
        modifier = modifier
            .systemBarsPadding()
            .padding(16.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        Text(
            modifier = Modifier.weight(1f),
            text = title,
            style = MaterialTheme.typography.headlineSmall.copy(
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onBackground
            ),
        )
        ThemeSwitch(
            themeSwitchState = themeSwitchState,
            onThemeSwitch = onThemeSwitch
        )
    }
}

@Preview(
    showBackground = true,
    backgroundColor = 0xFFEEF5FF
)
@Composable
fun TOTPTopBarPreview(modifier: Modifier = Modifier) {
    val themeSwitchState = rememberThemeSwitchState()

    TOTPHubTheme(darkTheme = themeSwitchState.isDarkMode) {
        TOTPTopBar(
            title = "TOTPHub",
            themeSwitchState = themeSwitchState,
            onThemeSwitch = themeSwitchState::switchMode
        )
    }
}