package com.juanpablo061207.calcunote.data.users.remote

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.juanpablo061207.calcunote.data.model.Result
import com.juanpablo061207.calcunote.data.users.model.UserModel
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class UsersRemoteDataSource @Inject constructor(private val firestore: FirebaseFirestore) {
    suspend fun saveUser(userModel: UserModel): Result<Nothing> {
        return try {
            firestore.document("users/${userModel.id}").set(userModel, SetOptions.merge()).await()
            Result.Success(null)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }
}