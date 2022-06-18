package io.github.jezreal.auth.service

import at.favre.lib.crypto.bcrypt.BCrypt
import io.github.jezreal.auth.RoleUtil
import io.github.jezreal.auth.dto.LoginDto
import io.github.jezreal.auth.model.TokenModel
import io.github.jezreal.auth.model.UserCredentialModel
import io.github.jezreal.auth.repository.AuthRepository
import io.github.jezreal.configuration.Configuration
import io.github.jezreal.exception.ResourceNotFoundException
import io.github.jezreal.exception.AuthenticationException
import io.github.jezreal.exception.AuthorizationException

object AuthService {
    private val authRepository = AuthRepository
    private val securityConfiguration = Configuration.getSecurityConfiguration()

    fun login(loginDto: LoginDto): TokenModel {
        val credential = authRepository.getUserCredentialByUsername(loginDto.username)
            ?: throw ResourceNotFoundException("Account not found")

        if (verifyPassword(credential, loginDto)) {
            val accessToken = securityConfiguration.makeAccessToken(credential.username, credential.role)
            val refreshToken = securityConfiguration.makeRefreshToken(credential.credentialId)

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

                return TokenModel(accessToken, refreshToken)
            }

            throw AuthorizationException("Your account is not allowed to access this resource")
        }

        throw AuthenticationException("Invalid credentials")
    }

    private fun verifyPassword(credentials: UserCredentialModel, loginDto: LoginDto): Boolean {
        val passwordFromDatabase = credentials.password
        val result = BCrypt.verifyer().verify(loginDto.password.toCharArray(), passwordFromDatabase)

        return result.verified
    }
}