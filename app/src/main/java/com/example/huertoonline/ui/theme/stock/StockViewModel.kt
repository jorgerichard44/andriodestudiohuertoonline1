package com.example.huertoonline.ui.theme.stock

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.huertoonline.data.model.Producto
import com.example.huertoonline.data.repository.CarritoRepository
import com.example.huertoonline.data.repository.ProductoRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

data class StockUiState(
    val searchQuery: String = "",
    val categoriaSeleccionada: String = "Todos",
    val productosFiltrados: List<Producto> = emptyList()
)

class StockViewModel(
    private val productoRepository: ProductoRepository,
    private val carritoRepository: CarritoRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(StockUiState())
    val uiState: StateFlow<StockUiState> = _uiState.asStateFlow()

    // CORRECCIÓN 1: Se accede a la propiedad 'productos' en lugar de llamar a 'getProductos()'
    val productos: StateFlow<List<Producto>> = productoRepository.productos
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    init {
        combine(productos, uiState) { productos, state ->
            val filtered = if (state.searchQuery.isBlank() && state.categoriaSeleccionada == "Todos") {
                productos
            } else {
                productos.filter { producto ->
                    val matchesCategory = state.categoriaSeleccionada == "Todos" ||
                            producto.categoria == state.categoriaSeleccionada
                    val matchesQuery = state.searchQuery.isBlank() ||
                            producto.nombre.contains(state.searchQuery, ignoreCase = true)
                    matchesCategory && matchesQuery
                }
            }
            _uiState.update { it.copy(productosFiltrados = filtered) }
        }.launchIn(viewModelScope)
    }

    fun onSearchQueryChange(query: String) {
        _uiState.update { it.copy(searchQuery = query) }
    }

    fun onCategoriaChange(categoria: String) {
        _uiState.update { it.copy(categoriaSeleccionada = categoria) }
    }

    fun agregarAlCarrito(producto: Producto) {
        viewModelScope.launch {
            // CORRECCIÓN 2: Se llama a 'agregarItem' en lugar de 'agregarProducto'
            carritoRepository.agregarItem(producto)
            // Opcional: Mostrar un Snackbar/Toast de confirmación
        }
    }
}