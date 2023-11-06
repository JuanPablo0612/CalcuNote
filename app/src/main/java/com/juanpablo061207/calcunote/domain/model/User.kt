package com.juanpablo061207.calcunote.domain.model

import com.juanpablo061207.calcunote.data.users.model.UserModel

data class User(
    val id: String,
    val email: String,
    val name: String,
    val document: String,
    val gradeId: Long
)

fun User.toModel() = UserModel(id = id, email = email, name = name, document = document, gradeId = gradeId)