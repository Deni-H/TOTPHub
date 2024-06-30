package com.denihilhamsyah.totphub.totp.presentation.component.text_field

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun TextField(
    modifier: Modifier = Modifier,
    textFieldState: TextFieldState,
    onValueChange: (String) -> Unit,
    placeholder: String
) {
    TOTPTextField(
        modifier = Modifier.fillMaxWidth(),
        value = textFieldState.text,
        onValueChange = onValueChange,
        placeholder = placeholder,
        isError = textFieldState.error != null,
        errorMessage = textFieldState.error?.asString(),
        contentPadding = PaddingValues(
            vertical = 8.dp,
            horizontal = 16.dp
        )
    )
}