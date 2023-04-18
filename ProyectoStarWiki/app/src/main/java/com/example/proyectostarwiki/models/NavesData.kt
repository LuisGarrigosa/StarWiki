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