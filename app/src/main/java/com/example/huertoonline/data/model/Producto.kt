package com.example.huertoonline.data.model

data class Producto(
    val id: String = "",
    val nombre: String = "",
    val categoria: String = "", // Verduras, Frutas, Hierbas
    val precio: Double = 0.0,
    val stock: Int = 0,
    val unidad: String = "kg", // kg, unidad, manojo
    val descripcion: String = "",
    val imagenUrl: String = "",
    val beneficios: String = ""
)