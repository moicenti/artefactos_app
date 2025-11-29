package com.example.artefactos.views.nav

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.artefactos.views.addFinger.AddFingerScreen
import com.example.artefactos.views.home.Home
import com.example.artefactos.views.login.login
import com.example.artefactos.views.login.loginViewModel
import com.example.artefactos.views.removeFinger.RemoveFingerScreen
import com.example.artefactos.views.success.SuccessScreen

@Composable
fun AppNavHost() {
    val navController = rememberNavController()
    Scaffold { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = LoginRoute,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            composable<LoginRoute> {
                val vm: loginViewModel = viewModel()
                login(nav = navController, vm = vm)
            }
            composable<Main> {
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
fun NavController.navigateToHome() = navigate(Main) {
    popUpTo(graph.findStartDestination().id) {
        inclusive = true
    }
    launchSingleTop = true
}