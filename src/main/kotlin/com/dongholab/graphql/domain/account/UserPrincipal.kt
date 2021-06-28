package com.dongholab.graphql.domain.account

import com.dongholab.graphql.entity.User
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

class UserPrincipal(
    private val id: String,
    val name: String,
    private val password: String,
    private val authorities: Collection<GrantedAuthority>
): UserDetails {
    constructor(user: User): this(user.id, user.name, user.password, listOf(SimpleGrantedAuthority(user.roleType.name)))

    override fun getAuthorities() = this.authorities

    override fun getPassword() = this.password

    override fun getUsername() = this.id

    override fun isAccountNonExpired() = true

    override fun isAccountNonLocked() = true

    override fun isCredentialsNonExpired() = true

    override fun isEnabled() = true
}