/**
 * apiService.kt
 * @author Luis Manuel Garrigosa Jimenez
 */

package com.example.proyectostarwiki.apiprovider

import com.example.proyectostarwiki.models.*
import retrofit2.http.GET

/**
 * Interfaz con los metodos para buscar en la api de Star Wars
 *
 */
interface apiService {
    /**
     * Función getNaves() para traer la información de las naves
     * de la api
     *
     */
    @GET("starships/")
    suspend fun getNaves(): naves

    /**
     * Función getPilotos() para traer la información de los pilotos
     * de la api
     *
     */
    @GET("people/")
    suspend fun getPilotos(): pilotos

    /**
     * Función getVehiculos() para traer la información de los vehiculos
     * de la api
     *
     */
    @GET("vehicles/")
    suspend fun getVehiculos(): vehiculos

    /**
     * Función getPlanetas() para traer la información de los planetas
     * de la api
     *
     */
    @GET("planets/")
    suspend fun getPlanetas(): planetas

    /**
     * Función getPeliculas() para traer la información de las peliculas
     * de la api
     *
     */
    @GET("films/")
    suspend fun getPeliculas(): peliculas

}