package com.example.artefactos.views.nav

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController


@Composable
fun navhost(){

    val navController = rememberNavController()

    Scaffold(
    ) { innerPadding ->

        NavHost(navController = navController,
            startDestination = {},
            modifier = Modifier.fillMaxSize()
                .padding(innerPadding)
            ){
            composable<login>{

            }
            composable<main> {

            }

        }


    }



}