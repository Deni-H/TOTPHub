package com.denihilhamsyah.totphub.totp.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.denihilhamsyah.totphub.R
import com.denihilhamsyah.totphub.totp.presentation.component.PrimaryButton
import com.denihilhamsyah.totphub.totp.presentation.component.dialog.DialogState
import com.denihilhamsyah.totphub.totp.presentation.component.dialog.DialogWrapper
import com.denihilhamsyah.totphub.totp.presentation.component.text_field.TextField
import com.denihilhamsyah.totphub.totp.presentation.component.text_field.TextFieldState

@Composable
fun AddSecretDialog(
    modifier: Modifier = Modifier,
    dialogState: DialogState,
    accountNameFieldState: TextFieldState,
    onAccountNameFieldChange: (String) -> Unit,
    secretLabelFieldState: TextFieldState,
    onSecretLabelFieldChange: (String) -> Unit,
    secretFieldState: TextFieldState,
    onSecretFieldChange: (String) -> Unit,
    isButtonEnabled: Boolean = true,
    onButtonClick: () -> Unit
) {
    DialogWrapper(
        dialogState = dialogState,
        onDismissRequest = dialogState::hide
    ) {
        Card(shape = RoundedCornerShape(16.dp)) {
            Column(
                modifier = modifier
                    .background(MaterialTheme.colorScheme.background)
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = stringResource(R.string.enter_account_details),
                    style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.SemiBold),
                    color = MaterialTheme.colorScheme.onBackground
                )
                Spacer(modifier = Modifier.height(2.dp))
                TextField(
                    textFieldState = accountNameFieldState,
                    onValueChange = onAccountNameFieldChange,
                    placeholder = stringResource(R.string.account_name)
                )
                TextField(
                    textFieldState = secretLabelFieldState,
                    onValueChange = onSecretLabelFieldChange,
                    placeholder = stringResource(R.string.secret_label)
                )
                TextField(
                    textFieldState = secretFieldState,
                    onValueChange = onSecretFieldChange,
                    placeholder = stringResource(R.string.secret)
                )
                Spacer(modifier = Modifier.height(2.dp))
                PrimaryButton(
                    modifier = Modifier
                        .fillMaxWidth(),
                    radius = 8.dp,
                    text = stringResource(R.string.add),
                    enabled = isButtonEnabled,
                    onClick = onButtonClick
                )
            }
        }
    }
}