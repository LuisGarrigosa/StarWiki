package com.example.proyectostarwiki.apiprovider

import com.example.proyectostarwiki.models.*
import retrofit2.http.GET

interface apiService {
    @GET("starships/")
    suspend fun getNaves(): naves

    @GET("people/")
    suspend fun getPilotos(): pilotos

    @GET("vehicles/")
    suspend fun getVehiculos(): vehiculos

    @GET("planets/")
    suspend fun getPlanetas(): planetas

    @GET("films/")
    suspend fun getPeliculas(): peliculas
}