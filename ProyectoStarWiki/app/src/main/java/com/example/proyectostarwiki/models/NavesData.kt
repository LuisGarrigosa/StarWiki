package com.example.proyectostarwiki.models


import com.google.gson.annotations.SerializedName
data class NavesData(
    @SerializedName("name") val nombre:String,
    @SerializedName("starship_class") val clase:String,
    @SerializedName("cargo_capacity") val capacidad:String,
    @SerializedName("length") val longitud:String,
    @SerializedName("passengers") val pasajeros:String,
    @SerializedName("cost_in_credits") val creditos:String,
    //@SerializedName("url") val id:String,
    //@SerializedName("pilots") val pilotos: ArrayList<PilotosData>
):java.io.Serializable

data class PilotosData(
    val nombreNave:String,
    @SerializedName("name") val nombre:String,
    @SerializedName("gender") val genero:String,
    @SerializedName("height") val altura:String,
    @SerializedName("peso") val peso:String
):java.io.Serializable