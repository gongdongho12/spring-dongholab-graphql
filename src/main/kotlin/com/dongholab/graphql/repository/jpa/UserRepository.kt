package com.dongholab.graphql.repository.jpa

import org.springframework.stereotype.Repository
import com.dongholab.graphql.entity.User
import org.springframework.data.jpa.repository.JpaRepository

@Repository
interface UserRepository: JpaRepository<User, String> {
}