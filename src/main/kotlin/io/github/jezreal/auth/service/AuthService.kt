package io.github.jezreal.auth.service

import at.favre.lib.crypto.bcrypt.BCrypt
import io.github.jezreal.auth.RoleUtil
import io.github.jezreal.auth.dto.LoginDto
import io.github.jezreal.auth.dto.RefreshToken
import io.github.jezreal.auth.model.TokenModel
import io.github.jezreal.auth.model.UserCredentialModel
import io.github.jezreal.auth.repository.AuthRepository
import io.github.jezreal.configuration.Configuration
import io.github.jezreal.exception.AuthenticationException
import io.github.jezreal.exception.AuthorizationException
import io.github.jezreal.exception.ResourceNotFoundException

object AuthService {
    private val authRepository = AuthRepository
    private val securityConfiguration = Configuration.getSecurityConfiguration()

    fun login(loginDto: LoginDto): TokenModel {
        val credential = authRepository.getUserCredentialByUsername(loginDto.username)
            ?: throw ResourceNotFoundException("Account not found")

        if (verifyPassword(credential, loginDto)) {
            val accessToken = securityConfiguration.makeAccessToken(credential.username, credential.role)
            val refreshToken = securityConfiguration.makeRefreshToken(credential.credentialId)
            authRepository.insertRefreshToken(refreshToken, credential.credentialId)

            return TokenModel(accessToken, refreshToken)
        }

        throw AuthenticationException("Invalid credentials")
    }

    fun loginAsAdmin(loginDto: LoginDto): TokenModel {
        val credential = authRepository.getUserCredentialByUsername(loginDto.username)
            ?: throw ResourceNotFoundException("Account not found")

        if (verifyPassword(credential, loginDto)) {
            if (credential.role == RoleUtil.ADMIN) {
                val accessToken = securityConfiguration.makeAccessToken(credential.username, credential.role)
                val refreshToken = securityConfiguration.makeRefreshToken(credential.credentialId)
                authRepository.insertRefreshToken(refreshToken, credential.credentialId)

                return TokenModel(accessToken, refreshToken)
            }

            throw AuthorizationException("You are not allowed to access this resource")
        }

        throw AuthenticationException("Invalid credentials")
    }

    private fun verifyPassword(credentials: UserCredentialModel, loginDto: LoginDto): Boolean {
        val passwordFromDatabase = credentials.password
        val result = BCrypt.verifyer().verify(loginDto.password.toCharArray(), passwordFromDatabase)

        return result.verified
    }

    fun refreshAccessToken(refreshToken: RefreshToken): String {
        authRepository.getUserRefreshToken(refreshToken.content)
            ?: throw AuthenticationException("Invalid refresh token")

        val decodedJwt = securityConfiguration.validateRefreshToken(refreshToken.content)
            ?: throw AuthenticationException("Invalid refresh token")

        val credentialId = decodedJwt.getClaim("credentialId").asLong()

        val credential = authRepository.getUserCredentialByCredentialId(credentialId)
            ?: throw ResourceNotFoundException("Account does not exist")

        val newRefreshToken = securityConfiguration.makeRefreshToken(credential.credentialId)
        authRepository.insertRefreshToken(newRefreshToken, credential.credentialId)

        return securityConfiguration.makeAccessToken(credential.username, credential.role)
    }
}