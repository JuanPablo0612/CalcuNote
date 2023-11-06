package com.juanpablo061207.calcunote.domain.usecase.user

import com.juanpablo061207.calcunote.data.model.Result
import com.juanpablo061207.calcunote.data.model.data
import com.juanpablo061207.calcunote.data.model.exception
import com.juanpablo061207.calcunote.data.model.isSuccess
import com.juanpablo061207.calcunote.data.auth.AuthRepository
import com.juanpablo061207.calcunote.data.users.UsersRepository
import com.juanpablo061207.calcunote.domain.model.User
import javax.inject.Inject

class SignUpUseCase @Inject constructor(
    private val authRepository: AuthRepository,
    private val usersRepository: UsersRepository
) {
    suspend operator fun invoke(
        email: String,
        password: String,
        name: String,
        dni: String,
        gradeId: Long
    ): Result<Nothing> {
        val signUpResult = authRepository.signUp(email, password)

        if (!signUpResult.isSuccess()) {
            return Result.Error(signUpResult.exception())
        }

        val userId = signUpResult.data()

        val user = User(id = userId, email = email, name = name, document = dni, gradeId = gradeId)

        return usersRepository.saveUser(user)
    }
}