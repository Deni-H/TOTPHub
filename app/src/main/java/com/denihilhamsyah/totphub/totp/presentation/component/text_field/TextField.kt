package com.denihilhamsyah.totphub.totp.presentation.component.text_field

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun TextField(
    modifier: Modifier = Modifier,
    textFieldState: TextFieldState,
    onValueChange: (String) -> Unit,
    placeholder: String,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    singleLine: Boolean = false,
    minLines: Int = 1,
    maxLines: Int = if (singleLine) 1 else Int.MAX_VALUE
) {
    TOTPTextField(
        modifier = modifier.fillMaxWidth(),
        value = textFieldState.text,
        onValueChange = onValueChange,
        placeholder = placeholder,
        isError = textFieldState.error != null,
        errorMessage = textFieldState.error?.asString(),
        contentPadding = PaddingValues(
            vertical = 8.dp,
            horizontal = 16.dp
        ),
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        singleLine = singleLine,
        minLines = minLines,
        maxLines = maxLines
    )
}