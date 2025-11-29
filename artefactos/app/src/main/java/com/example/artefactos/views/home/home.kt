package com.example.artefactos.views.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.artefactos.core.model.enums.TopLevelDestination
import com.example.artefactos.views.components.BaseScreen
import com.example.artefactos.views.components.H1Bold
import com.example.artefactos.views.components.H1WithoutBold
import com.example.artefactos.views.components.HomeCard
import com.example.artefactos.views.components.NormalLabel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Home(
    onAddFinger:()->Unit,
    onDeleteFinger:()->Unit
) {
    BaseScreen(
        topBar = {
        },
        content = {
            HomeView(
                onAddFinger = onAddFinger,
                onDeleteFinger = onDeleteFinger
            )
        }
    )
}

@Composable
fun HomeView(
    modifier: Modifier = Modifier,
    onAddFinger:()->Unit,
    onDeleteFinger:()->Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.primary)
            .padding(10.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        HomeTopAppBar()
        LazyVerticalGrid(
            modifier = Modifier.fillMaxSize(),
            columns = GridCells.Fixed(2),
            horizontalArrangement = Arrangement.spacedBy(
                2.dp,
                alignment = Alignment.CenterHorizontally
            ),
            contentPadding = PaddingValues(2.dp)
        ) {
            items(TopLevelDestination.entries) {
                if (it.shouldShowOption) {
                    HomeCard(destination = it, modifier = Modifier.weight(0.7f)){
                        when(it){
                            TopLevelDestination.REMOVE_FINGER -> onDeleteFinger()
                            TopLevelDestination.ADD_FINGER -> onAddFinger()
                            else -> {}
                        }
                    }
                }
            }
        }

    }
}

@Composable
fun HomeTopAppBar() {
    Column(modifier = Modifier) {
        H1Bold(label = "Bienvenido", color = MaterialTheme.colorScheme.onPrimary)
        H1WithoutBold(label = "Que vamos hacer hoy", color = MaterialTheme.colorScheme.onPrimary)
        NormalLabel(label = "Selecciona una opción", color = MaterialTheme.colorScheme.onPrimary)
    }
}
