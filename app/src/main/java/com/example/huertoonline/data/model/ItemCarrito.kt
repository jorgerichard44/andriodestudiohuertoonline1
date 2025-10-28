package com.example.huertoonline.data.model

data class ItemCarrito(
    val producto: Producto,
    val cantidad: Int = 1
) {
    val subtotal: Double
        get() = producto.precio * cantidad
}