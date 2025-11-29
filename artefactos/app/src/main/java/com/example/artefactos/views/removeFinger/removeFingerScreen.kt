package com.example.artefactos.views.removeFinger

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import com.example.artefactos.R
import com.example.artefactos.views.components.BaseActionScreen
import com.example.artefactos.views.components.BaseScreen
import com.example.artefactos.views.components.H1WithoutBold

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RemoveFingerScreen(
    onRemoveFingerClick: () -> Unit
) {
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
                            label = "Eliminar huella",
                            color = MaterialTheme.colorScheme.onPrimary,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            )
        },
        content = {
            BaseActionScreen(
                modifier = Modifier.background(MaterialTheme.colorScheme.primary),
                description = "Revisa las huellas registradas en tu dispositivo y elimina las que ya no necesites. Mantén tu acceso biométrico actualizado y seguro desde aquí.",
                animation = R.raw.delete,
                buttonDescription = "Eliminar huella",
                onActionClick = {
                    onRemoveFingerClick()
                }
            )
        }
    )
}