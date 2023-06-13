/**
 * naves.kt
 * @author Luis Manuel Garrigosa Jimenez
 */

package com.example.proyectostarwiki.models

/**
 * Data class para las naves
 *
 */
data class naves(
    val results: List<NavesData>
)

/**
 * Data class para los pilotos
 *
 */
data class pilotos(
    val results: List<PilotosData>
)

/**
 * Data class para los vehiculos
 *
 */
data class vehiculos(
    val results: List<VehiculosData>
)

/**
 * Data class para los planetas
 *
 */
data class planetas(
    val results: List<PlanetasData>
)

/**
 * Data class para las peliculas
 *
 */
data class peliculas(
    val results: List<PeliculasData>
)

/**
 * Data class para los mensajes
 *
 */
data class Mensajes(
    val texto: String?=null,
    val email: String?=null,
    val fecha: Long=System.currentTimeMillis()
)
