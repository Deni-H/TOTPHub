package com.denihilhamsyah.totphub.totp.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
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
import com.denihilhamsyah.totphub.R
import com.denihilhamsyah.totphub.totp.domain.model.SecretDetails
import com.denihilhamsyah.totphub.totp.presentation.component.ObserveAsEvents
import com.denihilhamsyah.totphub.totp.presentation.component.PrimaryButton
import com.denihilhamsyah.totphub.totp.presentation.component.SecondaryButton
import com.denihilhamsyah.totphub.totp.presentation.component.TOTPTopBar
import com.denihilhamsyah.totphub.totp.presentation.component.ThemeSwitchState
import com.denihilhamsyah.totphub.ui.theme.TOTPHubTheme
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TOTPScreen(
    modifier: Modifier = Modifier,
    viewModel: TOTPViewModel = hiltViewModel(),
    themeSwitchState: ThemeSwitchState
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    val secrets by viewModel.secrets.collectAsStateWithLifecycle()
    val state by viewModel.totpState.collectAsStateWithLifecycle()

    val secretFieldState by viewModel.secretFieldState.collectAsStateWithLifecycle()
    val secretLabelFieldState by viewModel.secretLabelFieldState.collectAsStateWithLifecycle()
    val accountNameFieldState by viewModel.accountNameFieldState.collectAsStateWithLifecycle()

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

    Scaffold(
        modifier = modifier,
        floatingActionButton = {
            if (secrets.isNotEmpty()) {
                FloatingActionButton(onClick = { /*TODO*/ }) {
                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.ic_add),
                        contentDescription = stringResource(R.string.add_secret_button)
                    )
                }
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
        if (secrets.isEmpty()) {
            TOTPEmpty(
                modifier.padding(padding),
                scanQrOnClick = {},
                enterManuallyOnClick = {}
            )
        }
        else TOTPContent(
            modifier = modifier.padding(padding),
            secrets = secrets
        )
    }
}

@Composable
fun TOTPContent(
    modifier: Modifier = Modifier,
    secrets: List<SecretDetails>,
) {

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
fun TOTPCard(
    modifier: Modifier = Modifier,
    secretDetails: SecretDetails
) {

}

@Composable
fun LoadingDialog(modifier: Modifier = Modifier) {
    CircularProgressIndicator()
}