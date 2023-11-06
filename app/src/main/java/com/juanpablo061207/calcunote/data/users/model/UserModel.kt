package com.juanpablo061207.calcunote.data.users.model

import com.juanpablo061207.calcunote.domain.model.User

data class UserModel(
    val id: String,
    val email: String,
    val name: String,
    val document: String,
    val gradeId: Long
) {
    constructor() : this(id = "", email = "", name = "", document = "", gradeId = 0)
}

fun UserModel.toDomain() = User(id = id, email = email, name = name, document = document, gradeId = gradeId)