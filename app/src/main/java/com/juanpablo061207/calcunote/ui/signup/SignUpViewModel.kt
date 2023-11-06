package com.juanpablo061207.calcunote.ui.signup

import android.util.Patterns
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.juanpablo061207.calcunote.data.model.isSuccess
import com.juanpablo061207.calcunote.domain.usecase.user.SignUpUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(private val signUpUseCase: SignUpUseCase) : ViewModel() {
    var uiState by mutableStateOf(SignUpUiState())
        private set

    fun onEmailTextChanged(text: String) {
        uiState = uiState.copy(email = text)
        validateEmail()
    }

    private fun validateEmail() {
        val pattern = Patterns.EMAIL_ADDRESS
        val isValidEmail = pattern.matcher(uiState.email).matches()
        uiState = uiState.copy(isValidEmail = isValidEmail)
    }

    fun onPasswordTextChanged(text: String) {
        uiState = uiState.copy(password = text)
        validatePassword()
        validateRepeatPassword()
    }

    private fun validatePassword() {
        val isValidPassword = uiState.password.length in 8..16
        uiState = uiState.copy(isValidPassword = isValidPassword)
    }

    fun onPasswordVisibilityChanged() {
        uiState = uiState.copy(showPassword = uiState.showPassword)
    }

    fun onRepeatPasswordTextChanged(text: String) {
        uiState = uiState.copy(repeatPassword = text)
        validateRepeatPassword()
    }

    private fun validateRepeatPassword() {
        val isValidRepeatPassword = uiState.repeatPassword == uiState.password
        uiState = uiState.copy(isValidRepeatPassword = isValidRepeatPassword)
    }

    fun onNameTextChanged(text: String) {
        uiState = uiState.copy(name = text)
        validateName()
    }

    private fun validateName() {
        val isValidName = uiState.name.isNotBlank()
        uiState = uiState.copy(isValidName = isValidName)
    }

    fun onDocumentTextChanged(text: String) {
        uiState = uiState.copy(document = text)
        validateDocument()
    }

    private fun validateDocument() {
        val isValidDocument = uiState.document.isNotBlank()
        uiState = uiState.copy(isValidDocument = isValidDocument)
    }

    fun onSelectedGradeIdChanged(gradeId: Long) {
        uiState = uiState.copy(selectedGradeId = gradeId)
    }

    fun onSignUp() {
        viewModelScope.launch {
            uiState = uiState.copy(isLoading = true)

            val signUpResult = signUpUseCase(
                email = uiState.email.trim(),
                password = uiState.password.trim(),
                name = uiState.name.trim(),
                dni = uiState.document.trim(),
                gradeId = uiState.selectedGradeId ?: 0
            )

            uiState = uiState.copy(
                isLoading = false,
                isSuccess = signUpResult.isSuccess(),
                errorMessageId = null
            )
        }
    }
}

data class SignUpUiState(
    val email: String = "",
    val isValidEmail: Boolean = false,
    val password: String = "",
    val isValidPassword: Boolean = false,
    val showPassword: Boolean = false,
    val repeatPassword: String = "",
    val isValidRepeatPassword: Boolean = true,
    val name: String = "",
    val isValidName: Boolean = false,
    val document: String = "",
    val isValidDocument: Boolean = false,
    val selectedGradeId: Long? = null,
    val isLoading: Boolean = false,
    val isSuccess: Boolean? = null,
    val errorMessageId: Int? = null
)