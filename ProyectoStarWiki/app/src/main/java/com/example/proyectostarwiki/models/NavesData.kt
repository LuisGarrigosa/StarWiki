package com.example.proyectostarwiki.models


import com.google.gson.annotations.SerializedName
data class NavesData(
    @SerializedName("name") val nombre:String,
    @SerializedName("starship_class") val clase:String,
    @SerializedName("cargo_capacity") val capacidad:String,
    @SerializedName("length") val longitud:String,
    @SerializedName("passengers") val pasajeros:String,
    @SerializedName("cost_in_credits") val creditos:String
):java.io.Serializable

data class PilotosData(
    @SerializedName("name") val nombre:String,
    @SerializedName("gender") val genero:String,
    @SerializedName("height") val altura:String,
    @SerializedName("mass") val peso:String
):java.io.Serializable

data class VehiculosData(
    @SerializedName("name") val nombre: String,
    @SerializedName("model") val modelo: String,
    @SerializedName("length") val altura: String,
    @SerializedName("max_atmosphering_speed") val velocidad: String,
    @SerializedName("manufacturer") val fabricante: String
):java.io.Serializable

data class PlanetasData(
    @SerializedName("name") val nombre: String,
    @SerializedName("gravity") val gravedad: String,
    @SerializedName("population") val poblacion: String,
    @SerializedName("diameter") val diametro: String,
    @SerializedName("terrain") val terreno: String
):java.io.Serializable

data class PeliculasData(
    @SerializedName("title") val nombre: String,
    @SerializedName("director") val director: String,
    @SerializedName("producer") val productor: String,
    @SerializedName("release_date") val estreno: String,
):java.io.Serializable