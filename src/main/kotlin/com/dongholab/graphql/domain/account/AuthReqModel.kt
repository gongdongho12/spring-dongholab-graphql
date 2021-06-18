package com.dongholab.graphql.domain.account

data class AuthReqModel(
    val id: String,
    val name: String?,
    val password: String,
    val roleType: String?
)