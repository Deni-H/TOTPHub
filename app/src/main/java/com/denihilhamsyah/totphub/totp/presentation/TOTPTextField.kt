package com.denihilhamsyah.totphub.totp.presentation

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.denihilhamsyah.totphub.ui.theme.TOTPHubTheme

@Composable
fun TOTPTextField(
    modifier: Modifier = Modifier,
    textFieldState: TextFieldState,
    onValueChange: (String) -> Unit,
    label: String,
    singleLine: Boolean = true
) {
    OutlinedTextField(
        modifier = modifier,
        value = textFieldState.text,
        onValueChange = onValueChange,
        label = {
            Text(
                text = label,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Normal
            )
        },
        singleLine = singleLine,
        isError = textFieldState.error != null,
        supportingText = {
            textFieldState.error?.let { Text(it.asString()) }
        },
        textStyle = MaterialTheme.typography.bodyMedium
    )
}

@Preview(showBackground = true)
@Composable
fun TOTPTextFieldPreview() {

    var secret by remember { mutableStateOf("") }

    TOTPHubTheme {
        TOTPTextField(
            modifier = Modifier.fillMaxWidth(),
            label = "Secret",
            textFieldState = TextFieldState(
                text = secret,
                error = null
            ),
            onValueChange = { secret = it }
        )
    }
}