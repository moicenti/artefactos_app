package com.example.artefactos.views.removeFinger

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.artefactos.artefactosAPP
import com.example.artefactos.data.ApiResponse
import com.example.artefactos.data.DeleteUserRequest
import com.example.artefactos.data.MessageResponse
import com.example.artefactos.network.RetrofitClient
import com.example.artefactos.network.interfaces.UsersApi
import com.example.artefactos.network.models.User
import com.example.artefactos.network.models.UserR
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class adminViewModel(
    private val getUser: () -> String,
    private val getToken: () -> String
): ViewModel() {

    private val _users = MutableStateFlow<List<User>>(emptyList())
    val users: StateFlow<List<User>> = _users

    fun getUseractive(): String{
        return getUser()
    }

    fun loadUsers(onResult: (Result<List<User>>) -> Unit) {
        val call = getUsersApiService(getToken())

        call.getUsers().enqueue(object : Callback<ApiResponse<List<User>>> {
            override fun onResponse(call: Call<ApiResponse<List<User>>>, response: Response<ApiResponse<List<User>>>) {
                if (response.isSuccessful) {
                    val users = response.body()?.data ?: emptyList()
                    _users.value = users
                    onResult(Result.success(users))
                } else {
                    onResult(Result.failure(Exception("Error al cargar usuarios: ${response.message()}")))
                }
            }

            override fun onFailure(call: Call<ApiResponse<List<User>>>, t: Throwable) {
                onResult(Result.failure(t))
            }
        })
    }

    fun deleteUser(userId: String, onResult: (Result<Unit>) -> Unit) {
        val call = getUsersApiService(getToken()).deleteUser(userId, DeleteUserRequest())
        call.enqueue(object : Callback<ApiResponse<MessageResponse>> {
            override fun onResponse(call: Call<ApiResponse<MessageResponse>>, response: Response<ApiResponse<MessageResponse>>) {
                if (response.isSuccessful) {
                    onResult(Result.success(Unit))
                } else {
                    onResult(Result.failure(Exception("Error al eliminar usuario: ${response.message()}")))
                }
            }

            override fun onFailure(call: Call<ApiResponse<MessageResponse>>, t: Throwable) {
                onResult(Result.failure(t))
            }
        })
    }

    // Agrega esta función en tu adminViewModel
    fun registerUser(userName: String, password: String, onResult: (Result<Unit>) -> Unit) {
        val userR = UserR().apply {
            name = userName
            this.password = password
        }

        val call = getUsersApiService(getToken()).createUser(userR)
        call.enqueue(object : Callback<ApiResponse<User>> {
            override fun onResponse(call: Call<ApiResponse<User>>, response: Response<ApiResponse<User>>) {
                if (response.isSuccessful) {
                    onResult(Result.success(Unit))
                } else {
                    onResult(Result.failure(Exception("Error al registrar usuario: ${response.message()}")))
                }
            }

            override fun onFailure(call: Call<ApiResponse<User>>, t: Throwable) {
                onResult(Result.failure(t))
            }
        })
    }


    private fun getUsersApiService(token : String): UsersApi {
        val retrofit = RetrofitClient.getClient(token)
        return retrofit.create(UsersApi::class.java)
    }

    companion object {
        val factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val app = this[APPLICATION_KEY] as? artefactosAPP
                    ?: throw IllegalStateException("App is not artefactos")
                adminViewModel(
                    getUser = app::obtainUser,
                    getToken = app::obtainToken
                )
            }
        }
    }
}


