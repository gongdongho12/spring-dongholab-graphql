package com.dongholab.graphql.controller

import com.dongholab.graphql.domain.account.AuthLoginModel
import com.dongholab.graphql.domain.account.AuthReqModel
import com.dongholab.graphql.domain.account.JWTToken
import com.dongholab.graphql.entity.User
import com.dongholab.graphql.security.JwtSigner
import com.dongholab.graphql.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Mono
import java.security.Principal

@RestController
@RequestMapping("/user")
class UserController {
    @Autowired
    lateinit var jwtSigner: JwtSigner

    @Autowired
    lateinit var userService: UserService

    @PutMapping("/signup")
    fun signUp(@RequestBody authReqModel: AuthReqModel): ResponseEntity<Mono<User>> {
        return ResponseEntity.ok(userService.signUp(authReqModel))
    }

    @PostMapping("/login")
    fun login(@RequestBody authLoginModel: AuthLoginModel): Mono<ResponseEntity<JWTToken>?> {
        println("authLoginModel ${authLoginModel}")
        return jwtSigner.createJwt(authLoginModel).mapNotNull {
            it?.let {
                ResponseEntity(
                    JWTToken(it),
                    HttpStatus.OK
                )
            }
        }?.switchIfEmpty(Mono.just(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build())).log()
    }

    @GetMapping
    fun getMyself(principal: Principal): Mono<ResponseEntity<User>> {
        println("principal $principal")
        return userService.getUser(principal.name)
            .mapNotNull { ResponseEntity.ok(it) }
            .switchIfEmpty(Mono.just(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()))
    }
}