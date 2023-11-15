package com.fitriadyaa.ultralight.ui.component

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContract
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.fitriadyaa.ultralight.ui.theme.UltralightTheme

@Composable
fun BuyButton(
    text: String,
    url: String,
    navController: NavController,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,

    ) {
    var success by remember { mutableStateOf(false) }

    val openUrl = rememberLauncherForActivityResult(OpenUrlContract()) { result ->
        success = result
    }

    if (success) {
        DisposableEffect(Unit) {
            navController.navigate("success_screen")
            onDispose {
                success = false
            }
        }
    }

    Button(
        onClick = {
            openUrl.launch(url)
        },
        enabled = enabled,
        modifier = modifier
            .fillMaxWidth()
            .height(52.dp)
    ) {
        Text(
            text = text,
            modifier = Modifier.align(Alignment.CenterVertically)
        )
    }
}


class OpenUrlContract : ActivityResultContract<String, Boolean>() {
    override fun createIntent(context: Context, input: String): Intent {
        return Intent(Intent.ACTION_VIEW).setData(Uri.parse(input))
    }

    override fun parseResult(resultCode: Int, intent: Intent?): Boolean {
        return resultCode == android.app.Activity.RESULT_OK
    }
}

@Composable
@Preview(showBackground = true)
fun BuyButtonPreview() {
    val navController = rememberNavController()
    CompositionLocalProvider(LocalContext provides LocalContext.current) {
        UltralightTheme {
            BuyButton(
                text = "Beli Sekarang",
                url = "https://www.fitriadyaa.my.id",
                navController = navController
            )
        }
    }
}


