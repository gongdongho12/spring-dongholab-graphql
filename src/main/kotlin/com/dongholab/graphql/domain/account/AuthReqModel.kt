package com.dongholab.graphql.domain.account

data class AuthReqModel(
    override val id: String,
    override val password: String,
    val name: String?,
    val roleType: String?
): AuthLoginModel(id, password)