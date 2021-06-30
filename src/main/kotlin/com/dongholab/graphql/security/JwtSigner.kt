package com.dongholab.graphql.security

import com.dongholab.graphql.domain.account.AuthBase
import com.dongholab.graphql.service.UserService
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import java.security.KeyPair
import java.time.Instant
import java.util.*

@Service
class JwtSigner {
    companion object {
        private const val TOKEN_TTL = 60 * 60 * 1000L
    }

    @Autowired
    lateinit var passwordEncoder: PasswordEncoder

    @Autowired
    lateinit var userService: UserService

    val keyPair: KeyPair = Keys.keyPairFor(SignatureAlgorithm.RS256)

    fun createJwt(login: AuthBase): Mono<String?> {
        val now = Date()
        return userService.getUser(login.id).mapNotNull {
            println("getUser $it")
            it?.let { user ->
                if (login.id == user.id && passwordEncoder.matches(login.password, user.password)) {
                    val claims = Jwts.claims()
                        .setSubject(user.id)
                        .also { claims ->
                            claims["name"] = user.name
                            claims["role"] = user.roleType
                            claims["password"] = login.password
                        }
                    Jwts.builder()
                        .signWith(keyPair.private, SignatureAlgorithm.RS256)
                        .setClaims(claims)
                        .setIssuer("identity")
                        .setExpiration(Date(now.time + TOKEN_TTL))
                        .setIssuedAt(Date.from(Instant.now()))
                        .compact()
                } else {
                    null
                }
            }?: null
        }.log()
    }

    fun validateJwt(jwt: String): Claims? {
        val claims: Claims = Jwts.parserBuilder()
            .setSigningKey(keyPair.public)
            .build()
            .parseClaimsJws(jwt)
            .body

        return claims.run {
            if (expiration.after(Date())) {
                this
            } else {
                null
            }
        }
    }
}