package com.juanpablo061207.calcunote.data.auth

import com.juanpablo061207.calcunote.data.auth.local.AuthLocalDataSource
import com.juanpablo061207.calcunote.data.model.Result
import com.juanpablo061207.calcunote.data.model.data
import com.juanpablo061207.calcunote.data.model.exception
import com.juanpablo061207.calcunote.data.model.isSuccess
import com.juanpablo061207.calcunote.data.auth.remote.AuthRemoteDataSource
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val authRemoteDataSource: AuthRemoteDataSource,
    private val authLocalDataSource: AuthLocalDataSource
) {
    suspend fun getCurrentUserId() = authLocalDataSource.getCurrentUserId().first()

    suspend fun login(email: String, password: String): Result<Nothing> {
        val loginResult = authRemoteDataSource.login(email, password)

        if (!loginResult.isSuccess()) {
            return Result.Error(loginResult.exception())
        }

        val userId = loginResult.data()
        authLocalDataSource.saveUserId(userId)

        return Result.Success(null)
    }

    suspend fun signUp(email: String, password: String): Result<String> {
        val signUpResult = authRemoteDataSource.signUp(email, password)

        if (!signUpResult.isSuccess()) {
            return signUpResult
        }

        val userId = signUpResult.data()
        authLocalDataSource.saveUserId(userId)

        return Result.Success(userId)
    }
}