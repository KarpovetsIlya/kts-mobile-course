package com.karpovec.kmpmobileapp.feature.onBoarding

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.LocalRippleConfiguration
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RippleConfiguration
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import kmpmobileapp.composeapp.generated.resources.Res
import kmpmobileapp.composeapp.generated.resources.compose_multiplatform
import org.jetbrains.compose.resources.painterResource
import coil3.compose.LocalPlatformContext
import coil3.request.CachePolicy
import coil3.request.ImageRequest
import coil3.request.crossfade
import io.github.aakira.napier.Napier


@Composable
fun WelcomeScreen(
    onClick: () -> Unit,
    onToggleTheme: () -> Unit
) {
    val cs = MaterialTheme.colorScheme
    val context = LocalPlatformContext.current
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = cs.background
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            Box(modifier = Modifier.weight(1f).fillMaxWidth())
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(context)

                        .data("https://avt-school.ru/wp-content/uploads/2023/05/%D0%B4%D1%83%D0%B1%D0%BB%D1%8F%D0%B6-1024x910.png")
                            .memoryCachePolicy(CachePolicy.DISABLED)
                            .diskCachePolicy(CachePolicy.DISABLED)
                            .crossfade(true)
                            .build(),
                    contentDescription = "Welcome",
                    placeholder = painterResource(Res.drawable.compose_multiplatform),
                    error = painterResource(Res.drawable.compose_multiplatform),
                    onLoading = { Napier.d("IMAGE: loading", tag = "Napier") },
                    onSuccess = { Napier.d("IMAGE: success", tag = "Napier") },
                    onError = { state ->
                        Napier.e("IMAGE: error", state.result.throwable, tag = "Napier")
                    }
                )
                Text(
                    text = "Welcome to Our App.",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(24.dp),
                    color = cs.onBackground
                )
                Text(
                    text = "Discover new features and enjoy\na smooth and simple experience.",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Normal,
                    color = cs.onBackground.copy(alpha = 0.8f)
                )
            }
            Box(modifier = Modifier.weight(1.5f).fillMaxWidth()) {
                CompositionLocalProvider(
                    LocalRippleConfiguration provides RippleConfiguration(
                        color = cs.onPrimary.copy(alpha = 0.25f)
                    )
                ) {
                    Button(
                        onClick = onClick,
                        modifier = Modifier
                            .align(Alignment.Center)
                            .fillMaxWidth(0.6f)
                            .height(60.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = cs.primary,
                            contentColor = cs.onPrimary
                        ),
                        shape = RoundedCornerShape(20.dp)
                    ) {
                        Text(
                            text = "Get started",
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}
