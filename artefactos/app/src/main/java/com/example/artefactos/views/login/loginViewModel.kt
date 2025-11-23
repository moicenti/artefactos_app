package com.example.artefactos.views.login

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class loginViewModel(
): ViewModel(){
    private val _error = MutableStateFlow("");
    val error: StateFlow<String> = _error.asStateFlow();
    private val _user = MutableStateFlow("");
    val user: StateFlow<String> = _user.asStateFlow();
    private val _password = MutableStateFlow("");
    val password: StateFlow<String> = _password.asStateFlow()



    fun onUserChange(value: String) {
        _user.value =  value ;
    }

    fun onPasswordChange(value: String) {
        _password.value  = value
    }

    fun login(): Boolean {
        return if (_user.value == "matoceti" && _password.value == "1234") {
            _error.value = "" ;
            true
        } else {
            _error.value = "Hubo un error al iniciar sesion";
            false
        }
    }

}