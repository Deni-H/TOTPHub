package com.denihilhamsyah.totphub.totp.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.denihilhamsyah.totphub.R
import com.denihilhamsyah.totphub.qr.domain.DownloadState
import com.denihilhamsyah.totphub.totp.presentation.component.dialog.DialogState
import com.denihilhamsyah.totphub.totp.presentation.component.dialog.DialogWrapper
import com.denihilhamsyah.totphub.totp.presentation.component.dialog.rememberDialogState
import com.denihilhamsyah.totphub.totp.presentation.component.ui_text.asUiText
import com.denihilhamsyah.totphub.ui.theme.TOTPHubTheme

@Composable
fun DownloadModuleDialog(
    modifier: Modifier = Modifier,
    dialogState: DialogState,
    progress: Float,
    downloaded: String,
    total: String,
    downloadState: DownloadState
) {
    DialogWrapper(dialogState = dialogState) {
        Card(shape = RoundedCornerShape(16.dp)) {
            Column(
                modifier = modifier
                    .background(MaterialTheme.colorScheme.background)
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = stringResource(R.string.downloading_qr_module),
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Spacer(modifier = Modifier.height(2.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    Text(
                        text = downloaded.plus(" / $total"),
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    Text(
                        text = "$progress%",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }
                if (downloadState == DownloadState.INSTALLING) {
                    LinearProgressIndicator(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(8.dp))
                    )
                } else {
                    LinearProgressIndicator(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(8.dp)),
                        progress = { progress / 100 },
                    )
                }
                Text(
                    text = downloadState.asUiText().asString(),
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun DownloadModuleDialogPreview() {
    TOTPHubTheme(darkTheme = true) {
        val dialogState = rememberDialogState(true)

        DownloadModuleDialog(
            modifier = Modifier.fillMaxWidth(),
            dialogState = dialogState,
            progress = 5.0F,
            downloaded = "12.5kB",
            total = "7.4MB",
            downloadState = DownloadState.INSTALLING
        )
    }
}