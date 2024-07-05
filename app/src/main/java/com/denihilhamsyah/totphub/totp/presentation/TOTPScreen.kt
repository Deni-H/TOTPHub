package com.denihilhamsyah.totphub.totp.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey
import com.denihilhamsyah.totphub.R
import com.denihilhamsyah.totphub.totp.domain.model.SecretDetails
import com.denihilhamsyah.totphub.totp.presentation.component.ObserveAsEvents
import com.denihilhamsyah.totphub.totp.presentation.component.PrimaryButton
import com.denihilhamsyah.totphub.totp.presentation.component.SecondaryButton
import com.denihilhamsyah.totphub.totp.presentation.component.TOTPTopBar
import com.denihilhamsyah.totphub.totp.presentation.component.dialog.rememberDialogState
import com.denihilhamsyah.totphub.totp.presentation.component.theme_switch.ThemeSwitchState
import com.denihilhamsyah.totphub.totp.presentation.component.time_indicator.TimeIndicator
import com.denihilhamsyah.totphub.ui.theme.TOTPHubTheme
import kotlinx.coroutines.launch

@Composable
fun TOTPScreen(
    modifier: Modifier = Modifier,
    viewModel: TOTPViewModel = hiltViewModel(),
    themeSwitchState: ThemeSwitchState
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    val dialogState = rememberDialogState()

    val secrets = viewModel.secrets.collectAsLazyPagingItems()
    val state by viewModel.totpState.collectAsStateWithLifecycle()

    val isSecretLoading = secrets.loadState.refresh is LoadState.Loading

    val accountNameFieldState by viewModel.accountNameFieldState.collectAsStateWithLifecycle()
    val secretFieldState by viewModel.secretFieldState.collectAsStateWithLifecycle()
    val secretLabelFieldState by viewModel.secretLabelFieldState.collectAsStateWithLifecycle()
    val remainingCountDown by viewModel.remainingCountDown.collectAsStateWithLifecycle()

    ObserveAsEvents(viewModel.totpEvent) { event ->
        when (event) {
            is TOTPEvent.OnSecretAdded -> {
                coroutineScope.launch {
                    snackbarHostState.showSnackbar(event.uiText.asString(context))
                }
            }
            is TOTPEvent.OnOperationFailed -> {
                coroutineScope.launch {
                    snackbarHostState.showSnackbar(event.uiText.asString(context))
                }
            }
        }
    }

    if (state.isLoading) LoadingDialog()

    AddSecretDialog(
        dialogState = dialogState,
        accountNameFieldState = accountNameFieldState,
        onAccountNameFieldChange = viewModel::onAccountNameFieldChange,
        secretLabelFieldState = secretLabelFieldState,
        onSecretLabelFieldChange = viewModel::onSecretLabelFieldChange,
        secretFieldState = secretFieldState,
        onSecretFieldChange = viewModel::onSecretFieldChange,
        onButtonClick = {
            viewModel.onAddSecretDialogClick()
            dialogState.hide()
        }
    )

    Scaffold(
        modifier = modifier,
        floatingActionButton = {
            if (secrets.itemCount > 0 && !isSecretLoading) {
                FloatingActionButton(
                    shape = CircleShape,
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary,
                    onClick = dialogState::show,
                    content = {
                        Icon(
                            imageVector = ImageVector.vectorResource(R.drawable.ic_add),
                            contentDescription = stringResource(R.string.add_secret_button)
                        )
                    }
                )
            }
        },
        topBar = {
            TOTPTopBar(
                modifier = Modifier
                    .fillMaxWidth(),
                title = stringResource(R.string.app_name),
                themeSwitchState = themeSwitchState,
                onThemeSwitch = themeSwitchState::switchMode
            )
        },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { padding ->
        if (secrets.itemCount <= 0 && !isSecretLoading) {
            TOTPEmpty(
                modifier.padding(padding),
                scanQrOnClick = {},
                enterManuallyOnClick = dialogState::show
            )
        }
        else TOTPContent(
            modifier = modifier.padding(padding),
            secrets = secrets,
            remainingCountDown = remainingCountDown
        )
    }
}

@Composable
fun TOTPContent(
    modifier: Modifier = Modifier,
    secrets: LazyPagingItems<SecretDetails>,
    remainingCountDown: Long
) {
    LazyColumn(
        modifier = modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(
            count = secrets.itemCount,
            key = secrets.itemKey { it.id },
            contentType = secrets.itemContentType { "secret_details" }
        ) { index: Int ->
            val secretDetails = secrets[index]
            if (secretDetails != null) TOTPCard(
                secretDetails = secretDetails,
                remainingCountDown = remainingCountDown
            )
        }
    }
}

@Composable
fun TOTPCard(
    secretDetails: SecretDetails,
    remainingCountDown: Long
) {
    Card(
        shape = RoundedCornerShape(32.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.secondary
        )
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = secretDetails.secretLabel,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Text(
                    text = secretDetails.accountName,
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = secretDetails.totp,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            TimeIndicator(
                value = remainingCountDown.toInt(),
                maxValue = 30_000
            )
        }
    }
}

@Preview
@Composable
fun TOTPEmptyPreview() {
    TOTPHubTheme(darkTheme = true) {
        TOTPEmpty(Modifier.fillMaxSize(), {}, {})
    }
}

@Composable
fun TOTPEmpty(
    modifier: Modifier = Modifier,
    scanQrOnClick: () -> Unit,
    enterManuallyOnClick: () -> Unit
) {
    Column(
        modifier = modifier
            .background(MaterialTheme.colorScheme.background)
            .padding(60.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.lets_get_started),
            style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.SemiBold),
            color = MaterialTheme.colorScheme.onSecondary
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = stringResource(R.string.generate_2fa_codes),
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Light),
            color = MaterialTheme.colorScheme.onSecondary,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(26.dp))
        PrimaryButton(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(R.string.scan_qr_code),
            onClick = scanQrOnClick
        )
        Spacer(modifier = Modifier.height(8.dp))
        SecondaryButton(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(R.string.enter_manually),
            onClick = enterManuallyOnClick
        )
    }
}

@Composable
fun LoadingDialog(modifier: Modifier = Modifier) {
    CircularProgressIndicator()
}