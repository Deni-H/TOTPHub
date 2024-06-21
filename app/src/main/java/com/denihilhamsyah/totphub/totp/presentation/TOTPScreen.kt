package com.denihilhamsyah.totphub.totp.presentation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.denihilhamsyah.totphub.R
import com.denihilhamsyah.totphub.totp.domain.model.SecretDetails
import kotlinx.coroutines.launch

@Composable
fun TOTPScreen(
    modifier: Modifier = Modifier,
    viewModel: TOTPViewModel = hiltViewModel()
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
        floatingActionButton = { FloatingActionButton(onClick = { /*TODO*/ }) {
            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.ic_add),
                contentDescription = stringResource(R.string.add_secret_button)
            )
        }},
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { padding ->
        if (secrets.isEmpty()) TOTPEmpty(modifier.padding(padding))
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

@Composable
fun TOTPEmpty(modifier: Modifier = Modifier) {

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