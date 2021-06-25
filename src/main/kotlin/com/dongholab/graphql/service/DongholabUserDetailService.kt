package com.dongholab.graphql.service

import com.dongholab.graphql.domain.account.AuthReqModel
import com.dongholab.graphql.domain.account.JWTToken
import com.dongholab.graphql.domain.account.RoleType
import com.dongholab.graphql.domain.account.UserPrincipal
import com.dongholab.graphql.repository.jpa.UserRepository
import com.dongholab.graphql.repository.reactive.UserRxRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class DongholabUserDetailService: UserDetailsService {

    @Autowired
    lateinit var jwtSigner: JWTSigner

    @Autowired
    lateinit var userRepository: UserRepository

    @Autowired
    lateinit var userRxRepository: UserRxRepository

    private val users: MutableMap<String, AuthReqModel> = mutableMapOf(
        "test" to AuthReqModel("test", "dongho_test", "1234", RoleType.ROLE_USER.toString()),
    )

    fun addUser(authReqModel: AuthReqModel): Unit {
        users[authReqModel.id] = authReqModel
    }

    fun checkAccount(authReqModel: AuthReqModel): Mono<JWTToken> {
        return Mono.justOrEmpty(users[authReqModel.id])
            .filter { it.password == authReqModel.password }
            .map {
                val jwt: String = jwtSigner.createJwt(it.id)
                JWTToken(jwt)
            }
    }

    fun getUserById(id: String): AuthReqModel? {
        return users[id]
    }

    override fun loadUserByUsername(id: String?): UserDetails {
        return (id?.let { it ->
            userRepository.findById(it).let { it ->
                it.get().let {
                    UserPrincipal(
                        it.id,
                        it.name,
                        it.password,
                        listOf<GrantedAuthority>(SimpleGrantedAuthority(it.roleType.name))
                    )
                }
            }
        }) ?: throw UsernameNotFoundException("Can not found username.")
    }
}
