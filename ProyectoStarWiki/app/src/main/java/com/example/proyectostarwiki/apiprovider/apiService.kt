package com.example.proyectostarwiki.apiprovider

import com.example.proyectostarwiki.models.naves
import com.example.proyectostarwiki.models.pilotos
import retrofit2.http.GET

interface apiService {
    @GET("starships/")
    suspend fun getNaves(): naves
    @GET("people/")
    suspend fun getPilotos(): pilotos
}