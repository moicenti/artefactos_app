package com.example.artefactos.data

data class User(
    val id: String? = null,
    val name: String,
    val password: String? = null
)

data class Fingerprint(
    val id: Int
)

data class LoginRequest(
    val name: String,
    val password: String
)

data class LoginResponse(
    val token: Token
)

data class Token(
    val access_token: String
)

data class SendCommandRequest(
    val deviceId: String,
    val command: String,
    val payload: Any? = null
)

data class EnrollRequest(
    val deviceId: String
)

data class DeleteFingerprintRequest(
    val deviceId: String
)

data class EmergencyLockRequest(
    val deviceId: String
)

data class DeleteUserRequest(
    val deviceId: String,
    val apiKey: String
)

data class MessageResponse(
    val success: Boolean,
    val message: String
)

data class ApiResponse<T>(
    val success: Boolean,
    val data: T?
)
