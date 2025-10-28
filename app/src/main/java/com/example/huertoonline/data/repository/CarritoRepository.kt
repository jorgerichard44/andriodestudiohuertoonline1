package com.example.huertoonline.data.repository

import com.example.huertoonline.data.model.ItemCarrito
import com.example.huertoonline.data.model.Producto
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class CarritoRepository {

    private val _items = MutableStateFlow<List<ItemCarrito>>(emptyList())
    val items: StateFlow<List<ItemCarrito>> = _items.asStateFlow()

    private val _total = MutableStateFlow(0.0)
    val total: StateFlow<Double> = _total.asStateFlow()

    fun agregarItem(producto: Producto, cantidad: Int = 1) {
        val listaActual = _items.value.toMutableList()
        val itemExistente = listaActual.find { it.producto.id == producto.id }

        if (itemExistente != null) {
            listaActual.remove(itemExistente)
            listaActual.add(itemExistente.copy(cantidad = itemExistente.cantidad + cantidad))
        } else {
            listaActual.add(ItemCarrito(producto, cantidad))
        }

        _items.value = listaActual
        actualizarTotal()
    }

    fun actualizarCantidad(productoId: String, nuevaCantidad: Int) {
        if (nuevaCantidad <= 0) {
            eliminarItem(productoId)
            return
        }

        _items.value = _items.value.map { item ->
            if (item.producto.id == productoId) {
                item.copy(cantidad = nuevaCantidad)
            } else {
                item
            }
        }
        actualizarTotal()
    }

    fun eliminarItem(productoId: String) {
        _items.value = _items.value.filter { it.producto.id != productoId }
        actualizarTotal()
    }

    fun vaciarCarrito() {
        _items.value = emptyList()
        actualizarTotal()
    }

    private fun actualizarTotal() {
        _total.value = _items.value.sumOf { it.subtotal }
    }

    fun getCantidadTotal(): Int {
        return _items.value.sumOf { it.cantidad }
    }
}