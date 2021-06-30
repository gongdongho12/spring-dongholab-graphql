package com.dongholab.graphql.domain.account

abstract class AuthBase(
    open val id: String,
    open val password: String
)