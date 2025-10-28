package com.example.huertoonline.ui.theme.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.huertoonline.data.model.Producto
import com.example.huertoonline.data.repository.AuthRepository
import com.example.huertoonline.data.repository.CarritoRepository
import com.example.huertoonline.data.repository.ProductoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class HomeUiState(
    val userName: String = "",
    val bannerIndex: Int = 0
)

class HomeViewModel(
    private val authRepository: AuthRepository,
    private val productoRepository: ProductoRepository,
    private val carritoRepository: CarritoRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    val currentUser = authRepository.currentUser
    val productosDestacados = productoRepository.productos

    fun agregarAlCarrito(producto: Producto) {
        viewModelScope.launch {
            carritoRepository.agregarItem(producto)
            // Opcional: Mostrar un Snackbar/Toast de confirmación
        }
    }

    fun updateBannerIndex(index: Int) {
        _uiState.value = _uiState.value.copy(bannerIndex = index)
    }
}