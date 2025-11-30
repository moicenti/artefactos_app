package com.example.artefactos.views.addFinger

import androidx.compose.foundation.background
import androidx.compose.foundation.content.MediaType.Companion.Text
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.artefactos.R
import com.example.artefactos.views.components.BaseActionScreen
import com.example.artefactos.views.components.BaseScreen
import com.example.artefactos.views.components.H1WithoutBold
import com.example.artefactos.views.nav.Success

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddFingerScreen(
    nav: NavController,
    vm: AddFingerScreenViewModel = viewModel()
) {
    val error by vm.error.collectAsState()
    val success by vm.success.collectAsState()

    BaseScreen(
        topBar = {

            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary
                ),
                title = {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(MaterialTheme.colorScheme.primary),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        H1WithoutBold(
                            label = "Agregar huella",
                            color = MaterialTheme.colorScheme.onPrimary,
                            textAlign = TextAlign.Center
                        )
                    }


                })
        },


        content = {
            BaseActionScreen(
                modifier = Modifier.background(MaterialTheme.colorScheme.primary),
                description = "Agrega nuevas huellas a tu dispositivo y mantén tu acceso biométrico rápido y seguro.",
                animation = R.raw.securebox,
                buttonDescription = "Agregar huella",
                onActionClick = {
                    vm.enrollFingerprint {
                        nav.navigate(Success)
                    }
                }
            )

            if (error.isNotEmpty()) {
                Text(error, color = MaterialTheme.colorScheme.error)
            }
        }
    )
}


