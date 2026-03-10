package com.karpovec.kmpmobileapp.feature.login.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.karpovec.kmpmobileapp.Res
import com.karpovec.kmpmobileapp.core.theme.LocalDimens
import com.karpovec.kmpmobileapp.login_button
import org.jetbrains.compose.resources.stringResource

@Composable
fun LoginScreen(
    onBackClick: () -> Unit,
    onLoginSuccess: () -> Unit,
    onLoginClick: () -> Unit
) {
    val d = LocalDimens.current

    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .navigationBarsPadding()
                .padding(horizontal = d.screenPadding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(Modifier.weight(1f))

            AsyncImage(
                model = "https://cdn-icons-png.flaticon.com/512/25/25231.png",
                contentDescription = "GitHub logo",
                modifier = Modifier.size(120.dp)
            )

            Spacer(Modifier.weight(1f))

            Button(
                onClick = onLoginClick,
                modifier = Modifier
                    .fillMaxWidth(d.contentWidth)
                    .height(d.buttonHeight + 12.dp),
                shape = RoundedCornerShape(d.buttonCorner),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                )
            ) {
                Text(
                    text = stringResource(Res.string.login_button),
                    style = MaterialTheme.typography.titleMedium
                )
            }

            Spacer(Modifier.height(d.space3xl))
        }
    }
}