package com.fitriadyaa.ultralight.ui.navigation

sealed class ScreenPage(val route: String) {
    object Home : ScreenPage("home")
    object Profile : ScreenPage("profile")
    object DetailProduct : ScreenPage("home/{productId}") {
        fun createRoute(productId: Long) = "home/$productId"
    }
}
