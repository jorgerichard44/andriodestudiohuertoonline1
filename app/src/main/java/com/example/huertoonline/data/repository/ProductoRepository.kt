package com.example.huertoonline.data.repository

import com.example.huertoonline.data.model.Producto
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class ProductoRepository {

    private val _productos = MutableStateFlow(getProductosIniciales())
    val productos: StateFlow<List<Producto>> = _productos.asStateFlow()

    fun agregarProducto(producto: Producto) {
        val nuevaLista = _productos.value.toMutableList()
        nuevaLista.add(producto.copy(id = System.currentTimeMillis().toString()))
        _productos.value = nuevaLista
    }

    fun actualizarProducto(producto: Producto) {
        val nuevaLista = _productos.value.map {
            if (it.id == producto.id) producto else it
        }
        _productos.value = nuevaLista
    }

    fun eliminarProducto(id: String) {
        _productos.value = _productos.value.filter { it.id != id }
    }

    fun obtenerProductoPorId(id: String): Producto? {
        return _productos.value.find { it.id == id }
    }

    fun filtrarPorCategoria(categoria: String): List<Producto> {
        if (categoria == "Todos") return _productos.value
        return _productos.value.filter { it.categoria == categoria }
    }

    private fun getProductosIniciales(): List<Producto> {
        return listOf(
            Producto(
                id = "1",
                nombre = "Tomate Cherry",
                categoria = "Verduras",
                precio = 2500.0,
                stock = 50,
                unidad = "kg",
                descripcion = "Tomates cherry orgánicos, dulces y jugosos",
                imagenUrl = "",
                beneficios = "Rico en licopeno y vitamina C"
            ),
            Producto(
                id = "2",
                nombre = "Lechuga Hidropónica",
                categoria = "Verduras",
                precio = 1800.0,
                stock = 30,
                unidad = "unidad",
                descripcion = "Lechuga fresca cultivada en sistema hidropónico",
                imagenUrl = "",
                beneficios = "Baja en calorías, rica en fibra"
            ),
            Producto(
                id = "3",
                nombre = "Albahaca",
                categoria = "Hierbas",
                precio = 1200.0,
                stock = 40,
                unidad = "manojo",
                descripcion = "Albahaca fresca aromática",
                imagenUrl = "",
                beneficios = "Propiedades antiinflamatorias"
            ),
            Producto(
                id = "4",
                nombre = "Frutillas",
                categoria = "Frutas",
                precio = 3500.0,
                stock = 25,
                unidad = "kg",
                descripcion = "Frutillas orgánicas de temporada",
                imagenUrl = "",
                beneficios = "Alto contenido de antioxidantes"
            ),
            Producto(
                id = "5",
                nombre = "Cilantro",
                categoria = "Hierbas",
                precio = 800.0,
                stock = 60,
                unidad = "manojo",
                descripcion = "Cilantro fresco para tus comidas",
                imagenUrl = "",
                beneficios = "Ayuda a la digestión"
            ),
            Producto(
                id = "6",
                nombre = "Zanahoria",
                categoria = "Verduras",
                precio = 1500.0,
                stock = 45,
                unidad = "kg",
                descripcion = "Zanahorias orgánicas crujientes",
                imagenUrl = "",
                beneficios = "Rica en betacaroteno"
            ),
            Producto(
                id = "7",
                nombre = "Perejil",
                categoria = "Hierbas",
                precio = 900.0,
                stock = 55,
                unidad = "manojo",
                descripcion = "Perejil fresco y aromático",
                imagenUrl = "",
                beneficios = "Rico en vitamina K"
            ),
            Producto(
                id = "8",
                nombre = "Pimentón",
                categoria = "Verduras",
                precio = 2200.0,
                stock = 35,
                unidad = "kg",
                descripcion = "Pimentones rojos y amarillos",
                imagenUrl = "",
                beneficios = "Alto contenido de vitamina C"
            )
        )
    }
}