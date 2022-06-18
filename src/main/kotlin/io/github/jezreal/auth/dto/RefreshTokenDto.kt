package io.github.jezreal.auth.dto

import com.google.gson.annotations.SerializedName

data class RefreshTokenDto(
    @SerializedName("refresh_token")
    val refreshToken: String
)
