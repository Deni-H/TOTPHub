package com.denihilhamsyah.totphub.totp.presentation.component.dialog

import androidx.compose.runtime.Composable
import androidx.compose.ui.window.Dialog

@Composable
fun DialogWrapper(
    dialogState: DialogState,
    onDismissRequest: () -> Unit,
    content: @Composable () -> Unit
) {
    if (dialogState.isVisible) {
        Dialog(
            onDismissRequest = onDismissRequest,
            content = content
        )
    }
}