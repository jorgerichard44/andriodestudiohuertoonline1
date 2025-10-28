package com.example.huertoonline.data.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.huertoonline.data.model.Credential
import com.example.huertoonline.data.model.Usuario
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "auth_prefs")

class AuthRepository(private val context: Context) {

    companion object {
        private val EMAIL_KEY = stringPreferencesKey("user_email")
        private val NAME_KEY = stringPreferencesKey("user_name")
        private val IS_LOGGED_KEY = stringPreferencesKey("is_logged")
    }

    private val usuariosRegistrados = listOf(
        Usuario("1", "Juan Pérez", "juan@huerto.com", "912345678", "Santiago Centro"),
        Usuario("2", "María González", "maria@huerto.com", "987654321", "Providencia"),
        Usuario("3", "Admin", "admin@huerto.com", "999999999", "Admin")
    )

    suspend fun login(credential: Credential): Boolean {
        val usuario = usuariosRegistrados.find { it.email == credential.email }

        if (usuario != null && credential.password.length >= 6) {
            context.dataStore.edit { preferences ->
                preferences[EMAIL_KEY] = usuario.email
                preferences[NAME_KEY] = usuario.nombre
                preferences[IS_LOGGED_KEY] = "true"
            }
            return true
        }
        return false
    }

    suspend fun logout() {
        context.dataStore.edit { preferences ->
            preferences.clear()
        }
    }

    val isLoggedIn: Flow<Boolean> = context.dataStore.data.map { preferences ->
        (preferences[IS_LOGGED_KEY] ?: "false").toBoolean()
    }

    val currentUser: Flow<Usuario?> = context.dataStore.data.map { preferences ->
        val email = preferences[EMAIL_KEY] ?: return@map null
        usuariosRegistrados.find { it.email == email }
    }
}
