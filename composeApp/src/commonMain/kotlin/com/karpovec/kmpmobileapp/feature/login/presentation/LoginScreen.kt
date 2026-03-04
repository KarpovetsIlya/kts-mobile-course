package com.karpovec.kmpmobileapp.feature.login.presentation

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.lifecycle.viewmodel.compose.viewModel
import com.karpovec.kmpmobileapp.Res
import com.karpovec.kmpmobileapp.core.theme.LocalDimens
import com.karpovec.kmpmobileapp.email_placeholder
import com.karpovec.kmpmobileapp.enter_email
import com.karpovec.kmpmobileapp.enter_password
import com.karpovec.kmpmobileapp.login_button
import com.karpovec.kmpmobileapp.login_title
import com.karpovec.kmpmobileapp.password_placeholder
import kotlinx.coroutines.flow.collectLatest
import org.jetbrains.compose.resources.stringResource
import kotlin.Unit

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    onBackClick: () -> Unit,
    onToggleTheme: () -> Unit,
    onLoginSuccess: () -> Unit,
    viewModel: LoginViewModel = viewModel()
) {
    val uiState by viewModel.state.collectAsState()
    val d = LocalDimens.current

    LaunchedEffect(Unit) {
        viewModel.events.collectLatest { event ->
            when (event) {
                LoginUiEvent.LoginSuccessEvent -> onLoginSuccess()
            }
        }
    }

    val animatedBgColor by animateColorAsState(
        targetValue = MaterialTheme.colorScheme.background,
        label = "colorAnimation"
    )

    Scaffold(
        containerColor = animatedBgColor,
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = stringResource(Res.string.login_title),
                        style = MaterialTheme.typography.headlineLarge
                    )
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color.Transparent,
                    scrolledContainerColor = Color.Transparent,
                    titleContentColor = MaterialTheme.colorScheme.onSurface,
                    actionIconContentColor = MaterialTheme.colorScheme.onSurface,
                    navigationIconContentColor = MaterialTheme.colorScheme.onSurface
                ),
            )
        }
    ) { innerPadding ->

        val scrollState = rememberScrollState()
        val fieldWidth = Modifier.fillMaxWidth(d.contentWidth)

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .imePadding()
                .navigationBarsPadding()
                .padding(horizontal = d.screenPadding)
        ) {
            Column(
                modifier = Modifier
                    .align(Alignment.Center)
                    .fillMaxWidth()
                    .verticalScroll(scrollState),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Text(
                    text = stringResource(Res.string.enter_email),
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = fieldWidth,
                    textAlign = TextAlign.Start,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Spacer(Modifier.height(d.spaceS))

                OutlinedTextField(
                    value = uiState.username,
                    onValueChange = viewModel::onUsernameChanged,
                    modifier = fieldWidth,
                    placeholder = { Text(stringResource(Res.string.email_placeholder)) },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                    shape = RoundedCornerShape(d.textFieldCorner),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = MaterialTheme.colorScheme.surface,
                        unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                        focusedBorderColor = MaterialTheme.colorScheme.primary,
                        unfocusedBorderColor = MaterialTheme.colorScheme.outline,
                        focusedTextColor = MaterialTheme.colorScheme.onSurface,
                        unfocusedTextColor = MaterialTheme.colorScheme.onSurface
                    )
                )

                Spacer(Modifier.height(d.spaceL))

                Text(
                    text = stringResource(Res.string.enter_password),
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = fieldWidth,
                    textAlign = TextAlign.Start,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Spacer(Modifier.height(d.spaceS))

                var isPasswordVisible by rememberSaveable { mutableStateOf(false) }

                OutlinedTextField(
                    value = uiState.password,
                    onValueChange = viewModel::onPasswordChanged,
                    modifier = fieldWidth,
                    placeholder = { Text(stringResource(Res.string.password_placeholder)) },
                    singleLine = true,
                    visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    trailingIcon = {
                        IconButton(onClick = { isPasswordVisible = !isPasswordVisible }) {
                            Icon(
                                imageVector = if (isPasswordVisible) Icons.Filled.VisibilityOff else Icons.Filled.Visibility,
                                contentDescription = null
                            )
                        }
                    },
                    shape = RoundedCornerShape(d.textFieldCorner),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = MaterialTheme.colorScheme.surface,
                        unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                        focusedBorderColor = MaterialTheme.colorScheme.primary,
                        unfocusedBorderColor = MaterialTheme.colorScheme.outline
                    )
                )

                if (uiState.error != null) {
                    Spacer(Modifier.height(d.spaceS))
                    Text(
                        text = uiState.error!!,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.error,
                        modifier = fieldWidth
                    )
                }

                Spacer(Modifier.height(d.spaceXl))

                Button(
                    onClick = viewModel::onLoginClick,
                    enabled = uiState.isLoginButtonActive,
                    modifier = fieldWidth.height(d.buttonHeight),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.onPrimary
                    ),
                    shape = RoundedCornerShape(d.buttonCorner)
                ) {
                    Text(
                        text = stringResource(Res.string.login_button),
                        style = MaterialTheme.typography.titleMedium
                    )
                }

                Spacer(Modifier.height(d.spaceL))
            }
        }
    }
}