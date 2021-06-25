package com.dongholab.graphql.config

import org.springframework.http.HttpHeaders
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.web.server.authentication.ServerAuthenticationConverter
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono

@Component
class JwtServerAuthenticationConverter : ServerAuthenticationConverter {
    override fun convert(exchange: ServerWebExchange?): Mono<Authentication> {
        return Mono.justOrEmpty(exchange)
            .flatMap {
                val jwtToken = it.request.headers.getFirst(HttpHeaders.AUTHORIZATION)?.startsWith("Bearer ")
                println("JwtServerAuthenticationConverter jwtToken $jwtToken")
                Mono.justOrEmpty(it.request.cookies["X-Auth"])
            }
            .filter { it.isNotEmpty() }
            .map { it[0].value }
            .map { UsernamePasswordAuthenticationToken(it, it) }
    }
}