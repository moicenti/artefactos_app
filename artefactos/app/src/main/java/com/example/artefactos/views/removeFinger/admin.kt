package com.example.artefactos.views.removeFinger

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.artefactos.network.models.User
import com.example.artefactos.views.components.H1WithoutBold
import com.example.artefactos.views.nav.LoginRoute

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Admin(
    nav: NavController
) {
    val adminViewModel: adminViewModel = viewModel(factory = adminViewModel.factory)
    val currentUser = adminViewModel.getUseractive()

    var users by remember { mutableStateOf<List<User>>(emptyList()) }
    var isLoading by remember { mutableStateOf(false) }
    var error by remember { mutableStateOf<String?>(null) }

    var showRegisterForm by remember { mutableStateOf(false) }
    var newUserName by remember { mutableStateOf("") }
    var newUserPassword by remember { mutableStateOf("") }
    var isRegistering by remember { mutableStateOf(false) }
    var registerError by remember { mutableStateOf<String?>(null) }
    var registerSuccess by remember { mutableStateOf(false) }

    LaunchedEffect(key1 = Unit) {
        if (currentUser == "jorgon") {
            isLoading = true
            adminViewModel.loadUsers { result ->
                isLoading = false
                result.onSuccess { userList ->
                    users = userList
                }.onFailure { exception ->
                    error = exception.message
                }
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary
                ),
                title = {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        H1WithoutBold(
                            label = "Panel de Administración",
                            color = MaterialTheme.colorScheme.onPrimary,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            when {
                currentUser != "jorgon" -> {
                    // Mostrar mensaje de acceso denegado
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = "Acceso Restringido",
                                style = MaterialTheme.typography.headlineMedium,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.error
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            Text(
                                text = "Solo los administradores pueden acceder a esta sección",
                                style = MaterialTheme.typography.bodyLarge,
                                textAlign = TextAlign.Center,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }
                }
                isLoading -> {
                    // Mostrar loading
                    Box(
                        modifier = Modifier
                            .fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
                error != null -> {
                    // Mostrar error
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "Error",
                                style = MaterialTheme.typography.headlineMedium,
                                color = MaterialTheme.colorScheme.error
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = error ?: "Error desconocido",
                                textAlign = TextAlign.Center
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            Button(onClick = {
                                error = null
                                isLoading = true
                                adminViewModel.loadUsers { result ->
                                    isLoading = false
                                    result.onSuccess { userList ->
                                        users = userList
                                    }.onFailure { exception ->
                                        error = exception.message
                                    }
                                }
                            }) {
                                Text("Reintentar")
                            }
                        }
                    }
                }
                else -> {
                    // Mostrar lista de usuarios
                    Column(modifier = Modifier.fillMaxSize()) {
                        Text(
                            text = "Lista de Usuarios",
                            style = MaterialTheme.typography.headlineSmall,
                            modifier = Modifier.padding(bottom = 16.dp)
                        )

                        if (users.isEmpty()) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .weight(1f),
                                contentAlignment = Alignment.Center
                            ) {
                                Text("No hay usuarios registrados")
                            }
                        } else {
                            LazyColumn(
                                modifier = Modifier.weight(1f)
                            ) {
                                items(users) { user ->
                                    UserItem(
                                        user = user,
                                        onDeleteUser = { userId ->
                                            adminViewModel.deleteUser(userId) { result ->
                                                result.onSuccess {
                                                    // Recargar la lista después de eliminar
                                                    adminViewModel.loadUsers { loadResult ->
                                                        loadResult.onSuccess { userList ->
                                                            users = userList
                                                        }.onFailure { exception ->
                                                            error = exception.message
                                                        }
                                                    }
                                                }.onFailure { exception ->
                                                    error = exception.message
                                                }
                                            }
                                        }
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))
                        Divider()
                        Spacer(modifier = Modifier.height(16.dp))

                        // SECCIÓN PARA REGISTRAR NUEVO USUARIO
                        Column(
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            if (!showRegisterForm) {
                                Button(
                                    onClick = { showRegisterForm = true },
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Text("Registrar Nuevo Usuario")
                                }
                            } else {
                                Card(
                                    modifier = Modifier.fillMaxWidth(),
                                    elevation = androidx.compose.material3.CardDefaults.cardElevation(defaultElevation = 4.dp)
                                ) {
                                    Column(
                                        modifier = Modifier.padding(16.dp)
                                    ) {
                                        Text(
                                            text = "Registrar Nuevo Usuario",
                                            style = MaterialTheme.typography.headlineSmall,
                                            modifier = Modifier.padding(bottom = 16.dp)
                                        )

                                        if (registerSuccess) {
                                            Text(
                                                text = "¡Usuario registrado exitosamente!",
                                                color = MaterialTheme.colorScheme.primary,
                                                fontWeight = FontWeight.Bold,
                                                modifier = Modifier.padding(bottom = 16.dp)
                                            )
                                        }

                                        if (registerError != null) {
                                            Text(
                                                text = registerError ?: "Error desconocido",
                                                color = MaterialTheme.colorScheme.error,
                                                modifier = Modifier.padding(bottom = 16.dp)
                                            )
                                        }

                                        OutlinedTextField(
                                            value = newUserName,
                                            onValueChange = { newUserName = it },
                                            label = { Text("Nombre de usuario") },
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(bottom = 8.dp),
                                            enabled = !isRegistering
                                        )

                                        OutlinedTextField(
                                            value = newUserPassword,
                                            onValueChange = { newUserPassword = it },
                                            label = { Text("Contraseña") },
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(bottom = 16.dp),
                                            enabled = !isRegistering
                                        )

                                        Row(
                                            modifier = Modifier.fillMaxWidth(),
                                            horizontalArrangement = Arrangement.SpaceBetween
                                        ) {
                                            Button(
                                                onClick = {
                                                    showRegisterForm = false
                                                    newUserName = ""
                                                    newUserPassword = ""
                                                    registerError = null
                                                    registerSuccess = false
                                                },
                                                enabled = !isRegistering
                                            ) {
                                                Text("Cancelar")
                                            }

                                            Button(
                                                onClick = {
                                                    if (newUserName.isBlank() || newUserPassword.isBlank()) {
                                                        registerError = "Por favor completa todos los campos"
                                                        return@Button
                                                    }

                                                    isRegistering = true
                                                    registerError = null
                                                    adminViewModel.registerUser(newUserName, newUserPassword) { result ->
                                                        isRegistering = false
                                                        result.onSuccess {
                                                            registerSuccess = true
                                                            newUserName = ""
                                                            newUserPassword = ""
                                                            // Recargar la lista de usuarios
                                                            adminViewModel.loadUsers { loadResult ->
                                                                loadResult.onSuccess { userList ->
                                                                    users = userList
                                                                }
                                                            }
                                                        }.onFailure { exception ->
                                                            registerError = exception.message ?: "Error al registrar usuario"
                                                        }
                                                    }
                                                },
                                                enabled = !isRegistering
                                            ) {
                                                if (isRegistering) {
                                                    CircularProgressIndicator(
                                                        modifier = Modifier.size(16.dp),
                                                        strokeWidth = 2.dp
                                                    )
                                                } else {
                                                    Text("Registrar")
                                                }
                                            }
                                        }
                                    }
                                }
                            }

                            Spacer(modifier = Modifier.height(16.dp))
                            Divider()
                            Spacer(modifier = Modifier.height(16.dp))

                            Button(
                                onClick = { nav.navigate(LoginRoute) },
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text("Iniciar Sesión con otro usuario")
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun UserItem(
    user: User,
    onDeleteUser: (String) -> Unit
) {
    var showConfirmDialog by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        elevation = androidx.compose.material3.CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = user.name ?: "Sin nombre",
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Medium
                )
                Text(
                    text = "ID: ${user.id ?: "N/A"}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Button(
                onClick = { showConfirmDialog = true },
                colors = androidx.compose.material3.ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.error
                )
            ) {
                Text("Eliminar")
            }
        }
    }

    if (showConfirmDialog) {
        androidx.compose.material3.AlertDialog(
            onDismissRequest = { showConfirmDialog = false },
            title = { Text("Confirmar eliminación") },
            text = { Text("¿Estás seguro de que quieres eliminar al usuario ${user.name}?") },
            confirmButton = {
                Button(
                    onClick = {
                        showConfirmDialog = false
                        user.id?.let { onDeleteUser(it) }
                    },
                    colors = androidx.compose.material3.ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.error
                    )
                ) {
                    Text("Eliminar")
                }
            },
            dismissButton = {
                Button(
                    onClick = { showConfirmDialog = false }
                ) {
                    Text("Cancelar")
                }
            }
        )
    }
}