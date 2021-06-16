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
class WebSecurityConfig {

    @Bean
    fun securityWebFilterChain(
        http: ServerHttpSecurity,
        jwtAuthenticationManager: ReactiveAuthenticationManager,
        jwtAuthenticationConverter: ServerAuthenticationConverter
    ): SecurityWebFilterChain {
        val authenticationWebFilter = AuthenticationWebFilter(jwtAuthenticationManager)
        authenticationWebFilter.setServerAuthenticationConverter(jwtAuthenticationConverter)

        return http
            .cors()
            .and()
            .httpBasic().disable()
            .csrf().disable()
            .authorizeExchange()
                .pathMatchers("/user/signup").permitAll()
                .pathMatchers("/user/login").permitAll()
                .pathMatchers("/user")
                    .permitAll()
//                    .authenticated()
                .pathMatchers("/**").permitAll()
            .and()
            .formLogin().disable()
            .logout().disable()
            .addFilterAt(authenticationWebFilter, SecurityWebFiltersOrder.AUTHENTICATION)
            .build()
    }

//    @Bean
//    fun securityWebFilterChain(
//        http: ServerHttpSecurity
//    ): SecurityWebFilterChain? {
//
//        return http.authorizeExchange()
//            .pathMatchers("/**")
//            .permitAll()
//            .and()
//            .httpBasic()
//            .disable()
//            .csrf()
//            .disable()
//            .formLogin()
//            .disable()
//            .logout()
//            .disable()
//            .build()
//
////        return http!!.cors() // cors 설정
////            .and()
////            .httpBasic().disable() // Rest API 형태로 개발하기 때문에 비활성 시킵니다.
////            .csrf().disable() // 비활성
////            .exceptionHandling() // 상단에서 만들었던 RestAuthenticationEntryPoint 클래스 객체를 등록해줍니다.
//////            .authenticationEntryPoint(RestAuthenticationEntryPoint())
////            .and()
////            // matcher를 통해서 권한에 따른 제한을 두는 설정입니다.
////            .authorizeExchange().anyExchange().permitAll().and().build()
//
////        return http
////            .authorizeExchange()
////            .anyExchange()
////            .authenticated()
////            .and().build()
//    }

    @Bean
    fun passwordEncoder() = BCryptPasswordEncoder()

//    override fun configure(http: HttpSecurity?) {
//        http!!
//            .cors() // cors 설정
//            .and()
//            .httpBasic().disable() // Rest API 형태로 개발하기 때문에 비활성 시킵니다.
//            .csrf().disable() // 비활성
//            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // 세션설정도 STATELESS로 해줍니다.
//            .and()
//            .exceptionHandling() // 상단에서 만들었던 RestAuthenticationEntryPoint 클래스 객체를 등록해줍니다.
////            .authenticationEntryPoint(RestAuthenticationEntryPoint())
//            .and()
//            // matcher를 통해서 권한에 따른 제한을 두는 설정입니다.
//            .authorizeRequests()
//                .antMatchers("/admin/**").hasRole("ADMIN") // 어드민만 접근 가능
//                .antMatchers("/user/**").hasAnyRole("ADMIN", "USER") // 유저만 접근 가능
//                .antMatchers("/**").permitAll() // 모든 권한의 유저 접근 가능
//                .anyRequest().authenticated() // 모든 리퀘스트는 인증과정을 거처야 함
////            .and()
//            // 상단에서 만들었던 Jwt 토큰 필터를 등록해줍니다.
////            .addFilterBefore(
////                JwtAuthenticationFilter(jwtTokenProvider),
////                UsernamePasswordAuthenticationFilter::class.java
////            )
//    }

    // 인증의 저장 관리를 해줄 userDetailsService, passwordEncoder와 함께 등록해줍니다.
//    override fun configure(auth: AuthenticationManagerBuilder?) {
//        auth!!
//            .userDetailsService<UserDetailsService>(customUserDetailsService)
//            .passwordEncoder(passwordEncoder())
//    }

//    override fun configure(web: WebSecurity) {
//        web.ignoring().antMatchers("/playground").antMatchers("/graphql")
//    }
}