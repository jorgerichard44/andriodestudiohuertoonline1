package com.example.huertoonline.ui.theme.producto

import android.net.Uri
import androidx.lifecycle.ViewModel
import com.example.huertoonline.data.model.Producto
import com.example.huertoonline.data.repository.ProductoRepository
import com.example.huertoonline.utils.Validators
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow


data class ProductoFormUiState(
    val nombre: String = "",
    val categoria: String = "Verduras",
    val precio: String = "",
    val stock: String = "",
    val unidad: String = "kg",
    val descripcion: String = "",
    val beneficios: String = "",
    val imagenUri: Uri? = null,
    val nombreError: String? = null,
    val precioError: String? = null,
    val stockError: String? = null,
    val descripcionError: String? = null,
    val guardadoExitoso: Boolean = false,
    val mostrarCamara: Boolean = false
)

class ProductoViewModel(
    private val productoRepository: ProductoRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(ProductoFormUiState())
    val uiState: StateFlow<ProductoFormUiState> = _uiState.asStateFlow()

    fun onNombreChange(nombre: String) {
        _uiState.value = _uiState.value.copy(nombre = nombre, nombreError = null)
    }

    fun onCategoriaChange(categoria: String) {
        _uiState.value = _uiState.value.copy(categoria = categoria)
    }

    fun onPrecioChange(precio: String) {
        _uiState.value = _uiState.value.copy(precio = precio, precioError = null)
    }

    fun onStockChange(stock: String) {
        _uiState.value = _uiState.value.copy(stock = stock, stockError = null)
    }

    fun onUnidadChange(unidad: String) {
        _uiState.value = _uiState.value.copy(unidad = unidad)
    }

    fun onDescripcionChange(descripcion: String) {
        _uiState.value = _uiState.value.copy(descripcion = descripcion, descripcionError = null)
    }

    fun onBeneficiosChange(beneficios: String) {
        _uiState.value = _uiState.value.copy(beneficios = beneficios)
    }

    fun onImagenSelected(uri: Uri?) {
        _uiState.value = _uiState.value.copy(imagenUri = uri)
    }

    fun toggleMostrarCamara() {
        _uiState.value = _uiState.value.copy(
            mostrarCamara = !_uiState.value.mostrarCamara
        )
    }

    fun guardarProducto() {
        val state = _uiState.value

        // Validaciones
        val nombreError = Validators.validarCampoRequerido(state.nombre, "Nombre")
        val precioError = Validators.validarPrecio(state.precio)
        val stockError = Validators.validarStock(state.stock)
        val descripcionError = Validators.validarCampoRequerido(state.descripcion, "Descripción")

        if (nombreError != null || precioError != null ||
            stockError != null || descripcionError != null) {
            _uiState.value = state.copy(
                nombreError = nombreError,
                precioError = precioError,
                stockError = stockError,
                descripcionError = descripcionError
            )
            return
        }

        // Crear producto
        val producto = Producto(
            nombre = state.nombre,
            categoria = state.categoria,
            precio = state.precio.toDouble(),
            stock = state.stock.toInt(),
            unidad = state.unidad,
            descripcion = state.descripcion,
            beneficios = state.beneficios,
            imagenUrl = state.imagenUri?.toString() ?: ""
        )

        productoRepository.agregarProducto(producto)

        _uiState.value = state.copy(guardadoExitoso = true)
    }

    fun resetForm() {
        _uiState.value = ProductoFormUiState()
    }
}



