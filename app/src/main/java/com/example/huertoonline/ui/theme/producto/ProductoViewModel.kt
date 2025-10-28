package com.example.huertoonline.ui.theme.producto

import android.net.Uri
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class ProductoFormUiState(
    val nombre: String = "",
    val nombreError: String? = null,
    val categoria: String = "",
    val precio: String = "",
    val precioError: String? = null,
    val stock: String = "",
    val stockError: String? = null,
    val unidad: String = "",
    val imagenUri: Uri? = null,
    val mostrarCamara: Boolean = false,
    val guardadoExitoso: Boolean = false
)

class ProductoViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(ProductoFormUiState())
    val uiState: StateFlow<ProductoFormUiState> = _uiState.asStateFlow()

    fun onNombreChange(nombre: String) {
        _uiState.update { it.copy(nombre = nombre, nombreError = null) }
    }

    fun onCategoriaChange(categoria: String) {
        _uiState.update { it.copy(categoria = categoria) }
    }

    fun onPrecioChange(precio: String) {
        _uiState.update { it.copy(precio = precio, precioError = null) }
    }

    fun onStockChange(stock: String) {
        _uiState.update { it.copy(stock = stock, stockError = null) }
    }

    fun onUnidadChange(unidad: String) {
        _uiState.update { it.copy(unidad = unidad) }
    }

    fun onImagenSelected(uri: Uri) {
        _uiState.update { it.copy(imagenUri = uri) }
    }

    fun toggleMostrarCamara() {
        _uiState.update { it.copy(mostrarCamara = !it.mostrarCamara) }
    }

    fun guardarProducto() {
        // Lógica para guardar el producto...
        _uiState.update { it.copy(guardadoExitoso = true) }
    }
}