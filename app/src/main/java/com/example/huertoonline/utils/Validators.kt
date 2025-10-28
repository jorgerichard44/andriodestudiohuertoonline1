package com.example.huertoonline.utils

import android.util.Patterns

object Validators {

    fun validarEmail(email: String): String? {
        return when {
            email.isBlank() -> "El email es requerido"
            !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> "Email inválido"
            else -> null
        }
    }

    fun validarPassword(password: String): String? {
        return when {
            password.isBlank() -> "La contraseña es requerida"
            password.length < 6 -> "Mínimo 6 caracteres"
            else -> null
        }
    }

    fun validarCampoRequerido(valor: String, nombreCampo: String): String? {
        return if (valor.isBlank()) "$nombreCampo es requerido" else null
    }

    fun validarPrecio(precio: String): String? {
        return when {
            precio.isBlank() -> "El precio es requerido"
            precio.toDoubleOrNull() == null -> "Precio inválido"
            precio.toDouble() <= 0 -> "El precio debe ser mayor a 0"
            else -> null
        }
    }

    fun validarStock(stock: String): String? {
        return when {
            stock.isBlank() -> "El stock es requerido"
            stock.toIntOrNull() == null -> "Stock inválido"
            stock.toInt() < 0 -> "El stock no puede ser negativo"
            else -> null
        }
    }

    fun validarTelefono(telefono: String): String? {
        return when {
            telefono.isBlank() -> "El teléfono es requerido"
            telefono.length < 9 -> "Teléfono inválido"
            !telefono.all { it.isDigit() } -> "Solo números"
            else -> null
        }
    }
}