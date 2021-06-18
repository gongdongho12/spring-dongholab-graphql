package com.dongholab.graphql.repository.reactive

import com.dongholab.graphql.entity.User
import org.springframework.stereotype.Repository
import org.springframework.data.r2dbc.repository.R2dbcRepository

@Repository
interface UserRxRepository: R2dbcRepository<User, String> {
}