package com.jeongseunggyu.devjeongseungyusns.network.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AuthResponse (
    @SerialName("access_token")
    val accessToken: String? = null,

    @SerialName("token_type")
    val tokenType: String? = null,

    @SerialName("expires_in")
    val expiresIn: Long? = null,

    @SerialName("refresh_token")
    val refreshToken: String? = null,

    val user: User? = null
)

@Serializable
data class User (
    val id: String? = null,
    val aud: String? = null,
    val role: String? = null,
    val email: String? = null,

    @SerialName("email_confirmed_at")
    val emailConfirmedAt: String? = null,

    val phone: String? = null,

    @SerialName("confirmed_at")
    val confirmedAt: String? = null,

    @SerialName("last_sign_in_at")
    val lastSignInAt: String? = null,

    @SerialName("app_metadata")
    val appMetadata: AppMetadata? = null,

    @SerialName("user_metadata")
    val userMetadata: UserMetadata? = null,

    val identities: List<Identity>? = null,

    @SerialName("created_at")
    val createdAt: String? = null,

    @SerialName("updated_at")
    val updatedAt: String? = null
)

@Serializable
data class AppMetadata (
    val provider: String? = null,
    val providers: List<String>? = null
)

@Serializable
data class Identity (
    val id: String? = null,

    @SerialName("user_id")
    val userID: String? = null,

    @SerialName("identity_data")
    val identityData: IdentityData? = null,

    val provider: String? = null,

    @SerialName("last_sign_in_at")
    val lastSignInAt: String? = null,

    @SerialName("created_at")
    val createdAt: String? = null,

    @SerialName("updated_at")
    val updatedAt: String? = null
)

@Serializable
data class IdentityData (
    val email: String? = null,
    val sub: String? = null
)

@Serializable
class UserMetadata()
