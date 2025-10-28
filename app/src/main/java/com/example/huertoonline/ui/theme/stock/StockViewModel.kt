package com.example.huertoonline.ui.theme.stock

import androidx.lifecycle.ViewModel
import com.example.huertoonline.data.model.Producto
import com.example.huertoonline.data.repository.CarritoRepository
import com.example.huertoonline.data.repository.ProductoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class StockUiState(
    val categoriaSeleccionada: String = "Todos",
    val searchQuery: String = "",
    val productosFiltrados: List<Producto> = emptyList()
)

class StockViewModel(
    private val productoRepository: ProductoRepository,
    private val carritoRepository: CarritoRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(StockUiState())
    val uiState: StateFlow<StockUiState> = _uiState.asStateFlow()

    val productos = productoRepository.productos

    fun onCategoriaChange(categoria: String) {
        _uiState.value = _uiState.value.copy(categoriaSeleccionada = categoria)
        filtrarProductos()
    }

    fun onSearchQueryChange(query: String) {
        _uiState.value = _uiState.value.copy(searchQuery = query)
        filtrarProductos()
    }

    private fun filtrarProductos() {
        val categoria = _uiState.value.categoriaSeleccionada
        val query = _uiState.value.searchQuery.lowercase()

        var productosFiltrados = if (categoria == "Todos") {
            productos.value
        } else {
            productos.value.filter { it.categoria == categoria }
        }

        if (query.isNotBlank()) {
            productosFiltrados = productosFiltrados.filter {
                it.nombre.lowercase().contains(query) ||
                        it.descripcion.lowercase().contains(query)
            }
        }

        _uiState.value = _uiState.value.copy(productosFiltrados = productosFiltrados)
    }

    fun agregarAlCarrito(producto: Producto, cantidad: Int = 1) {
        carritoRepository.agregarItem(producto, cantidad)
    }
}