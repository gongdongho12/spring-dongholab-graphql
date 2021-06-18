package com.dongholab.graphql.service

import com.dongholab.graphql.domain.account.UserPrincipal
import com.dongholab.graphql.repository.jpa.UserRepository
import com.dongholab.graphql.repository.reactive.UserRxRepository
import org.springframework.beans.factory.annotation.Autowired
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
    lateinit var userRepository: UserRepository

    @Autowired
    lateinit var userRxRepository: UserRxRepository

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
