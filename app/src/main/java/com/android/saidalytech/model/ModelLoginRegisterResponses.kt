package com.android.saidalytech.model

data class ModelLoginRegisterResponses(
    val customerId: Int,
    val email: String,
    val fullName: String,
    val isAuthenticated: Boolean,
    val massage: Any,
    val refreshToken: String,
    val refreshTokenExpiration: String,
    val roales: List<String>,
    val token: String,
    val userId: String
)