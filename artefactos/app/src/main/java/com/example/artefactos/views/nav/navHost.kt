package com.example.artefactos.views.nav

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.artefactos.views.addFinger.AddFingerScreen
import com.example.artefactos.views.home.Home
import com.example.artefactos.views.removeFinger.RemoveFingerScreen
import com.example.artefactos.views.success.SuccessScreen


@Composable
fun navhost() {

    val navController = rememberNavController()

    Scaffold(
    ) { innerPadding ->

        NavHost(
            navController = navController,
            startDestination = Home,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            composable<login> {
                AddFingerScreen(
                    onAddFingerClick = navController::navigateToAddFinger
                )
            }
            composable<Home> {
                Home(
                    onAddFinger = navController::navigateToAddFinger,
                    onDeleteFinger = navController::navigateToRemoveFinger
                )
            }
            composable<AddFinger> {
                AddFingerScreen(
                    onAddFingerClick = navController::navigateToSuccess
                )
            }
            composable<RemoveFinger> {
                RemoveFingerScreen(
                    onRemoveFingerClick = navController::navigateToSuccess
                )
            }
            composable<Success> {
                SuccessScreen(
                    onClick = navController::navigateToHome
                )
            }
        }


    }


}

fun NavController.navigateToAddFinger() = navigate(AddFinger)
fun NavController.navigateToRemoveFinger() = navigate(RemoveFinger)
fun NavController.navigateToSuccess() = navigate(Success)
fun NavController.navigateToHome() = navigate(Home) {
    popUpTo(graph.findStartDestination().id) {
        inclusive = true
    }
    launchSingleTop = true
}