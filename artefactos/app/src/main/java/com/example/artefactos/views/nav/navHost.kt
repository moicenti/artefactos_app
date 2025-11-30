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
import com.example.artefactos.views.addFinger.AddFingerScreenViewModel
import com.example.artefactos.views.home.Home
import com.example.artefactos.views.login.login
import com.example.artefactos.views.login.loginViewModel
import com.example.artefactos.views.removeFinger.Admin
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
                val loginVM: loginViewModel = viewModel(factory = loginViewModel.factory)
                login(nav = navController, vm = loginVM)
            }
            composable<Main> {
                Home(
                    onAddFinger = navController::navigateToAddFinger,
                    onDeleteFinger = navController::navigateToRemoveFinger
                )
            }
            composable<AddFinger> {
                val vm: AddFingerScreenViewModel = viewModel(factory = AddFingerScreenViewModel.factory)  ;
                AddFingerScreen(
                    navController,
                    vm
                )
            }
            composable<admin> {
                Admin(
                    navController
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
fun NavController.navigateToRemoveFinger() = navigate(admin)
fun NavController.navigateToSuccess() = navigate(Success)
fun NavController.navigateToHome() = navigate(Main) {
    popUpTo(graph.findStartDestination().id) {
        inclusive = true
    }
    launchSingleTop = true
}