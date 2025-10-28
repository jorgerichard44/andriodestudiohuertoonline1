package com.example.huertoonline.ui.theme

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.huertoonline.data.model.Credential
import com.example.huertoonline.data.repository.AuthRepository
import com.example.huertoonline.utils.Validators
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class LoginUiState(
    val email: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val loginSuccess: Boolean = false,
    val emailError: String? = null,
    val passwordError: String? = null
)

class LoginViewModel(private val authRepository: AuthRepository) : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    fun onEmailChange(email: String) {
        _uiState.value = _uiState.value.copy(
            email = email,
            emailError = null,
            errorMessage = null
        )
    }

    fun onPasswordChange(password: String) {
        _uiState.value = _uiState.value.copy(
            password = password,
            passwordError = null,
            errorMessage = null
        )
    }

    fun login() {
        val emailError = Validators.validarEmail(_uiState.value.email)
        val passwordError = Validators.validarPassword(_uiState.value.password)

        if (emailError != null || passwordError != null) {
            _uiState.value = _uiState.value.copy(
                emailError = emailError,
                passwordError = passwordError
            )
            return
        }

        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)

            val credential = Credential(
                email = _uiState.value.email,
                password = _uiState.value.password
            )

            val success = authRepository.login(credential)

            _uiState.value = if (success) {
                _uiState.value.copy(
                    isLoading = false,
                    loginSuccess = true,
                    errorMessage = null
                )
            } else {
                _uiState.value.copy(
                    isLoading = false,
                    errorMessage = "Credenciales inválidas"
                )
            }
        }
    }
}