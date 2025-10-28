package com.example.huertoonline.ui.producto

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.huertoonline.ui.components.CameraCapture
import com.example.huertoonline.utils.Constants
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState

@OptIn(ExperimentalMaterial3Api::class, ExperimentalPermissionsApi::class)
@Composable
fun ProductoFormScreen(
    viewModel: ProductoViewModel,
    onNavigateBack: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    val scrollState = rememberScrollState()

    // Permisos de cámara
    val cameraPermissionState = rememberPermissionState(
        android.Manifest.permission.CAMERA
    )

    // Launcher para galería
    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let { viewModel.onImagenSelected(it) }
    }

    // Efecto para navegar cuando se guarda
    LaunchedEffect(uiState.guardadoExitoso) {
        if (uiState.guardadoExitoso) {
            onNavigateBack()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Agregar Producto") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(scrollState)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Sección de imagen
            Card(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        "Imagen del producto",
                        style = MaterialTheme.typography.titleMedium
                    )

                    if (uiState.imagenUri != null) {
                        // Mostrar preview de imagen
                        Icon(
                            Icons.Default.CheckCircle,
                            contentDescription = null,
                            modifier = Modifier.size(48.dp),
                            tint = MaterialTheme.colorScheme.primary
                        )
                        Text("Imagen seleccionada")
                    }

                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        OutlinedButton(
                            onClick = {
                                if (cameraPermissionState.hasPermission) {
                                    viewModel.toggleMostrarCamara()
                                } else {
                                    cameraPermissionState.launchPermissionRequest()
                                }
                            }
                        ) {
                            Icon(Icons.Default.Camera, contentDescription = null)
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("Cámara")
                        }

                        OutlinedButton(
                            onClick = { galleryLauncher.launch("image/*") }
                        ) {
                            Icon(Icons.Default.PhotoLibrary, contentDescription = null)
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("Galería")
                        }
                    }
                }
            }

            // Nombre
            OutlinedTextField(
                value = uiState.nombre,
                onValueChange = { viewModel.onNombreChange(it) },
                label = { Text("Nombre del producto *") },
                isError = uiState.nombreError != null,
                supportingText = {
                    uiState.nombreError?.let { Text(it) }
                },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            // Categoría
            var expandedCategoria by remember { mutableStateOf(false) }
            ExposedDropdownMenuBox(
                expanded = expandedCategoria,
                onExpandedChange = { expandedCategoria = it }
            ) {
                OutlinedTextField(
                    value = uiState.categoria,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Categoría *") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expandedCategoria) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor()
                )
                ExposedDropdownMenu(
                    expanded = expandedCategoria,
                    onDismissRequest = { expandedCategoria = false }
                ) {
                    listOf("Verduras", "Frutas", "Hierbas").forEach { categoria ->
                        DropdownMenuItem(
                            text = { Text(categoria) },
                            onClick = {
                                viewModel.onCategoriaChange(categoria)
                                expandedCategoria = false
                            }
                        )
                    }
                }
            }

            // Precio y Stock en fila
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedTextField(
                    value = uiState.precio,
                    onValueChange = { viewModel.onPrecioChange(it) },
                    label = { Text("Precio *") },
                    leadingIcon = { Text("$") },
                    isError = uiState.precioError != null,
                    supportingText = {
                        uiState.precioError?.let { Text(it) }
                    },
                    modifier = Modifier.weight(1f),
                    singleLine = true
                )

                OutlinedTextField(
                    value = uiState.stock,
                    onValueChange = { viewModel.onStockChange(it) },
                    label = { Text("Stock *") },
                    isError = uiState.stockError != null,
                    supportingText = {
                        uiState.stockError?.let { Text(it) }
                    },
                    modifier = Modifier.weight(1f),
                    singleLine = true
                )
            }

            // Unidad
            var expandedUnidad by remember { mutableStateOf(false) }
            ExposedDropdownMenuBox(
                expanded = expandedUnidad,
                onExpandedChange = { expandedUnidad = it }
            ) {
                OutlinedTextField(
                    value = uiState.unidad,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Unidad *") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expandedUnidad) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor()
                )
                ExposedDropdownMenu(
                    expanded = expandedUnidad,
                    onDismissRequest = { expandedUnidad = false }
                ) {
                    Constants.UNIDADES.forEach { unidad ->
                        DropdownMenuItem(
                            text = { Text(unidad) },
                            onClick = {
                                viewModel.onUnidadChange(unidad)
                                expandedUnidad = false
                            }
                        )
                    }
                }
            }

            // Botón para guardar
            Button(
                onClick = { viewModel.guardarProducto() },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Guardar Producto")
            }
        }
    }
}
