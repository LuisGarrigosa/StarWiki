/**
 * Prefs.kt
 * @author Luis Manuel Garrigosa Jimenez
 */

package com.example.proyectostarwiki.prefs

import android.content.Context

/**
 * Clase Prefs para guardar el email con el se inicia sesión
 * en caso de que se cierre la aplicación sin cerrar sesión
 * se volverá a iniciar sesión con dicho email
 *
 */
class Prefs(c: Context) {
    val storage = c.getSharedPreferences("STAR", 0)

    /**
     * Función getEmail() que trae el email guardado en la base de datos
     * de Firebase
     */
    fun getEmail(): String?{
        return storage.getString("EMAIL", null)
    }

    /**
     * Función setEmail() que guarda el email en la base de datos
     * de Firebase
     */
    fun setEmail(email: String){
        storage.edit().putString("EMAIL", email).apply()
    }

    /**
     * Función borrarTodo() que borra el contenido la base de datos
     * de Firebase
     */
    fun borrarTodo(){
        storage.edit().clear().apply()
    }
}