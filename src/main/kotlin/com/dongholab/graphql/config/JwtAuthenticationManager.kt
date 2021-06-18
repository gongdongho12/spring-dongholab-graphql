package com.dongholab.graphql.config

import com.dongholab.graphql.service.JWTSigner
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jws
import org.springframework.security.authentication.ReactiveAuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono


@Component
class JwtAuthenticationManager(private val jwtSigner: JWTSigner) : ReactiveAuthenticationManager {
    override fun authenticate(authentication: Authentication): Mono<Authentication?>? {
        val authToken = authentication.credentials.toString()
        val jwsClaims: Jws<Claims>? = jwtSigner.validateJwt(authToken)
        return jwsClaims?.body?.let {
            val username = it.subject
            val rolesMap: List<GrantedAuthority> = it.get("role", List::class.java) as List<GrantedAuthority>
            UsernamePasswordAuthenticationToken(
                username,
                null,
                rolesMap
            )
        }?.let { Mono.just(it) }
    }
}