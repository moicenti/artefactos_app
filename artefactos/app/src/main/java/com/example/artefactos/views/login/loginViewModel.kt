package com.example.artefactos.views.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.artefactos.artefactosAPP
import com.example.artefactos.data.ApiResponse
import com.example.artefactos.data.LoginRequest
import com.example.artefactos.data.LoginResponse
import com.example.artefactos.network.RetrofitClient
import com.example.artefactos.network.interfaces.UsersApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.awaitResponse

class loginViewModel(
    private val saveToken: (String) -> Unit,
    private val saveUser: (String) -> Unit
)
 : ViewModel() {

    private val _error = MutableStateFlow("")
    val error: StateFlow<String> = _error.asStateFlow()

    private val _user = MutableStateFlow("")
    val user: StateFlow<String> = _user.asStateFlow()

    private val _password = MutableStateFlow("")
    val password: StateFlow<String> = _password.asStateFlow()

    private val _token = MutableStateFlow<String?>(null)
    val token: StateFlow<String?> = _token.asStateFlow()

    private val retrofit = RetrofitClient.getClient()
    private val userApi = retrofit.create(UsersApi::class.java)

    fun onUserChange(value: String) {
        _user.value = value
    }

    fun onPasswordChange(value: String) {
        _password.value = value
    }

    fun login(onSuccess: () -> Unit) {
        viewModelScope.launch {
            try {
                val request = LoginRequest(name = _user.value, password = _password.value)
                val response = userApi.login(request).awaitResponse()

                if (response.isSuccessful) {
                    val body: ApiResponse<LoginResponse>? = response.body()
                    val tokenValue = body?.data?.token?.access_token

                    if (!tokenValue.isNullOrEmpty()) {
                        _token.value = tokenValue
                        saveToken(tokenValue)
                        saveUser(_user.value)

                        _error.value = ""
                        onSuccess()
                    } else {
                        _error.value = "Token inválido"
                    }

                } else {
                    _error.value = "Error: ${response.code()}"
                }
            } catch (e: Exception) {
                _error.value = "Error de red: ${e.message}"
            }
        }
    }

    companion object{
        val factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val app = this[APPLICATION_KEY] as? artefactosAPP
                    ?: throw IllegalStateException("App is not artefacctos")
                loginViewModel(
                    saveToken = app::changeToken,
                    saveUser = app::changeUser
                )
            }
        }

    }

}




