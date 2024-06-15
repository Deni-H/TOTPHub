package com.denihilhamsyah.totphub.code.presentation

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun CodeScreen(
    modifier: Modifier = Modifier,
    viewModel: CodeViewModel = hiltViewModel()
) {
    val totpSecret by viewModel.totpSecret.collectAsState()
    val totpCode by viewModel.totpCode.collectAsState()
    val remainingTimeStep by viewModel.remainingTimeStep.collectAsState()

    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = totpCode ?: "-")
        Text(text = remainingTimeStep.toString())
        TextField(value = totpSecret ?: "", onValueChange = { viewModel.onTOTPSecretChanged(it) })
    }
}