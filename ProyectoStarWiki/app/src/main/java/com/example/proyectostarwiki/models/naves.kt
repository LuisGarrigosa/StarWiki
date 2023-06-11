package com.example.proyectostarwiki.models

data class naves(
    val results: List<NavesData>
)

data class pilotos(
    val results: List<PilotosData>
)

data class vehiculos(
    val results: List<VehiculosData>
)

data class planetas(
    val results: List<PlanetasData>
)

data class peliculas(
    val results: List<PeliculasData>
)

data class Mensajes(
    val texto: String?=null,
    val email: String?=null,
    val fecha: Long=System.currentTimeMillis()
)
