package com.dongholab.graphql.service

import com.dongholab.graphql.domain.account.AuthLoginModel
import com.dongholab.graphql.domain.account.AuthReqModel
import com.dongholab.graphql.entity.User
import com.dongholab.graphql.repository.jpa.UserRepository
import com.dongholab.graphql.repository.reactive.UserRxRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class UserService {
    @Autowired
    lateinit var userRepository: UserRepository

    @Autowired
    lateinit var userRxRepository: UserRxRepository

    @Autowired
    lateinit var passwordEncoder: PasswordEncoder

    fun getUser(id: String): Mono<User> {
        return userRxRepository.findById(id)
    }

    fun getUser(authLoginModel: AuthLoginModel): Mono<User> {
        return userRxRepository.findById(authLoginModel.id)
    }

    fun signUp(authReqModel: AuthReqModel): Mono<User> {
        return Mono.just(userRepository.save(User(
            authReqModel.apply {
                // added after password encoded
                password = passwordEncoder.encode(password)
            }
        )))
    }
}