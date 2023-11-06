package com.juanpablo061207.calcunote.domain.usecase.user

import com.juanpablo061207.calcunote.data.auth.AuthRepository
import javax.inject.Inject

class LoginUseCase @Inject constructor(private val authRepository: AuthRepository) {
    suspend operator fun invoke(email: String, password: String) =
        authRepository.login(email, password)
}