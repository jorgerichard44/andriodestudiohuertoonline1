package com.example.huertoonline.ui.theme.home

import androidx.lifecycle.ViewModel
import com.example.huertoonline.data.repository.AuthRepository
import com.example.huertoonline.data.repository.ProductoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class HomeUiState(
    val userName: String = "",
    val bannerIndex: Int = 0
)

class HomeViewModel(
    private val authRepository: AuthRepository,
    private val productoRepository: ProductoRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    val currentUser = authRepository.currentUser
    val productosDestacados = productoRepository.productos

    fun updateBannerIndex(index: Int) {
        _uiState.value = _uiState.value.copy(bannerIndex = index)
    }
}