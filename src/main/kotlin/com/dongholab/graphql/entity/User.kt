package com.dongholab.graphql.entity

import com.dongholab.graphql.domain.account.AuthReqModel
import com.dongholab.graphql.domain.account.RoleType
import javax.persistence.*

@Entity
@Table(name = "user")
data class User(
    @Id
    @org.springframework.data.annotation.Id
    var id: String,
    @Column(name = "name")
    val name: String,
    @Column(name = "password")
    val password: String,
    @Column(name = "role_type")
    @Enumerated(EnumType.STRING)
    val roleType: RoleType
) {
    constructor(authReqModel: AuthReqModel): this(authReqModel.id, authReqModel.name, authReqModel.password, RoleType.valueOf(authReqModel.roleType))
}