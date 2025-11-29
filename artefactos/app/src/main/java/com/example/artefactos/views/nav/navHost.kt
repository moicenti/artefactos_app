import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.artefactos.views.login.login
import com.example.artefactos.views.login.loginViewModel
import com.example.artefactos.views.nav.LoginRoute
import com.example.artefactos.views.nav.Main

@Composable
fun AppNavHost() {
    val navController = rememberNavController()

    Scaffold { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = LoginRoute,
            modifier = Modifier.fillMaxSize().padding(innerPadding)
        ) {
            composable<LoginRoute> {
                val vm: loginViewModel = viewModel()
                login(nav = navController, vm = vm)
            }
            composable<Main> {
            }
        }
    }
}
