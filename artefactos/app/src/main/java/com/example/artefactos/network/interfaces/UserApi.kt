package com.example.artefactos.network.interfaces

import com.example.artefactos.data.ApiResponse
import com.example.artefactos.data.DeleteUserRequest
import com.example.artefactos.data.Fingerprint
import com.example.artefactos.data.LoginRequest
import com.example.artefactos.data.LoginResponse
import com.example.artefactos.data.MessageResponse
import com.example.artefactos.network.models.User
import retrofit2.Call
import retrofit2.http.*

interface UsersApi {

    @POST("users")
    fun createUser(@Body user: User): Call<ApiResponse<User>>

    @GET("users")
    fun getUsers(): Call<ApiResponse<List<User>>>

    @GET("users/{id}")
    fun getUser(@Path("id") id: String): Call<ApiResponse<User>>

    @PUT("users/{id}")
    fun updateUser(@Path("id") id: String, @Body user: User): Call<ApiResponse<User>>

    @DELETE("users/{id}")
    fun deleteUser(@Path("id") id: String, @Body body: DeleteUserRequest): Call<ApiResponse<MessageResponse>>

    @POST("users/auth/login")
    fun login(@Body loginRequest: LoginRequest): Call<ApiResponse<LoginResponse>>

    @GET("users/fingerprints")
    fun getUserFingerprints(): Call<ApiResponse<List<Fingerprint>>>

    @GET("users/fingerprints/{id}")
    fun getFingerprintsByUserId(@Path("id") id: String): Call<ApiResponse<List<Fingerprint>>>
}
