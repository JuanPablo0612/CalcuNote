package com.juanpablo061207.calcunote.data.auth.remote

import com.google.firebase.auth.FirebaseAuth
import com.juanpablo061207.calcunote.data.model.Result
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthRemoteDataSource @Inject constructor(private val auth: FirebaseAuth) {
    suspend fun login(email: String, password: String): Result<String> {
        return try {
            val loginResult = auth.signInWithEmailAndPassword(email, password).await()
            Result.Success(loginResult.user?.uid ?: "")
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    suspend fun signUp(email: String, password: String): Result<String> {
        return try {
            val result = auth.createUserWithEmailAndPassword(email, password).await()
            Result.Success(result.user!!.uid)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    suspend fun getCurrentUserId(): String {
        reloadUser()
        return auth.currentUser?.uid ?: ""
    }

    private suspend fun reloadUser() {
        auth.currentUser?.reload()?.await()
    }
}