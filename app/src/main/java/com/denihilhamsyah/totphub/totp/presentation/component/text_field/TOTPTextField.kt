package com.denihilhamsyah.totphub.totp.presentation.component.text_field

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TOTPTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    shape: Shape = RoundedCornerShape(8.dp),
    focusedBorderThickness: Dp = 2.dp,
    unfocusedBorderThickness: Dp = 2.dp,
    enabled: Boolean = true,
    isError: Boolean = false,
    errorMessage: String? = null,
    placeholder: String,
    singleLine: Boolean = false,
    minLines: Int = 1,
    maxLines: Int = if (singleLine) 1 else Int. MAX_VALUE,
    contentPadding: PaddingValues = PaddingValues(8.dp),
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
) {
    BasicTextField(
        modifier = modifier,
        value = value,
        singleLine = singleLine,
        minLines = minLines,
        maxLines = maxLines,
        interactionSource = interactionSource,
        cursorBrush = SolidColor(MaterialTheme.colorScheme.onSurfaceVariant),
        onValueChange = onValueChange,
        textStyle = MaterialTheme.typography.bodyMedium.copy(
            color = MaterialTheme.colorScheme.primary
        ),
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
    ) { innerTextField ->
        OutlinedTextFieldDefaults.DecorationBox(
            value = value,
            innerTextField = innerTextField,
            contentPadding = contentPadding,
            enabled = enabled,
            isError = isError,
            singleLine = singleLine,
            interactionSource = interactionSource,
            visualTransformation = VisualTransformation.None,
            placeholder = {
                Text(
                    text = placeholder,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    ),
                    fontWeight = FontWeight.Normal
                )
            },
            supportingText = if (isError) { { Text(
                text = errorMessage ?: "",
                color = MaterialTheme.colorScheme.error
            ) } } else null,
            container = {
                OutlinedTextFieldDefaults.ContainerBox(
                    enabled = enabled,
                    isError = isError,
                    interactionSource = interactionSource,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = MaterialTheme.colorScheme.primary,
                        unfocusedBorderColor = MaterialTheme.colorScheme.onSurfaceVariant,
                        focusedTextColor = MaterialTheme.colorScheme.onSecondary,
                        unfocusedTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
                        focusedContainerColor = MaterialTheme.colorScheme.background,
                        unfocusedContainerColor = MaterialTheme.colorScheme.background,
                    ),
                    shape = shape,
                    focusedBorderThickness = focusedBorderThickness,
                    unfocusedBorderThickness = unfocusedBorderThickness
                )
            }
        )
    }
}