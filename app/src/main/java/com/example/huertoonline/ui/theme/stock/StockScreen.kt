package com.example.huertoonline.ui.theme.stock

import androidx.compose.animation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.huertoonline.data.model.Producto
import com.example.huertoonline.ui.components.ProductoCard
import com.example.huertoonline.utils.Constants
import androidx.compose.foundation.lazy.items
import androidx.compose.ui.Alignment

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StockScreen(
    viewModel: StockViewModel,
    onProductoClick: (String) -> Unit,
    onAgregarProducto: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    val productos by viewModel.productos.collectAsState()

    val productosMostrar = if (uiState.productosFiltrados.isNotEmpty() ||
        uiState.searchQuery.isNotBlank() ||
        uiState.categoriaSeleccionada != "Todos") {
        uiState.productosFiltrados
    } else {
        productos
    }

    Scaffold(
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = onAgregarProducto,
                icon = { Icon(Icons.Default.Add, contentDescription = null) },
                text = { Text("Agregar Producto") }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            // Barra de búsqueda
            OutlinedTextField(
                value = uiState.searchQuery,
                onValueChange = { viewModel.onSearchQueryChange(it) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                placeholder = { Text("Buscar productos...") },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                trailingIcon = {
                    if (uiState.searchQuery.isNotBlank()) {
                        IconButton(onClick = { viewModel.onSearchQueryChange("") }) {
                            Icon(Icons.Default.Clear, contentDescription = "Limpiar")
                        }
                    }
                },
                singleLine = true
            )

            // Filtros de categoría
            ScrollableTabRow(
                selectedTabIndex = Constants.CATEGORIAS.indexOf(uiState.categoriaSeleccionada),
                modifier = Modifier.fillMaxWidth(),
                edgePadding = 16.dp
            ) {
                Constants.CATEGORIAS.forEach { categoria ->
                    Tab(
                        selected = uiState.categoriaSeleccionada == categoria,
                        onClick = { viewModel.onCategoriaChange(categoria) },
                        text = { Text(categoria) }
                    )
                }
            }

            // Grid de productos
            if (productosMostrar.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Icon(
                            Icons.Default.SearchOff,
                            contentDescription = null,
                            modifier = Modifier.size(64.dp),
                            tint = MaterialTheme.colorScheme.outline
                        )
                        Text(
                            "No se encontraron productos",
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            } else {
                LazyVerticalGrid(
                    columns = GridCells.Adaptive(160.dp),
                    contentPadding = PaddingValues(16.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(productosMostrar) { producto ->
                        ProductoCard(
                            producto = producto,
                            onClick = { onProductoClick(producto.id) },
                            onAddToCart = { viewModel.agregarAlCarrito(producto) }
                        )
                    }
                }
            }
        }
    }
}
