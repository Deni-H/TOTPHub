package com.denihilhamsyah.totphub.totp.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.denihilhamsyah.totphub.R
import com.denihilhamsyah.totphub.totp.presentation.component.dialog.DialogState
import com.denihilhamsyah.totphub.totp.presentation.component.dialog.DialogWrapper
import com.denihilhamsyah.totphub.totp.presentation.component.dialog.rememberDialogState
import com.denihilhamsyah.totphub.ui.theme.TOTPHubTheme

@Composable
fun AddSecretMenuDialog(
    modifier: Modifier = Modifier,
    dialogState: DialogState,
    onScanQr: () -> Unit,
    onEnterManually: () -> Unit,
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
                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    onClick = {
                        onScanQr()
                        dialogState.hide()
                    },
                    colors = ButtonDefaults.buttonColors().copy(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.onPrimary
                    )
                ) {
                    Text(text = stringResource(R.string.scan_qr_code))
                    Spacer(modifier = Modifier.width(8.dp))
                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.ic_scan),
                        contentDescription = stringResource(R.string.scan_icon)
                    )
                }

                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    onClick = {
                        onEnterManually()
                        dialogState.hide()
                    },
                    colors = ButtonDefaults.buttonColors().copy(
                        containerColor = MaterialTheme.colorScheme.secondary,
                        contentColor = MaterialTheme.colorScheme.onSecondary
                    )
                ) {
                    Text(text = stringResource(R.string.enter_manually))
                    Spacer(modifier = Modifier.width(8.dp))
                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.ic_keyboard),
                        contentDescription = stringResource(R.string.add_manually_icon)
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun AddSecretMenuDialogPreview() {
    val dialogState = rememberDialogState()
    dialogState.show()
    TOTPHubTheme {
        AddSecretMenuDialog(
            dialogState = dialogState,
            onScanQr = {  },
            onEnterManually = {  }
        )
    }
}