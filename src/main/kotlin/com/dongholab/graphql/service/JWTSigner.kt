package com.dongholab.graphql.service

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jws
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.security.KeyPair
import java.time.Duration
import java.time.Instant
import java.util.*

@Service
class JWTSigner {
    companion object {
        private const val TOKEN_TTL = 60 * 60 * 1000L
    }

    @Autowired
    lateinit var userDetailService: DongholabUserDetailService

    val keyPair: KeyPair = Keys.keyPairFor(SignatureAlgorithm.RS256)

    fun createJwt(userId: String): String {
        val now = Date()
        return Jwts.builder()
            .setClaims(
                Jwts.claims().setSubject(userId)
                    .also {
                        it["role"] = userDetailService.getUserById(userId)?.roleType
                        it["password"] = userDetailService.getUserById(userId)?.password
                    }
            )
            .setIssuer("identity")
//            .setExpiration(Date.from(now.plus(Duration.ofMinutes(15))))
            .setExpiration(Date(now.time + TOKEN_TTL))
            .setIssuedAt(now)
            .signWith(keyPair.private, SignatureAlgorithm.RS256)
            .compact()
    }

//    fun getUserId(token: String?): String =
//        Jwts.parserBuilder()
//            .setSigningKey(keyPair.public)
//            .build()
//            .parseClaimsJws(token)
//            .body
//            .subject
//
//    fun createToken(authentication: Authentication?): String =
//        Jwts.builder().let {
//            val now = Date()
//
//            // 전달받은 인증 정보로부터 principal 값을 가져옵니다.
//            val userPrincipal = authentication?.principal as UserPrincipal
//
//            // 토큰 빌더를 통해서 토큰을 생성해줍니다.
//            it.setClaims(
//                // username = id 입니다.(PK)
//                Jwts.claims().setSubject(userPrincipal.username)
//                    .also { claims ->
//                        claims["role"] = userPrincipal.authorities.first()
//                    }
//            )
//                .setIssuedAt(now)
//                .setExpiration(Date(now.time + TOKEN_TTL))
//                .signWith(keyPair.private, SignatureAlgorithm.RS256)
//                .compact()
//        }!!

    /**
     * Validate the JWT where it will throw an exception if it isn't valid.
     */
    fun validateJwt(jwt: String): Jws<Claims>? {
        println("validateJwt $jwt")

        val claims: Jws<Claims> = Jwts.parserBuilder()
            .setSigningKey(keyPair.public)
            .build()
            .parseClaimsJws(jwt)

        return claims.let {
            return if (it.body.expiration.before(Date())) {
                it
            } else {
                null
            }
        }
    }
}