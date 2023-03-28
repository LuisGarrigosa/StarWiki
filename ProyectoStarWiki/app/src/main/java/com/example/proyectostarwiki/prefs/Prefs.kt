package com.example.proyectostarwiki.prefs

import android.content.Context

class Prefs(c: Context) {
    val storage = c.getSharedPreferences("STAR", 0)

    fun getEmail(): String?{
        return storage.getString("EMAIL", null)
    }

    fun setEmail(email: String){
        storage.edit().putString("EMAIL", email).apply()
    }

    fun borrarTodo(){
        storage.edit().clear().apply()
    }
}