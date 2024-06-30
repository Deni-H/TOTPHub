package com.denihilhamsyah.totphub.totp.presentation.component.dialog

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue

@Composable
fun rememberDialogState(
    initial: Boolean = false)
        : DialogState {
    return rememberSaveable(saver = DialogState.Saver) {
        DialogState(initial)
    }
}

class DialogState(initial: Boolean) {
    var isVisible: Boolean by mutableStateOf(initial)
        private set

    fun show() {
        this.isVisible = true
    }

    fun hide() {
        this.isVisible = false
    }

    companion object {
        val Saver: Saver<DialogState, *> = Saver(
            save = { it.isVisible },
            restore = { DialogState(it) }
        )
    }
}