package com.example.artefactos.views.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun BaseScreen(topBar: @Composable () -> Unit = {}, content: @Composable () -> Unit = {}) {
    Scaffold(
        containerColor = Color.Transparent,
        topBar = topBar,
        bottomBar = {},
        floatingActionButton = {},
        content = { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .consumeWindowInsets(paddingValues)
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                content()
            }

        })
}