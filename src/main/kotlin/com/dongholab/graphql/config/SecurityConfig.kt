package com.dongholab.graphql.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.ReactiveAuthenticationManager
import org.springframework.security.config.web.server.SecurityWebFiltersOrder
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.server.SecurityWebFilterChain
import org.springframework.security.web.server.authentication.AuthenticationWebFilter
import org.springframework.security.web.server.authentication.ServerAuthenticationConverter

@Configuration
class SecurityConfig {

    @Bean
    fun passwordEncoder() = BCryptPasswordEncoder()

    @Bean
    fun securityWebFilterChain(http: ServerHttpSecurity, jwtAuthenticationManager: ReactiveAuthenticationManager, jwtAuthenticationConverter: ServerAuthenticationConverter): SecurityWebFilterChain {
        return http.authorizeExchange()
            .pathMatchers("/user/signup").permitAll()
            .pathMatchers("/user/login").permitAll()
            .pathMatchers("/user")
//                .authenticated()
                .permitAll()
            .pathMatchers("/**").permitAll()
            .and()
            .cors()
            .and()
            .addFilterAt(
                AuthenticationWebFilter(jwtAuthenticationManager).apply {
                    setServerAuthenticationConverter(jwtAuthenticationConverter)
                },
                SecurityWebFiltersOrder.AUTHENTICATION
            )
            .httpBasic()
            .disable()
            .csrf()
            .disable()
            .formLogin()
            .disable()
            .logout()
            .disable()
            .build()
    }
}