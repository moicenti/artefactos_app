package com.example.artefactos.views.addFinger

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.artefactos.artefactosAPP
import com.example.artefactos.data.ApiResponse
import com.example.artefactos.data.MessageResponse
import com.example.artefactos.data.EnrollRequest
import com.example.artefactos.network.RetrofitClient
import com.example.artefactos.network.interfaces.CommandsApi
import com.example.artefactos.views.login.loginViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.awaitResponse

class AddFingerScreenViewModel(
    private val getTokens: () -> String

) : ViewModel() {

    private val retrofit = RetrofitClient.getClient(getTokens())
    private val commandApi = retrofit.create(CommandsApi::class.java)

    private val _error = MutableStateFlow("")
    val error: StateFlow<String> = _error.asStateFlow()

    private val _success = MutableStateFlow(false)
    val success: StateFlow<Boolean> = _success.asStateFlow()

    fun enrollFingerprint(onSuccessNavigate: () -> Unit) {
        viewModelScope.launch {
            try {
                val response = commandApi.enrollFingerprint(
                    request = EnrollRequest()
                ).awaitResponse()

                if (response.isSuccessful) {
                    val body: ApiResponse<MessageResponse>? = response.body()
                    if (body?.success == true) {
                        _error.value = ""
                        _success.value = true
                        onSuccessNavigate()
                    } else {
                        _error.value = body?.data?.message ?: "Error desconocido"
                        _success.value = false
                    }
                } else {
                    _error.value = "Error: ${response.code()}"
                    _success.value = false
                }
            } catch (e: Exception) {
                _error.value = "Error de red: ${e.message}"
                _success.value = false
            }
        }
    }

    companion object{
        val factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val app = this[APPLICATION_KEY] as? artefactosAPP
                    ?: throw IllegalStateException("App is not artefacctos")
                AddFingerScreenViewModel(
                 getTokens = app::obtainToken
                )
            }
        }

    }
}



