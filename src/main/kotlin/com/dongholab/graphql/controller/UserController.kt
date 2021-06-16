package com.dongholab.graphql.controller

import com.dongholab.graphql.service.JWTSigner
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseCookie
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Mono
import java.security.Principal

data class UserData(val email: String)

data class UserCredentials(val email: String, val password: String)

@RestController
@RequestMapping("/user")
class UserController {
    @Autowired
    lateinit var jwtSigner: JWTSigner

    private val users: MutableMap<String, UserCredentials> = mutableMapOf(
        "email@example.com" to UserCredentials("email@example.com", "pw")
    )

    @PutMapping("/signup")
    fun signUp(@RequestBody user: UserCredentials): Mono<ResponseEntity<Void>> {
        users[user.email] = user

        return Mono.just(ResponseEntity.noContent().build())
    }

//    @PostMapping("/login")
//    fun login(@RequestBody user: UserCredentials): Mono<ResponseEntity<Void>> {
//        return Mono.justOrEmpty(users[user.email])
//            .filter { it.password == user.password }
//            .map { ResponseEntity.noContent().build<Void>() }
//            .switchIfEmpty(Mono.just(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()))
//    }

    @PostMapping("/login")
    fun login(@RequestBody user: UserCredentials): Mono<ResponseEntity<Void>> {
        return Mono.justOrEmpty(users[user.email])
            .filter { it.password == user.password }
            .map {
                val jwt = jwtSigner.createJwt(it.email)
                val authCookie = ResponseCookie.fromClientResponse("X-Auth", jwt)
                    .maxAge(3600)
                    .httpOnly(true)
                    .path("/")
                    .secure(false) // should be true in production
                    .build()

                ResponseEntity.noContent()
                    .header("Set-Cookie", authCookie.toString())
                    .build<Void>()
            }
            .switchIfEmpty(Mono.just(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()))
    }

//    @GetMapping
//    fun getMyself(): Mono<ResponseEntity<User>> {
//        val emailAddress = "email@example.com" // ultimately this will be obtained from the JWT
//
//        return Mono.justOrEmpty(users[emailAddress])
//            .map { ResponseEntity.ok(User(it.email)) }
//            .switchIfEmpty(Mono.just(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()))
//    }

    @GetMapping
    fun getMyself(principal: Principal): Mono<ResponseEntity<UserData>> {
        return Mono.justOrEmpty(users[principal.name])
            .map { ResponseEntity.ok(UserData(it.email)) }
            .switchIfEmpty(Mono.just(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()))
    }
}