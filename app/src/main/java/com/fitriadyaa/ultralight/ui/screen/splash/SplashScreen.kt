package com.fitriadyaa.ultralight.ui.screen.splash

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.fitriadyaa.ultralight.R

@Composable
fun SplashScreen(
    modifier: Modifier = Modifier,
    navigateToHome: () -> Unit
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .fillMaxSize()
            .background(color = Color.White)
    ) {
        val composition by rememberLottieComposition(spec = LottieCompositionSpec.RawRes(R.raw.logo))
        val logoAnimationState = animateLottieCompositionAsState(composition = composition)
        LottieAnimation(
            composition = composition,
            modifier = Modifier
                .size(172.dp)
        )
        if (logoAnimationState.isAtEnd and logoAnimationState.isPlaying) {
            navigateToHome()
        }
    }
}
