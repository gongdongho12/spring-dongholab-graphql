package com.dongholab.graphql.controller

import com.dongholab.graphql.domain.account.AuthReqModel
import com.dongholab.graphql.domain.account.JWTToken
import com.dongholab.graphql.domain.account.RoleType
import com.dongholab.graphql.service.DongholabUserDetailService
import com.dongholab.graphql.service.JWTSigner
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
    lateinit var dongholabUserDetailService: DongholabUserDetailService

    @PutMapping("/signup")
    fun signUp(@RequestBody authReqModel: AuthReqModel): Mono<ResponseEntity<Void>> {
//        users[authReqModel.id] = authReqModel
        dongholabUserDetailService.addUser(authReqModel)
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
    fun login(@RequestBody authReqModel: AuthReqModel): Mono<ResponseEntity<JWTToken>> {
        return dongholabUserDetailService.checkAccount(authReqModel).map {
            ResponseEntity.ok(it)
        }.switchIfEmpty(Mono.just(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()))
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
    fun getMyself(principal: Principal): Mono<ResponseEntity<AuthReqModel>> {
        println("principal $principal")
        return Mono.justOrEmpty(dongholabUserDetailService.getUserById(principal.name))
            .map {
                ResponseEntity.ok(it)
            }
            .switchIfEmpty(Mono.just(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()))
    }
}