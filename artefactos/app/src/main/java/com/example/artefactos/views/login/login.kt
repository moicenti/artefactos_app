package com.example.artefactos.views.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.artefactos.views.nav.Home

@Composable
fun login(
    nav: NavController,
    vm: loginViewModel = viewModel()
          ){
    val user by vm.user.collectAsState()
    val password by vm.password.collectAsState()
    val error by vm.error.collectAsState()

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        OutlinedTextField(
            value = user,
            onValueChange = vm::onUserChange,
            label = { Text("Usuario") }
        )

        OutlinedTextField(
            value = password,
            onValueChange = vm::onPasswordChange,
            label = { Text("Contraseña") },
            visualTransformation = PasswordVisualTransformation()
        )

        Button(
            onClick = {
                if (vm.login()) {
                    nav.navigate(Home)
                }
            },
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Text("Iniciar sesión")
        }

        if (error.isNotEmpty()) {
            Text(error, color = MaterialTheme.colorScheme.error)
        }
    }


}