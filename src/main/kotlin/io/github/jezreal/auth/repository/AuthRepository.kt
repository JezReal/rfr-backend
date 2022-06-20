package io.github.jezreal.auth.repository

import io.github.jezreal.auth.model.UserCredentialModel
import io.github.jezreal.tables.auth.AccountTypes
import io.github.jezreal.tables.auth.Credentials
import org.jetbrains.exposed.sql.JoinType
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
}