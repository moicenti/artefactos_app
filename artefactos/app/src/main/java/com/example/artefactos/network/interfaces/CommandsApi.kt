package com.example.artefactos.network.interfaces

import com.example.artefactos.data.ApiResponse
import com.example.artefactos.data.DeleteFingerprintRequest
import com.example.artefactos.data.EmergencyLockRequest
import com.example.artefactos.data.EnrollRequest
import com.example.artefactos.data.MessageResponse
import com.example.artefactos.data.SendCommandRequest
import retrofit2.Call
import retrofit2.http.*

interface CommandsApi {

    @POST("commands/send")
    fun sendCommand(
        @Header("x-api-key") apiKey: String = "JfQ5MtxfHFUPurxcOONOSDXBii5LzqcyKyJ1kiMYsqSXRPvZyzDoDmei",
        @Body request: SendCommandRequest
    ): Call<ApiResponse<MessageResponse>>

    @POST("commands/enroll")
    fun enrollFingerprint(
        @Header("x-api-key") apiKey: String = "JfQ5MtxfHFUPurxcOONOSDXBii5LzqcyKyJ1kiMYsqSXRPvZyzDoDmei",
        @Body request: EnrollRequest
    ): Call<ApiResponse<MessageResponse>>

    @DELETE("commands/fingerprint/{id}")
    fun deleteFingerprint(
        @Header("x-api-key") apiKey: String = "JfQ5MtxfHFUPurxcOONOSDXBii5LzqcyKyJ1kiMYsqSXRPvZyzDoDmei",
        @Path("id") id: String,
        @Body request: DeleteFingerprintRequest
    ): Call<ApiResponse<MessageResponse>>

    @POST("commands/emergency-lock")
    fun emergencyLock(
        @Header("x-api-key") apiKey: String = "JfQ5MtxfHFUPurxcOONOSDXBii5LzqcyKyJ1kiMYsqSXRPvZyzDoDmei",
        @Body request: EmergencyLockRequest
    ): Call<ApiResponse<MessageResponse>>
}
