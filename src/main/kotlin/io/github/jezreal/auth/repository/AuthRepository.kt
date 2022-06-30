package io.github.jezreal.auth.repository

import io.github.jezreal.auth.model.RefreshTokenModel
import io.github.jezreal.auth.model.StoreModel
import io.github.jezreal.auth.model.UserCredentialModel
import io.github.jezreal.tables.auth.AccountTypes
import io.github.jezreal.tables.auth.Credentials
import io.github.jezreal.tables.auth.RefreshTokens
import io.github.jezreal.tables.store.Stores
import org.jetbrains.exposed.sql.JoinType
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction

object AuthRepository {
    fun getUserCredentialByUsername(username: String): UserCredentialModel? {
        return transaction {
            Credentials.join(AccountTypes, JoinType.INNER, additionalConstraint = {
                Credentials.accountTypeId eq AccountTypes.accountTypeId
            })
                .select { Credentials.username eq username }.firstOrNull()?.let {
                    UserCredentialModel(
                        it[Credentials.username],
                        it[Credentials.password],
                        it[AccountTypes.accountType],
                        it[Credentials.credentialId]
                    )
                }
        }
    }

    fun getUserCredentialByCredentialId(credentialId: Long): UserCredentialModel? {
        return transaction {
            Credentials.join(AccountTypes, JoinType.INNER, additionalConstraint = {
                Credentials.accountTypeId eq AccountTypes.accountTypeId
            })
                .select { Credentials.credentialId eq credentialId }.firstOrNull()?.let {
                    UserCredentialModel(
                        it[Credentials.username],
                        it[Credentials.password],
                        it[AccountTypes.accountType],
                        it[Credentials.credentialId]
                    )
                }
        }
    }

    fun insertRefreshToken(refreshToken: String, credentialId: Long): Long {
        return transaction {
            RefreshTokens.insert {
                it[RefreshTokens.refreshToken] = refreshToken
                it[RefreshTokens.credentialId] = credentialId
            } get RefreshTokens.refreshTokenId
        }
    }

    fun getUserRefreshToken(refreshToken: String): RefreshTokenModel? {
        return transaction {
            Credentials.join(RefreshTokens, JoinType.INNER, additionalConstraint = {
                Credentials.credentialId eq RefreshTokens.credentialId
            })
                .select { RefreshTokens.refreshToken eq refreshToken }.firstOrNull()?.let {
                    RefreshTokenModel(
                        it[RefreshTokens.refreshTokenId],
                        it[RefreshTokens.refreshToken]
                    )
                }
        }
    }

    fun getUserRefreshTokens(credentialId: Long): List<RefreshTokenModel> {
        return transaction {
            RefreshTokens.join(Credentials, JoinType.INNER, additionalConstraint = {
                RefreshTokens.credentialId eq Credentials.credentialId
            }).select { Credentials.credentialId eq credentialId }.map {
                RefreshTokenModel(
                    it[RefreshTokens.refreshTokenId],
                    it[RefreshTokens.refreshToken]
                )
            }
        }
    }

    fun deleteRefreshTokens(tokenIds: List<Long>) {
        for (tokenId in tokenIds) {
            transaction {
                RefreshTokens.deleteWhere { RefreshTokens.refreshTokenId eq tokenId }
            }
        }
    }

    fun getStoreInfo(credentialId: Long): StoreModel? {
        return transaction {
            Stores.join(Credentials, JoinType.INNER, additionalConstraint = {
                Stores.credentialId eq Credentials.credentialId
            }).select { Credentials.credentialId eq credentialId }.firstOrNull()?.let {
                StoreModel(
                    it[Credentials.credentialId],
                    it[Credentials.username],
                    it[Credentials.password],
                    it[Stores.storeId],
                    it[Stores.storeName],
                    it[Stores.storeAddress]
                )
            }
        }
    }
}