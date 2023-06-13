/**
 * apiClient.kt
 * @author Luis Manuel Garrigosa Jimenez
 */

package com.example.proyectostarwiki.apiprovider

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Objeto con la funcionalidad para crear una petición
 * en la api de Star Wars
 *
 */
object apiClient{
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://swapi.dev/api/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    val apiClient = retrofit.create(apiService::class.java)
}