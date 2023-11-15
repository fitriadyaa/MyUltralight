package com.fitriadyaa.ultralight

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.fitriadyaa.ultralight.ui.navigation.NavigationItem
import com.fitriadyaa.ultralight.ui.navigation.ScreenPage
import com.fitriadyaa.ultralight.ui.screen.detail.DetailScreen
import com.fitriadyaa.ultralight.ui.screen.home.HomeScreen
import com.fitriadyaa.ultralight.ui.screen.profile.ProfileScreen
import com.fitriadyaa.ultralight.ui.theme.UltralightTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UltralightApp(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold(
        bottomBar = {
            if (currentRoute != ScreenPage.DetailProduct.route) {
                BottomBar(navController)
            }
        },
        modifier = modifier
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = ScreenPage.Home.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(ScreenPage.Home.route) {
                HomeScreen(
                    navigateToDetail = { productId ->
                        navController.navigate(ScreenPage.DetailProduct.createRoute(productId))
                    }
                )
            }
            composable(ScreenPage.Profile.route) {
                ProfileScreen()
            }
            composable(
                route = ScreenPage.DetailProduct.route,
                arguments = listOf(navArgument("productId") { type = NavType.LongType }),
            ) {
                val id = it.arguments?.getLong("productId") ?: -1L
                DetailScreen(
                    productId = id,
                    navigateBack = {
                        navController.navigateUp()
                    },
                )
            }
        }
    }
}

@Composable
private fun BottomBar(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavigationBar(
        modifier = modifier,
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        val navigationItems = listOf(
            NavigationItem(
                title = stringResource(R.string.menu_home),
                icon = Icons.Default.Home,
                screen = ScreenPage.Home
            ),
            NavigationItem(
                title = stringResource(R.string.menu_profile),
                icon = Icons.Default.AccountCircle,
                screen = ScreenPage.Profile
            ),
        )
        navigationItems.map { item ->
            NavigationBarItem(
                icon = {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = item.title
                    )
                },
                label = { Text(item.title) },
//                selected = false,
                selected = currentRoute == item.screen.route,
                onClick = {
                    navController.navigate(item.screen.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        restoreState = true
                        launchSingleTop = true
                    }
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun UltralightPreview() {
    UltralightTheme {
        UltralightApp()
    }
}
