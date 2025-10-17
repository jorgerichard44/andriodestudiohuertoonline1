package com.example.huertoonline.utils

object Constants {
    const val APP_NAME = "Huerto Online"

    // Categorías
    val CATEGORIAS = listOf("Todos", "Verduras", "Frutas", "Hierbas")

    // Unidades
    val UNIDADES = listOf("kg", "unidad", "manojo", "gramos")

    // Rutas de navegación
    object Routes {
        const val LOGIN = "login"
        const val HOME = "home"
        const val STOCK = "stock"
        const val PRODUCTO_FORM = "producto_form"
        const val CARRITO = "carrito"
        const val INFORMACION = "informacion"
        const val PERFIL = "perfil"
    }

    // Mensajes
    object Messages {
        const val PRODUCTO_AGREGADO = "Producto agregado al carrito"
        const val PRODUCTO_GUARDADO = "Producto guardado exitosamente"
        const val CARRITO_VACIO = "Carrito vaciado"
        const val COMPRA_EXITOSA = "¡Compra realizada con éxito!"
        const val ERROR_GENERICO = "Ocurrió un error, intenta nuevamente"
    }

    // Banners
    val BANNERS = listOf(
        "🌱 Productos frescos y orgánicos",
        "🥬 Directo del huerto a tu mesa",
        "🍓 Frutas y verduras de temporada",
        "🌿 Cultivado con amor y cuidado"
    )
}


