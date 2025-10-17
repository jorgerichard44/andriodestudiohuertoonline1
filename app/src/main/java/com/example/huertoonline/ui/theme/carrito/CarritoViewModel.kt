package com.example.huertoonline.ui.theme.carrito

import androidx.lifecycle.ViewModel
import com.example.huertoonline.data.repository.CarritoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class CarritoUiState(
    val mostrarDialogoConfirmacion: Boolean = false,
    val compraFinalizada: Boolean = false
)

class CarritoViewModel(
    private val carritoRepository: CarritoRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(CarritoUiState())
    val uiState: StateFlow<CarritoUiState> = _uiState.asStateFlow()

    val items = carritoRepository.items
    val total = carritoRepository.total

    fun actualizarCantidad(productoId: String, cantidad: Int) {
        carritoRepository.actualizarCantidad(productoId, cantidad)
    }

    fun eliminarItem(productoId: String) {
        carritoRepository.eliminarItem(productoId)
    }

    fun mostrarDialogoVaciar() {
        _uiState.value = _uiState.value.copy(mostrarDialogoConfirmacion = true)
    }

    fun ocultarDialogoVaciar() {
        _uiState.value = _uiState.value.copy(mostrarDialogoConfirmacion = false)
    }

    fun vaciarCarrito() {
        carritoRepository.vaciarCarrito()
        _uiState.value = _uiState.value.copy(mostrarDialogoConfirmacion = false)
    }

    fun finalizarCompra() {
        // Aquí iría la lógica de pago
        carritoRepository.vaciarCarrito()
        _uiState.value = _uiState.value.copy(compraFinalizada = true)
    }

    fun resetCompraFinalizada() {
        _uiState.value = _uiState.value.copy(compraFinalizada = false)
    }

}