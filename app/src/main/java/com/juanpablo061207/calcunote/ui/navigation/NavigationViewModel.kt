package com.juanpablo061207.calcunote.ui.navigation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.juanpablo061207.calcunote.domain.usecase.user.GetCurrentUserIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NavigationViewModel @Inject constructor(private val getCurrentUserIdUseCase: GetCurrentUserIdUseCase) :
    ViewModel() {
    var uiState by mutableStateOf(NavigationUiState())
        private set

    init {
        start()
    }

    private fun start() {
        viewModelScope.launch {
            val userId = getCurrentUserIdUseCase()
            uiState = uiState.copy(isLoggedIn = userId.isNotEmpty(), isLoading = false)
        }
    }
}

data class NavigationUiState(
    val isLoggedIn: Boolean = false,
    val isLoading: Boolean = true
)