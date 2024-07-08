package com.denihilhamsyah.totphub.totp.presentation.component.text_field

import com.denihilhamsyah.totphub.totp.presentation.component.ui_text.UiText

data class TextFieldState(
    val text: String = "",
    val error: UiText? = null
)
