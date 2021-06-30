package com.dongholab.graphql.security

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpHeaders
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.web.server.authentication.ServerAuthenticationConverter
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono

@Component
class JwtServerAuthenticationConverter : ServerAuthenticationConverter {
    @Autowired
    lateinit var jwtSigner: JwtSigner

    override fun convert(exchange: ServerWebExchange?): Mono<Authentication> {
        return Mono.justOrEmpty(exchange).flatMap {
                Mono.justOrEmpty(it.request.headers[HttpHeaders.AUTHORIZATION]?.get(0))
            }
            .filter { it?.startsWith("Bearer ")?: false }
            .mapNotNull {
                it?.let {
                    println("authHeader $it")
                    val authToken = it.substring(7)
                    println("authToken $authToken")
                    jwtSigner.validateJwt(authToken)?.let { claims ->
                        UsernamePasswordAuthenticationToken(
                            claims.subject,
                            claims.get("password", String::class.java),
                            mutableListOf(
                                SimpleGrantedAuthority(claims.get("role", String::class.java))
                            )
                        )
                    }
                }
            }
    }
}
