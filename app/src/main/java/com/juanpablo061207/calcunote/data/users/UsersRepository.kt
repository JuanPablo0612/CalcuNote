package com.juanpablo061207.calcunote.data.users

import com.juanpablo061207.calcunote.data.model.Result
import com.juanpablo061207.calcunote.data.users.remote.UsersRemoteDataSource
import com.juanpablo061207.calcunote.domain.model.User
import com.juanpablo061207.calcunote.domain.model.toModel
import javax.inject.Inject

class UsersRepository @Inject constructor(private val usersRemoteDataSource: UsersRemoteDataSource) {
    suspend fun saveUser(user: User): Result<Nothing> {
        val userModel = user.toModel()
        return usersRemoteDataSource.saveUser(userModel)
    }
}